package com.primertrimestre.ui.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.primertrimestre.auth.SessionContext;
import com.primertrimestre.model.User;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainFrame(SessionContext session) {
        setTitle("Panel principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        User user = session != null ? session.getCurrentUser() : null;
        String name = user != null && user.getFullName() != null
                ? user.getFullName()
                : "usuario";

        JLabel label = new JLabel("Bienvenido, " + name + "!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
}
