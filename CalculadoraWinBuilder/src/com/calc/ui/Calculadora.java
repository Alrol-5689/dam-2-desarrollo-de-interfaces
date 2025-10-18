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
		setBounds(100, 100, 250, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// panel
	
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(5, 4, 5, 5));

		// botones
		
		btnAC = new JButton("AC");
		btnAC.setBackground(new Color(255, 119, 113));
		btnAC.setOpaque(true); 
		btnAC.setContentAreaFilled(true); 
		panel.add(btnAC);
		
		btnC = new JButton("C");
		btnC.setBackground(new Color(255, 171, 167));
		btnC.setOpaque(true); 
		btnC.setContentAreaFilled(true); 
		panel.add(btnC);
		
		btnMasMenos = new JButton("±");
		btnMasMenos.setBorder(new CompoundBorder(null, new CompoundBorder(null, new CompoundBorder())));
		btnMasMenos.setBackground(new Color(187, 239, 255));
		btnMasMenos.setOpaque(true); 
		btnMasMenos.setContentAreaFilled(true); 
		btnPercent.setBorder(new CompoundBorder(
			new LineBorder(new Color(74, 255, 246), 2, true), 
			new EmptyBorder(5, 15, 5, 15)));
		panel.add(btnMasMenos);
		
		btnPercent = new JButton("%");
		btnPercent.setBackground(new Color(187, 239, 255));
		btnPercent.setOpaque(true); 
		btnPercent.setContentAreaFilled(true); 
		btnPercent.setBorder(new CompoundBorder(new LineBorder(new Color(74, 255, 246), 2, true), new EmptyBorder(5, 15, 5, 15)));
		panel.add(btnPercent);
		
		btn_7 = new JButton("7");
		panel.add(btn_7);

		panel.add(btnEqual);
		
		btn_8 = new JButton("8");
		panel.add(btn_8);
		
		btn_9 = new JButton("9");
		panel.add(btn_9);
		
		btnDivision = new JButton("/");
		btnDivision.setBackground(new Color(187, 239, 255));
		btnDivision.setOpaque(true); 
		btnDivision.setContentAreaFilled(true); 
		btnPercent.setBorder(new CompoundBorder(
			new LineBorder(new Color(74, 255, 246), 2, true), 
			new EmptyBorder(5, 15, 5, 15)));
		panel.add(btnDivision);
		
		btn_4 = new JButton("4");
		panel.add(btn_4);
		
		btn_5 = new JButton("5");
		panel.add(btn_5);
		
		btn_6 = new JButton("6");
		panel.add(btn_6);
		
		btnMultiplication = new JButton("*");
		btnMultiplication.setBackground(new Color(187, 239, 255));
		btnMultiplication.setOpaque(true); 
		btnMultiplication.setContentAreaFilled(true); 
		btnPercent.setBorder(new CompoundBorder(
			new LineBorder(new Color(74, 255, 246), 2, true), 
			new EmptyBorder(5, 15, 5, 15)));
		panel.add(btnMultiplication);
		
		btn_1 = new JButton("1");
		panel.add(btn_1);
		
		btn_2 = new JButton("2");
		panel.add(btn_2);
		
		btn_3 = new JButton("3");
		panel.add(btn_3);
		
		btnSubtraction = new JButton("-");
		btnSubtraction.setBackground(new Color(187, 239, 255));
		btnSubtraction.setOpaque(true); 
		btnSubtraction.setContentAreaFilled(true); 
		btnPercent.setBorder(new CompoundBorder(
			new LineBorder(new Color(74, 255, 246), 2, true), 
			new EmptyBorder(5, 15, 5, 15)));
		panel.add(btnSubtraction);
		
		btn_0 = new JButton("0");
		panel.add(btn_0);
		
		btnPoint = new JButton(".");
		panel.add(btnPoint);
		
		btnEqual = new JButton("=");
		btnEqual.setBackground(new Color(170, 196, 255));
		btnEqual.setOpaque(true); // necesario para que se vea el fondo
		btnEqual.setContentAreaFilled(true); // asegura que se pinte el área
		btnPercent.setBorder(new CompoundBorder(
			new LineBorder(new Color(74, 255, 246), 2, true), 
			new EmptyBorder(5, 15, 5, 15)));
		panel.add(btnEqual);
		
		btnSum = new JButton("+");
		btnSum.setBackground(new Color(187, 239, 255));
		btnSum.setOpaque(true); 
		btnSum.setContentAreaFilled(true); 
		btnPercent.setBorder(new CompoundBorder(
			new LineBorder(new Color(74, 255, 246), 2, true), 
			new EmptyBorder(5, 15, 5, 15)));
		panel.add(btnSum);

		// textField
		
		textField = new JTextField();
		contentPane.add(textField, BorderLayout.NORTH);
		textField.setColumns(10);

	}

}
