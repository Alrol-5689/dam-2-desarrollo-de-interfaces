package com.primertrimestre.ui.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javax.swing.table.DefaultTableModel;

import com.primertrimestre.auth.SessionContext;
import com.primertrimestre.model.Administrator;
import com.primertrimestre.model.Module;
import com.primertrimestre.model.Teacher;
import com.primertrimestre.service.ModuleService;
import com.primertrimestre.service.TeacherService;
import com.primertrimestre.ui.controllers.UiLauncher;

public class AdminMainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final SessionContext session;
    private final TeacherService teacherService;
    private final ModuleService moduleService;

    private final DefaultListModel<Teacher> teacherListModel = new DefaultListModel<>();
    private final JList<Teacher> teacherList = new JList<>(teacherListModel);
    private final DefaultTableModel moduleTableModel = new DefaultTableModel(
            new Object[] { "Código", "Nombre", "Profesor asignado" }, 0) {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable moduleTable = new JTable(moduleTableModel);
    private List<Module> modules = List.of();

    public AdminMainFrame(SessionContext session,
                          TeacherService teacherService,
                          ModuleService moduleService) {
        if (session == null || !(session.getCurrentUser() instanceof Administrator admin)) {
            throw new IllegalArgumentException("Session must contain an authenticated administrator");
        }
        this.session = session;
        this.teacherService = teacherService;
        this.moduleService = moduleService;

        setTitle("Panel administrador - " + (admin.getFullName() != null ? admin.getFullName() : admin.getUsername()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        initComponents(admin);
        loadTeachers();
        loadModules();
    }

    private void initComponents(Administrator admin) {
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new BorderLayout());
        header.add(new JLabel("Gestión de módulos y docentes"), BorderLayout.WEST);

        JButton logoutButton = new JButton("Cerrar sesión");
        logoutButton.addActionListener(e -> logout());
        header.add(logoutButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        teacherList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane teacherScroll = new JScrollPane(teacherList);

        moduleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane moduleScroll = new JScrollPane(moduleTable);

        JButton assignButton = new JButton("Asignar módulo seleccionado al docente");
        assignButton.addActionListener(e -> assignModuleToTeacher());

        JButton refreshButton = new JButton("Refrescar");
        refreshButton.addActionListener(e -> {
            loadTeachers();
            loadModules();
        });

        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.add(new JLabel("Profesores"), BorderLayout.NORTH);
        leftPanel.add(teacherScroll, BorderLayout.CENTER);
        leftPanel.add(refreshButton, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.add(new JLabel("Módulos"), BorderLayout.NORTH);
        rightPanel.add(moduleScroll, BorderLayout.CENTER);
        rightPanel.add(assignButton, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void loadTeachers() {
        teacherListModel.clear();
        List<Teacher> teachers = teacherService.listTeachers();
        for (Teacher teacher : teachers) {
            teacherListModel.addElement(teacher);
        }
        if (!teachers.isEmpty()) {
            teacherList.setSelectedIndex(0);
        }
    }

    private void loadModules() {
        moduleTableModel.setRowCount(0);
        modules = moduleService.listAll();
        for (Module module : modules) {
            String teacherName = module.getTeacher() != null ? module.getTeacher().toString() : "Sin asignar";
            moduleTableModel.addRow(new Object[] {
                    module.getCode(),
                    module.getName(),
                    teacherName
            });
        }
    }

    private void assignModuleToTeacher() {
        int moduleRow = moduleTable.getSelectedRow();
        Teacher selectedTeacher = teacherList.getSelectedValue();

        if (moduleRow < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un módulo.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (selectedTeacher == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un profesor.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (moduleRow >= modules.size()) {
            JOptionPane.showMessageDialog(this, "Selección de módulo inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Module module = modules.get(moduleRow);
        if (module == null) {
            JOptionPane.showMessageDialog(this, "No se pudo localizar el módulo seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            moduleService.assignTeacher(module.getId(), selectedTeacher.getId());
            loadModules();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        session.clear();
        dispose();
        SwingUtilities.invokeLater(UiLauncher::showLogin);
    }
}
