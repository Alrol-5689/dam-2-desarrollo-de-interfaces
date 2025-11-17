package com.primertrimestre.ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.primertrimestre.auth.SessionContext;
import com.primertrimestre.model.Administrator;
import com.primertrimestre.model.Student;
import com.primertrimestre.model.Teacher;
import com.primertrimestre.service.AdministratorService;
import com.primertrimestre.service.EnrollmentService;
import com.primertrimestre.service.ModuleService;
import com.primertrimestre.service.StudentService;
import com.primertrimestre.service.TeacherService;
import com.primertrimestre.ui.controllers.AdminController;
import com.primertrimestre.ui.view.StudentMainFrame;
import com.primertrimestre.ui.view.LoginWindow;

public final class LoginController implements ActionListener{
	
    private final LoginWindow view;
    private final SessionContext session;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final AdministratorService administratorService;
    private final ModuleService moduleService;
    private final EnrollmentService enrollmentService;

    public LoginController(LoginWindow view, StudentService studentService, 
    					   TeacherService teacherService, AdministratorService administratorService,
    					   ModuleService moduleService, EnrollmentService enrollmentService, SessionContext session) {
        this.view = view;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.administratorService = administratorService;
        this.moduleService = moduleService;
        this.enrollmentService = enrollmentService;
        this.session = session;
        view.getBtnLogin().addActionListener(this);
        view.getBtnSingUp().addActionListener(this);
        view.getBtnClear().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {	
    	String command = e.getActionCommand(); // Objet obj = e.getSource(); me gusta menos. 
    	switch (command) {
	    	case LoginWindow.CMD_LOGIN  -> handleLogin();
	        case LoginWindow.CMD_CLEAR  -> view.clearForm();
	        case LoginWindow.CMD_SINGUP -> openRegistration();
    	}
    }
    
    private void handleLogin() {    	
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

	private void handleAdmin(String username, String password) {
        Administrator administrator = administratorService.authenticate(username, password);
        if (administrator != null) {
            session.setCurrentUser(administrator);
            view.dispose();
            new AdminController(session, teacherService, moduleService, enrollmentService)
                .showAdminMainFrame();
        } else {
            view.showError("Credenciales incorrectas.");
            view.clearForm();
        }
	}

	private void handleTeacher(String username, String password) {
        Teacher teacher = teacherService.authenticate(username, password);
        if (teacher != null) {
            session.setCurrentUser(teacher);
            view.dispose();
            new TeacherController(session, moduleService, enrollmentService)
                .showTeacherMainFrame(); 
        } else {
            view.showError("Credenciales incorrectas.");
            view.clearForm();
        }
	}

	private void handleStudent(String username, String password) {
        Student student = studentService.authenticate(username, password);
        if (student != null) {
            session.setCurrentUser(student);
            view.dispose();
            new StudentController(session, studentService, enrollmentService, moduleService)
                .showStudentMainFrame();
        } else {
            view.showError("Credenciales incorrectas.");
            view.clearForm();
        }
	}

    private void openRegistration() {
        session.clear();
        view.dispose();
        UiLauncher.showRegistration();
    }

}
