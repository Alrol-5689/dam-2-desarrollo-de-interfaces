package com.primertrimestre.ui.controllers;

import com.primertrimestre.auth.SessionContext;
import com.primertrimestre.persistence.dao.StudentDao;
import com.primertrimestre.persistence.dao.TeacherDao;
import com.primertrimestre.persistence.dao.ModuleDao;
import com.primertrimestre.persistence.dao.EnrollmentDao;
import com.primertrimestre.persistence.jpa.AdministratorDaoJpa;
import com.primertrimestre.persistence.jpa.EnrollmentDaoJpa;
import com.primertrimestre.persistence.jpa.ModuleDaoJpa;
import com.primertrimestre.persistence.jpa.StudentDaoJpa;
import com.primertrimestre.persistence.jpa.TeacherDaoJpa;
import com.primertrimestre.service.AdministratorService;
import com.primertrimestre.service.EnrollmentService;
import com.primertrimestre.service.ModuleService;
import com.primertrimestre.service.StudentService;
import com.primertrimestre.service.TeacherService;
import com.primertrimestre.ui.view.LoginWindow;
import com.primertrimestre.ui.view.RegistrationWindow;

public final class UiLauncher {
    public static void showLogin() {
        StudentDao studentDao = new StudentDaoJpa();
        TeacherDao teacherDao = new TeacherDaoJpa();
        ModuleDao moduleDao = new ModuleDaoJpa();
        EnrollmentDao enrollmentDao = new EnrollmentDaoJpa();

        StudentService studentService = new StudentService(studentDao);
        TeacherService teacherService = new TeacherService(teacherDao);
        ModuleService moduleService = new ModuleService(moduleDao, teacherDao);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentDao, studentDao, moduleDao);
        AdministratorService administratorService = new AdministratorService(new AdministratorDaoJpa());
        SessionContext session = new SessionContext();
        
        LoginWindow view = new LoginWindow();
        
        new LoginController(view, 
			        		studentService, 
			        		teacherService, 
			        		administratorService, 
			        		moduleService, 
			        		enrollmentService, 
			        		session);
        // El constructor se registra como listener del botón Enviar, por eso no guardamos la referencia de esta instancia. 
        view.setVisible(true);
    }
    
    public static void showLogin_2() { //==>> NO RECOMENTADO crear tandos DAOs... mejor la versión larga de arriba 

        LoginWindow view = new LoginWindow();
        
        new LoginController(view, 
			        	    new StudentService(new StudentDaoJpa()),
			        	    new TeacherService(new TeacherDaoJpa()),
			        	    new AdministratorService(new AdministratorDaoJpa()),
			        	    new ModuleService(new ModuleDaoJpa(), new TeacherDaoJpa()),
			        	    new EnrollmentService(new EnrollmentDaoJpa(), new StudentDaoJpa(), new ModuleDaoJpa()),
			        	    new SessionContext());
        // El constructor se registra como listener del botón Enviar, por eso no guardamos la referencia de esta instancia. 
        view.setVisible(true);
    }

    public static void showRegistration() {
        StudentDaoJpa studentDao = new StudentDaoJpa();
        TeacherDaoJpa teacherDao = new TeacherDaoJpa();
        AdministratorDaoJpa administratorDao = new AdministratorDaoJpa();

        StudentService studentService = new StudentService(studentDao);
        TeacherService teacherService = new TeacherService(teacherDao);
        AdministratorService administratorService = new AdministratorService(administratorDao);

        RegistrationWindow registrationWindow = new RegistrationWindow(
                studentService,
                teacherService,
                administratorService,
                UiLauncher::showLogin // Es lo mismo que escribir ' showLogin(); '
        );
        registrationWindow.setVisible(true);
    }
    
}
