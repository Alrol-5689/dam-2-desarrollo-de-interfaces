package com.primertrimestre.ui.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class RegistrationWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Create the frame.
	 * @param loginWindow 
	 */
	public RegistrationWindow(JFrame owner) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNik = new JLabel("Nik:");
		lblNik.setBounds(45, 60, 61, 16);
		contentPane.add(lblNik);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setBounds(181, 28, 215, 27);
		contentPane.add(comboBox);
		
		JLabel lblUserType = new JLabel("Tipo de usuario:");
		lblUserType.setBounds(45, 32, 110, 16);
		contentPane.add(lblUserType);
		
		JLabel lblNik_1 = new JLabel("Nik:");
		lblNik_1.setBounds(45, 88, 61, 16);
		contentPane.add(lblNik_1);
		
		JLabel lblNik_2 = new JLabel("Nik:");
		lblNik_2.setBounds(45, 116, 61, 16);
		contentPane.add(lblNik_2);
		
		JLabel lblNik_3 = new JLabel("Nik:");
		lblNik_3.setBounds(45, 144, 61, 16);
		contentPane.add(lblNik_3);

	}
}
