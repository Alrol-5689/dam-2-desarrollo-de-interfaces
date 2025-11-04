package com.primertrimestre.ui.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.primertrimestre.auth.SessionContext;
import com.primertrimestre.model.Enrollment;
import com.primertrimestre.model.Module;
import com.primertrimestre.model.Student;
import com.primertrimestre.service.EnrollmentService;
import com.primertrimestre.service.ModuleService;
import com.primertrimestre.ui.controllers.UiLauncher;

public class StudentMainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final SessionContext session;
    private final ModuleService moduleService;
    private final EnrollmentService enrollmentService;

    private final DefaultListModel<Module> enrolledModel = new DefaultListModel<>();
    private final DefaultListModel<Module> availableModel = new DefaultListModel<>();
    private final JList<Module> enrolledList = new JList<>(enrolledModel);
    private final JList<Module> availableList = new JList<>(availableModel);

    public StudentMainFrame(SessionContext session,
                            ModuleService moduleService,
                            EnrollmentService enrollmentService) {
        if (session == null || !(session.getCurrentUser() instanceof Student)) {
            throw new IllegalArgumentException("Session must contain an authenticated student");
        }
        this.session = session;
        this.moduleService = moduleService;
        this.enrollmentService = enrollmentService;

        Student student = (Student) session.getCurrentUser();
        setTitle("Panel alumno - " + (student.getFullName() != null ? student.getFullName() : student.getUsername()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        initComponents(student);
        loadData();
    }

    private void initComponents(Student student) {
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new BorderLayout());
        JLabel welcome = new JLabel("Bienvenido, " + student);
        welcome.setHorizontalAlignment(SwingConstants.LEFT);
        header.add(welcome, BorderLayout.WEST);

        JButton logoutButton = new JButton("Cerrar sesión");
        logoutButton.addActionListener(e -> logout());
        header.add(logoutButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        enrolledList.setCellRenderer(new DefaultListCellRenderer());
        availableList.setCellRenderer(new DefaultListCellRenderer());

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        listsPanel.add(buildListPanel("Inscrito en", enrolledList,
                createButton("Darse de baja", this::handleUnenroll)));
        listsPanel.add(buildListPanel("Módulos disponibles", availableList,
                createButton("Inscribirse", this::handleEnroll)));

        add(listsPanel, BorderLayout.CENTER);
    }

    private JPanel buildListPanel(String title, JList<Module> list, JButton actionButton) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        if (actionButton != null) {
            panel.add(actionButton, BorderLayout.SOUTH);
        }
        return panel;
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
    }

    private void loadData() {
        Student student = getStudent();
        enrolledModel.clear();
        availableModel.clear();

        List<Enrollment> enrollments = enrollmentService.listByStudent(student.getId());
        List<Module> enrolledModules = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getModule() != null) {
                enrolledModules.add(enrollment.getModule());
                enrolledModel.addElement(enrollment.getModule());
            }
        }
        List<Module> availableModules = moduleService.listAvailableForStudent(enrolledModules);
        for (Module module : availableModules) {
            availableModel.addElement(module);
        }
    }

    private void handleEnroll() {
        Module selected = availableList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un módulo disponible.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Student student = getStudent();
        try {
            enrollmentService.enrollStudent(student.getId(), selected.getId());
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUnenroll() {
        Module selected = enrolledList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un módulo inscrito.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Student student = getStudent();
        try {
            enrollmentService.unenrollStudent(student.getId(), selected.getId());
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        session.clear();
        dispose();
        SwingUtilities.invokeLater(UiLauncher::showLogin);
    }

    private Student getStudent() {
        return (Student) session.getCurrentUser();
    }
}
