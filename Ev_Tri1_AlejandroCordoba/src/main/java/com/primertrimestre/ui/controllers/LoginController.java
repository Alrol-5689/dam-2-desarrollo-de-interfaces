package com.primertrimestre.ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.primertrimestre.auth.SessionContext;
import com.primertrimestre.model.Administrator;
import com.primertrimestre.model.Student;
import com.primertrimestre.model.Teacher;
import com.primertrimestre.service.AdministratorService;
import com.primertrimestre.service.StudentService;
import com.primertrimestre.service.TeacherService;
import com.primertrimestre.ui.view.LoginWindow;
import com.primertrimestre.ui.view.MainFrame;

public final class LoginController implements ActionListener{
	
    private final LoginWindow view;
    private final SessionContext session;
    private final StudentService studentServicer;
    private final TeacherService teacherService;
    private final AdministratorService administratorService;

    public LoginController(LoginWindow v, StudentService s, TeacherService t, AdministratorService a, SessionContext ss) {
        this.view = v;
        this.studentServicer = s != null ? s : null;
        this.teacherService = t != null ? t : null;
        this.administratorService = a != null ? a : null;
        this.session = ss;
        view.getBtnEnviar().addActionListener(this); // El controlador queda registrado como listener del botón Enviar
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = view.getUserText();
        String password = view.getPasswordText();
        String userType = view.getSelectedUserType();

        if (username.isEmpty() || password.isEmpty()) {
            view.showError("Usuario y contraseña son obligatorios.");
            view.clearForm();
            return;
        }

        if (userType == null || "Seleccione".equalsIgnoreCase(userType)) {
            view.showError("Seleccione un cargo.");
            return;
        }
        
        switch (userType) {
	        case "Alumno" -> handleStudent(username, password);
	        case "Profesor" -> handleTeacher(username, password);
	        case "Administrador" -> handleAdmin(username, password);
	        default -> view.showError("Tipo no soportado.");
        }

    }
    

	private Object handleAdmin(String username, String password) {
		
		Administrator administrator = administratorService.authenticate(username, password);
		if (administrator != null) {
			session.setCurrentUser(administrator);
			view.dispose();
			new MainFrame(session).setVisible(true);
		} else {
			view.showError("Credenciales incorrectas.");
			view.clearForm();
		}
		return null;
	}

	private Object handleTeacher(String username, String password) {
		
		Teacher teacher = teacherService.authenticate(username, password);
		if (teacher != null) {
			session.setCurrentUser(teacher);
			view.dispose();
			new MainFrame(session).setVisible(true);
		} else {
			view.showError("Credenciales incorrectas.");
			view.clearForm();
		}
		return null;
	}

	private Object handleStudent(String username, String password) {
		
		Student student = studentServicer.authenticate(username, password);
		if (student != null) {
			session.setCurrentUser(student);
			view.dispose();
			new MainFrame(session).setVisible(true);
		} else {
			view.showError("Credenciales incorrectas.");
			view.clearForm();
		}
		return null;
	}

}
