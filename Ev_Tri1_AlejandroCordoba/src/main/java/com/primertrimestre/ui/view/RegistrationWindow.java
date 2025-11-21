package com.primertrimestre.ui.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class RegistrationWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    public static final String CMD_REGISTER = "REGISTER";
    public static final String CMD_CANCEL = "CANCEL";

    private final JComboBox<String> roleCombo = new JComboBox<>(new String[] { "Alumno", "Profesor", "Administrador" });
    private final JTextField usernameField = new JTextField(20);
    private final JTextField fullNameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JPasswordField confirmPasswordField = new JPasswordField(20);
    private JButton registerButton;
    private JButton cancelButton;

    public RegistrationWindow() {
        setTitle("Registro de usuario");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
        registerButton = new JButton("Registrar");
        registerButton.setActionCommand(CMD_REGISTER);
        cancelButton = new JButton("Cancelar");
        cancelButton.setActionCommand(CMD_CANCEL);
        buttons.add(registerButton);
        buttons.add(cancelButton);

        content.add(buttons, BorderLayout.SOUTH);
    }

    public JButton getBtnRegister() { return registerButton; }
    public JButton getBtnCancel() { return cancelButton; }

    public String getUsername() { return usernameField.getText().trim(); }
    public String getFullName() { return fullNameField.getText().trim(); }
    public String getPassword() { return new String(passwordField.getPassword()); }
    public String getConfirmPassword() { return new String(confirmPasswordField.getPassword()); }
    public String getSelectedRole() { return (String) roleCombo.getSelectedItem(); }

    public void clearForm() {
        usernameField.setText("");
        fullNameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        roleCombo.setSelectedIndex(0);
        usernameField.requestFocus();
    }

    public boolean confirmCancel() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "¿Cancelar el registro y volver al inicio de sesión?",
                "Cancelar registro",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return option == JOptionPane.YES_OPTION;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
