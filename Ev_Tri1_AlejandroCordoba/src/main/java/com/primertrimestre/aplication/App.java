package com.primertrimestre.aplication;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import com.primertrimestre.auth.SessionContext;
import com.primertrimestre.persistence.dao.StudentDao;
import com.primertrimestre.persistence.dao.AdministratorDao;
import com.primertrimestre.persistence.dao.TeacherDao;
import com.primertrimestre.persistence.jpa.AdministratorDaoJpa;
import com.primertrimestre.persistence.jpa.StudentDaoJpa;
import com.primertrimestre.persistence.jpa.TeacherDaoJpa;
import com.primertrimestre.persistence.util.JpaUtil;
import com.primertrimestre.service.AdministratorService;
import com.primertrimestre.service.StudentService;
import com.primertrimestre.service.TeacherService;
import com.primertrimestre.ui.controllers.LoginController;
import com.primertrimestre.ui.view.LoginWindow;

public class App {

	public static void main(String[] args) {
        // 1) Inicializar JPA antes de lanzar la UI
        try {
            JpaUtil.getEntityManager().close(); // lo llamamos para comprobar la db y enseguida lo cerramos 
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo inicializar la base de datos:\n" + e.getMessage(),
                    "Error grave",
                    JOptionPane.ERROR_MESSAGE
            );
            //return; // no seguimos si la BD no está
        }
        // 2) Lanzar la UI
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	try {
            		
            		StudentDao studentDao = new StudentDaoJpa();
            		TeacherDao teacherDao = new TeacherDaoJpa();
            		AdministratorDao administratorDao = new AdministratorDaoJpa();
            		
            		StudentService studentServicer = new StudentService(studentDao);
            		TeacherService teacherService = new TeacherService(teacherDao);   	
            		AdministratorService administratorService = new AdministratorService(administratorDao);
            		
            		SessionContext sesionContext = new SessionContext();

                    LoginWindow loginWindow = new LoginWindow();
                    
                    new LoginController(loginWindow, studentServicer, teacherService, administratorService, sesionContext);
                    // El constructor se registra como listener del botón Enviar, por eso no guardamos la referencia de esta instancia. 

                    loginWindow.setVisible(true);  
                    
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            }
        });
	}
}
