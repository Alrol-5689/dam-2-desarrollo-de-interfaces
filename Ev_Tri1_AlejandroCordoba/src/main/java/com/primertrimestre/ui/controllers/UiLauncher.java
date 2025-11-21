package com.primertrimestre.ui.controllers;

import com.primertrimestre.ui.view.LoginWindow;
import com.primertrimestre.ui.view.RegistrationWindow;

public final class UiLauncher {

    private static final AppContext APP = AppContext.getInstance(); // singleton: se crea una única vez al cargar la clase
    
    public static void showLogin() {
        LoginWindow view = new LoginWindow();
        new LoginController(
                view,
                APP.getStudentService(),
                APP.getTeacherService(),
                APP.getAdministratorService(),
                APP.getSession(),

                // Le pasamos una clase anónima que implementa los contratos de la interfaz que está dentro del controller
                // implementación del callback de navegación
                new LoginController.LoginNavigator() { 
                    @Override
                    public void onStudentLogin() {
                        UiLauncher.showStudent();
                    }

                    @Override
                    public void onTeacherLogin() {
                        UiLauncher.showTeacher();
                    }

                    @Override
                    public void onAdminLogin() {
                        UiLauncher.showAdmin();
                    }
                }
        ).showLoginFrame(); 
    }

    public static void showRegistration() {
        RegistrationWindow view = new RegistrationWindow();
        new RegistrationController(
                view,
                APP.getStudentService(),
                APP.getTeacherService(),
                APP.getAdministratorService(),
                UiLauncher::showLogin
        ).showRegistrationFrame();
    }

    public static void showStudent() {
        new StudentController(
                APP.getSession(),
                APP.getStudentService(),
                APP.getEnrollmentService(),
                APP.getModuleService()
        ).showStudentMainFrame();
    }

    public static void showTeacher() {
        new TeacherController(
                APP.getSession(),
                APP.getModuleService(),
                APP.getEnrollmentService()
        ).showTeacherMainFrame();
    }

    public static void showAdmin() {
        new AdminController(
                APP.getSession(),
                APP.getTeacherService(),
                APP.getModuleService(),
                APP.getEnrollmentService()
        ).showAdminMainFrame();
    }
    
}
