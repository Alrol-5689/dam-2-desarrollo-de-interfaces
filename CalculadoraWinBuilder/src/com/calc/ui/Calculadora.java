package com.calc.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Color;

public class Calculadora extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnAC;
	private JButton btnC;
	private JButton btnMasMenos;
	private JButton btnPercent;
	private JButton btn_7;
	private JButton btn_8;
	private JButton btn_9;
	private JButton btnDivision;
	private JButton btn_4;
	private JButton btn_5;
	private JButton btn_6;
	private JButton btnMultiplication;
	private JButton btn_1;
	private JButton btn_2;
	private JButton btn_3;
	private JButton btnSubtraction;
	private JButton btn_0;
	private JButton btnPoint;
	private JButton btnEqual;
	private JButton btnSum;

	private Border lineEqual;
	private Border marginEqual;
	private Border lineOperation;
	private Border marginOperation;
	private Border lineDelete;
	private Border marginDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calculadora frame = new Calculadora();
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
	public Calculadora() {

		// contentPane

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// panel
	
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(5, 4, 5, 5));

		// botones

		lineEqual = new LineBorder(new Color(0, 150, 70), 2, true);
		marginEqual = new EmptyBorder(5, 15, 5, 15);

		lineOperation = new LineBorder(new Color(0, 0, 150), 2, true);
		marginOperation = new EmptyBorder(5, 15, 5, 15);

		lineDelete = new LineBorder(new Color(150, 0, 0), 2, true);
		marginDelete = new EmptyBorder(5, 15, 5, 15);
		
		btnAC = new JButton("AC");
		btnAC.setBackground(new Color(153, 255, 148));
		btnAC.setOpaque(true); 
		btnAC.setContentAreaFilled(true); 
		btnAC.setBorder(new CompoundBorder(lineDelete, marginDelete));
		panel.add(btnAC);
		
		btnC = new JButton("C");
		btnC.setBackground(new Color(153, 255, 148));
		btnC.setOpaque(true); 
		btnC.setContentAreaFilled(true); 
		btnC.setBorder(new CompoundBorder(lineDelete, marginDelete));
		panel.add(btnC);
		
		btnMasMenos = new JButton("±");
		panel.add(btnMasMenos);
		
		btnPercent = new JButton("%");
		panel.add(btnPercent);
		
		btn_7 = new JButton("7");
		btn_7.setBackground(new Color(153, 255, 148));
		btn_7.setOpaque(true); 
		btn_7.setContentAreaFilled(true); 
		btn_7.setBorder(new CompoundBorder(lineEqual, marginEqual));
		panel.add(btn_7);

		panel.add(btnEqual);
		
		btn_8 = new JButton("8");
		panel.add(btn_8);
		
		btn_9 = new JButton("9");
		panel.add(btn_9);
		
		btnDivision = new JButton("/");
		panel.add(btnDivision);
		
		btn_4 = new JButton("4");
		panel.add(btn_4);
		
		btn_5 = new JButton("5");
		panel.add(btn_5);
		
		btn_6 = new JButton("6");
		panel.add(btn_6);
		
		btnMultiplication = new JButton("*");
		panel.add(btnMultiplication);
		
		btn_1 = new JButton("1");
		panel.add(btn_1);
		
		btn_2 = new JButton("2");
		panel.add(btn_2);
		
		btn_3 = new JButton("3");
		panel.add(btn_3);
		
		btnSubtraction = new JButton("-");
		panel.add(btnSubtraction);
		
		btn_0 = new JButton("0");
		panel.add(btn_0);
		
		btnPoint = new JButton(".");
		panel.add(btnPoint);
		
		btnEqual = new JButton("=");
		btnEqual.setBackground(new Color(153, 255, 148));
		btnEqual.setOpaque(true); // necesario para que se vea el fondo
		btnEqual.setContentAreaFilled(true); // asegura que se pinte el área
		btnEqual.setBorder(new CompoundBorder(lineEqual, marginEqual));
		panel.add(btnEqual);
		
		btnSum = new JButton("+");
		panel.add(btnSum);

		// textField
		
		textField = new JTextField();
		contentPane.add(textField, BorderLayout.NORTH);
		textField.setColumns(10);

	}

}
