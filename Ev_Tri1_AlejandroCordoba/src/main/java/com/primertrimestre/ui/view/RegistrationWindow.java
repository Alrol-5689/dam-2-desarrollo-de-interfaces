package com.primertrimestre.ui.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.primertrimestre.model.Administrator;
import com.primertrimestre.model.Student;
import com.primertrimestre.model.Teacher;
import com.primertrimestre.service.AdministratorService;
import com.primertrimestre.service.StudentService;
import com.primertrimestre.service.TeacherService;

public class RegistrationWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final AdministratorService administratorService;
    private final Runnable returnToLogin;

    private final JComboBox<String> roleCombo = new JComboBox<>(new String[] { "Alumno", "Profesor", "Administrador" });
    private final JTextField usernameField = new JTextField(20);
    private final JTextField fullNameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JPasswordField confirmPasswordField = new JPasswordField(20);

    public RegistrationWindow(StudentService studentService,
                              TeacherService teacherService,
                              AdministratorService administratorService,
                              Runnable returnToLogin) {
        this.studentService = Objects.requireNonNull(studentService, "studentService");
        this.teacherService = Objects.requireNonNull(teacherService, "teacherService");
        this.administratorService = Objects.requireNonNull(administratorService, "administratorService");
        this.returnToLogin = returnToLogin != null ? returnToLogin : () -> {};
        setTitle("Registro de usuario");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancel();
            }
        });
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(content);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Rol:"), gbc);

        gbc.gridx = 1;
        form.add(roleCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        form.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(new JLabel("Nombre completo:"), gbc);

        gbc.gridx = 1;
        form.add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        form.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        form.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        form.add(new JLabel("Repetir contraseña:"), gbc);

        gbc.gridx = 1;
        form.add(confirmPasswordField, gbc);

        content.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton registerButton = new JButton("Registrar");
        registerButton.addActionListener(e -> register());
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> cancel());
        buttons.add(registerButton);
        buttons.add(cancelButton);

        content.add(buttons, BorderLayout.SOUTH);
    }

    private void register() {
        String username = usernameField.getText().trim();
        String fullName = fullNameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String role = (String) roleCombo.getSelectedItem();

        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if ("Profesor".equalsIgnoreCase(role)) {
                Teacher teacher = new Teacher();
                teacher.setUsername(username);
                teacher.setFullName(fullName);
                teacher.setPassword(password);
                teacherService.registerTeacher(teacher);
            } else if ("Administrador".equalsIgnoreCase(role)) {
                Administrator administrator = new Administrator();
                administrator.setUsername(username);
                administrator.setFullName(fullName);
                administrator.setPassword(password);
                administratorService.registerAdministrator(administrator);
            } else {
                Student student = new Student();
                student.setUsername(username);
                student.setFullName(fullName);
                student.setPassword(password);
                studentService.registerStudent(student);
            }
            JOptionPane.showMessageDialog(this, "Registro completado. Ahora puede iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            closeAndReturn();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancel() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "¿Cancelar el registro y volver al inicio de sesión?",
                "Cancelar registro",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            closeAndReturn();
        }
    }

    private void closeAndReturn() {
        dispose();
        SwingUtilities.invokeLater(returnToLogin);
    }
}
