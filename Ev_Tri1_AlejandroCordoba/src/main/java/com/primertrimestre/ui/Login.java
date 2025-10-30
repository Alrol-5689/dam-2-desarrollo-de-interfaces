package com.primertrimestre.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;

public class Login extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField pass;
	private JTextField user;
	private JButton btnEnviar;
	private JButton btnClear;
	private JButton btnExit;
	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("Ventana de inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(136, 154, 147, 27);
		contentPane.add(comboBox);
		
		btnClear = new JButton("Limpiar");
		btnClear.setBounds(45, 219, 111, 29);
		contentPane.add(btnClear);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(166, 219, 117, 29);
		contentPane.add(btnEnviar);
		
		btnExit = new JButton("Salir");
		btnExit.setBackground(new Color(255, 170, 175));
		btnExit.setBounds(295, 219, 112, 29);
		contentPane.add(btnExit);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
//	public JButton getBtnEnviar() {
//		return btnEnviar;
//	}
//	public JButton getBtnClear() {
//		return btnClear;
//	}
//	public JButton getBtnExit() {
//		return btnExit;
//	}
//	public JComboBox<String> getComboBox() {
//		return comboBox;
//	}
}
