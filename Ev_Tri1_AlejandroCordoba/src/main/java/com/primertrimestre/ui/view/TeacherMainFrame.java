package com.primertrimestre.ui.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import com.primertrimestre.auth.SessionContext;
import com.primertrimestre.model.Enrollment;
import com.primertrimestre.model.Module;
import com.primertrimestre.model.Student;
import com.primertrimestre.model.Teacher;
import com.primertrimestre.service.EnrollmentService;
import com.primertrimestre.service.ModuleService;
import com.primertrimestre.ui.controllers.UiLauncher;

public class TeacherMainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final SessionContext session;
    private final ModuleService moduleService;
    private final EnrollmentService enrollmentService;

    private final DefaultListModel<Module> moduleListModel = new DefaultListModel<>();
    private final JList<Module> moduleList = new JList<>(moduleListModel);
    private final DefaultTableModel studentsTableModel = new DefaultTableModel(
            new Object[] { "Alumno", "Usuario", "Nota" }, 0) {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 2;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 2 ? Double.class : String.class;
        }
    };
    private final JTable studentsTable = new JTable(studentsTableModel);

    private Module currentModule;
    private final List<Enrollment> currentEnrollments = new ArrayList<>();

    public TeacherMainFrame(SessionContext session,
                            ModuleService moduleService,
                            EnrollmentService enrollmentService) {
        if (session == null || !(session.getCurrentUser() instanceof Teacher teacher)) {
            throw new IllegalArgumentException("Session must contain an authenticated teacher");
        }
        this.session = session;
        this.moduleService = moduleService;
        this.enrollmentService = enrollmentService;

        setTitle("Panel profesor - " + (teacher.getFullName() != null ? teacher.getFullName() : teacher.getUsername()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        initComponents(teacher);
        loadModules(teacher);
    }

    private void initComponents(Teacher teacher) {
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new BorderLayout());
        header.add(new JLabel("Módulos impartidos por " + teacher), BorderLayout.WEST);

        JButton logoutButton = new JButton("Cerrar sesión");
        logoutButton.addActionListener(e -> logout());
        header.add(logoutButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moduleList.addListSelectionListener(this::onModuleSelection);
        JScrollPane moduleScroll = new JScrollPane(moduleList);

        studentsTable.setFillsViewportHeight(true);
        JScrollPane tableScroll = new JScrollPane(studentsTable);

        JButton refreshButton = new JButton("Refrescar");
        refreshButton.addActionListener(e -> refresh());

        JButton gradeButton = new JButton("Guardar nota seleccionada");
        gradeButton.addActionListener(e -> updateSelectedGrade());

        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.add(new JLabel("Módulos"), BorderLayout.NORTH);
        leftPanel.add(moduleScroll, BorderLayout.CENTER);
        leftPanel.add(refreshButton, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.add(new JLabel("Alumnos inscritos"), BorderLayout.NORTH);
        rightPanel.add(tableScroll, BorderLayout.CENTER);
        rightPanel.add(gradeButton, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void loadModules(Teacher teacher) {
        moduleListModel.clear();
        List<Module> modules = moduleService.listByTeacher(teacher.getId());
        for (Module module : modules) {
            moduleListModel.addElement(module);
        }
        if (!modules.isEmpty()) {
            moduleList.setSelectedIndex(0);
        } else {
            loadStudentsForModule(null);
        }
    }

    private void onModuleSelection(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) return;
        Module selected = moduleList.getSelectedValue();
        loadStudentsForModule(selected);
    }

    private void loadStudentsForModule(Module module) {
        currentModule = module;
        currentEnrollments.clear();
        studentsTableModel.setRowCount(0);

        if (module == null) return;

        List<Enrollment> enrollments = enrollmentService.listByModule(module.getId());
        for (Enrollment enrollment : enrollments) {
            Student student = enrollment.getStudent();
            currentEnrollments.add(enrollment);
            studentsTableModel.addRow(new Object[] {
                    student != null ? student.toString() : "Desconocido",
                    student != null ? student.getUsername() : "",
                    enrollment.getGrade()
            });
        }
    }

    private void updateSelectedGrade() {
        int row = studentsTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (currentModule == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un módulo.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Object value = studentsTableModel.getValueAt(row, 2);
        Double grade = null;
        if (value != null) {
            try {
                grade = value instanceof Number ? ((Number) value).doubleValue() : Double.parseDouble(value.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La nota debe ser numérica.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        Enrollment enrollment = currentEnrollments.get(row);
        try {
            enrollmentService.updateGrade(enrollment.getStudent().getId(), enrollment.getModule().getId(), grade);
            loadStudentsForModule(currentModule);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refresh() {
        Teacher teacher = (Teacher) session.getCurrentUser();
        loadModules(teacher);
    }

    private void logout() {
        session.clear();
        dispose();
        SwingUtilities.invokeLater(UiLauncher::showLogin);
    }
}
