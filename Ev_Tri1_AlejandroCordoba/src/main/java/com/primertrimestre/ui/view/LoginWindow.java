package com.primertrimestre.ui.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class LoginWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
    // Commands
    private static final String CMD_LOGIN = "LOGIN";
    private static final String CMD_CLEAR = "CLEAR";
    private static final String CMD_SINGUP = "SINGUP";
    
	private JPanel contentPane;
	private JPasswordField pass;
	private JTextField user;
	private JButton btnLogin;
	private JButton btnClear;
	private JButton btnSingUp;
	private JComboBox<String> userType;

	public LoginWindow() {
		
		setTitle("Ventana de inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null); // centrar 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUser = new JLabel("Usuario: ");
		lblUser.setBounds(45, 67, 61, 16);
		contentPane.add(lblUser);
		
		JLabel lblPass = new JLabel("Contraseña: ");
		lblPass.setBounds(45, 112, 79, 16);
		contentPane.add(lblPass);
		
		pass = new JPasswordField();
		pass.setBounds(136, 107, 147, 26);
		contentPane.add(pass);
		
		user = new JTextField();
		user.setBounds(136, 62, 147, 26);
		contentPane.add(user);
		user.setColumns(10);
		
		JLabel lblCargo = new JLabel("Cargo: ");
		lblCargo.setBounds(45, 158, 61, 16);
		contentPane.add(lblCargo);
		
		userType = new JComboBox<>();
		userType.setBounds(136, 154, 147, 27);
		userType.addItem("Seleccione");
		userType.addItem("Alumno");
		userType.addItem("Profesor");
		userType.addItem("Administrador");
		contentPane.add(userType);
		
		btnClear = new JButton("Limpiar");
		btnClear.setActionCommand(CMD_CLEAR);
		btnClear.setBounds(45, 219, 111, 29);
		contentPane.add(btnClear);
		
		btnLogin = new JButton("Enviar");
		btnLogin.setActionCommand(CMD_LOGIN);
		btnLogin.setBounds(166, 219, 117, 29);
		contentPane.add(btnLogin);
		
		btnSingUp = new JButton("Inscribirse");
        btnSingUp.setActionCommand(CMD_SINGUP);
        btnSingUp.setBounds(295, 219, 112, 29);
        contentPane.add(btnSingUp);

	}

	public JButton getBtnLogin() {return btnLogin;}
	public JButton getBtnSingUp() {return btnSingUp;}
	public JButton getBtnClear() {return btnClear;}

	public void clearForm() {
        user.setText("");
        pass.setText("");
        userType.setSelectedIndex(0);
        user.requestFocus();
	}

	public String getUserText() {
		return user.getText().trim();
	}

	public String getPasswordText() {
		return new String(pass.getPassword());
	}

	public String getSelectedUserType() {
		Object selected = userType.getSelectedItem();
		return selected != null ? selected.toString() : null;
	}

	public void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
