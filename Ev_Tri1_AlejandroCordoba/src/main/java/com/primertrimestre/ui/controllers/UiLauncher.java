package com.primertrimestre.ui.controllers;

import com.primertrimestre.auth.SessionContext;
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
        StudentDaoJpa studentDao = new StudentDaoJpa();
        TeacherDaoJpa teacherDao = new TeacherDaoJpa();
        ModuleDaoJpa moduleDao = new ModuleDaoJpa();
        EnrollmentDaoJpa enrollmentDao = new EnrollmentDaoJpa();

        StudentService studentService = new StudentService(studentDao);
        TeacherService teacherService = new TeacherService(teacherDao);
        ModuleService moduleService = new ModuleService(moduleDao, teacherDao);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentDao, studentDao, moduleDao);
        AdministratorService administratorService = new AdministratorService(new AdministratorDaoJpa());
        SessionContext session = new SessionContext();
        LoginWindow view = new LoginWindow();
        new LoginController(view, studentService, teacherService, administratorService, moduleService, enrollmentService, session);
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
                UiLauncher::showLogin // TODO --> Hay que entender bien esta expresión 
        );
        registrationWindow.setVisible(true);
    }
}
