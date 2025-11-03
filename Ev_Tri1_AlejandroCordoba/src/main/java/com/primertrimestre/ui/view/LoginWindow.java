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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
    // Commands
    private static final String CMD_LOGIN = "LOGIN";
    private static final String CMD_CLEAR = "CLEAR";
    private static final String CMD_SIGNUP = "SIGNUP";
    
	private JPanel contentPane;
	private JPasswordField pass;
	private JTextField user;
	private JButton btnEnviar;
	private JButton btnClear;
	private JButton btnSingup;
	private JComboBox<String> userType;
	/**
	 * Create the frame.
	 */
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
		btnClear.addActionListener(this);
		btnClear.setBounds(45, 219, 111, 29);
		contentPane.add(btnClear);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.setActionCommand(CMD_LOGIN);
		btnEnviar.setBounds(166, 219, 117, 29);
		contentPane.add(btnEnviar);
		
		btnSingup = new JButton("Inscribirse");
		btnSingup.setActionCommand(CMD_SIGNUP);
		btnSingup.addActionListener(this);
		btnSingup.setBounds(295, 219, 112, 29);
		contentPane.add(btnSingup);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
        switch (cmd) {
	        case CMD_CLEAR -> clearForm();
	        case CMD_SIGNUP -> openRegistrationWindow();
        }
	}

	private void openRegistrationWindow() {
        RegistrationWindow reg = new RegistrationWindow(this);
        reg.setModal(true); // bloquea hasta cerrar
        reg.setVisible(true); // Cuando el usuario cierre el diálogo, el control vuelve aquí
        // this.dispose();  // <-- Si haces esto, cierras LoginWindow
	}

	public void clearForm() {
        user.setText("");
        pass.setText("");
        userType.setSelectedIndex(0);
        user.requestFocus();
	}

	public JButton getBtnEnviar() {
		return btnEnviar;
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
