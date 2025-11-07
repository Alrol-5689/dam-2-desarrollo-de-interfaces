package com.primertrimestre.ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.primertrimestre.auth.SessionContext;
import com.primertrimestre.model.Enrollment;
import com.primertrimestre.model.Module;
import com.primertrimestre.model.Student;
import com.primertrimestre.model.Teacher;
import com.primertrimestre.service.EnrollmentService;
import com.primertrimestre.service.ModuleService;
import com.primertrimestre.ui.view.TeacherMainFrame;

public class TeacherController implements ActionListener {
	
	private TeacherMainFrame view;
	private final SessionContext session;
	private final ModuleService moduleService;
	private final EnrollmentService enrollmentService;
	

	public TeacherController(SessionContext session, 
							 ModuleService moduleService, 
							 EnrollmentService enrollmentService) {
		this.session = session;
		this.moduleService = moduleService;
		this.enrollmentService = enrollmentService;	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
			case TeacherMainFrame.CMD_REFRESH -> refresh();
			case TeacherMainFrame.CMD_SAVE -> saveNotes();
			case TeacherMainFrame.CMD_LOGOUT -> logout();
		}
	}
	
	public void showTeacherMainFrame() {
        view = new TeacherMainFrame(session);
        view.getBtnLogout().addActionListener(this);   // Botón superior de cerrar sesión
        view.getBtnRefresh().addActionListener(this);  // Botón inferior de refrescar datos
        view.getBtnSaveNotes().addActionListener(this); // Botón inferior para guardar la nota editada     
        view.addModuleSelectionListener(new ListSelectionListener() { // Reaccionar cuando el profe cambia el módulo seleccionado
        	@Override
        	public void valueChanged(ListSelectionEvent event) {onModuleSelected(event);}
        });        
        view.addViewTypeListener(new ActionListener() { // Cambiar la lista de módulos según el filtro del combo
        	@Override
        	public void actionPerformed(ActionEvent e) {refreshModules();}
        });       
        refreshModules(); 
        view.setVisible(true);
	}

    private void onModuleSelected(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) { // Ignorar eventos intermedios mientras el usuario todavía está arrastrando/clickando
            return;
        }
        loadStudentsForSelectedModule(); // Cuando confirma la selección, pedimos los alumnos del módulo
    }

    private void loadStudentsForSelectedModule() {
        view.clearStudents();
        Module selectedModule = view.getSelectionModule();
        if (selectedModule == null) return;
        List<Enrollment> enrollments = enrollmentService.listByModule(selectedModule.getId());
        for (Enrollment e : enrollments) {
            Student student = e.getStudent();
            Long studentId = student != null ? student.getId() : null;
            String username = student != null ? student.getUsername() : "Desconocido";
            String fullName = student != null ? student.getFullName() : "";
            view.addStudentRow(studentId, username, fullName, e.getGrade());
        }
    }
	
    private void refresh() {
        refreshModules();
        loadStudentsForSelectedModule();
	}

    private void refreshModules() {
        List<Module> modules = resolveModulesForCurrentFilter();
        view.setModules(modules); // le metemos los modulos que mostrar
    }

    private List<Module> resolveModulesForCurrentFilter() {
        Teacher teacher = session.getCurrentUser() instanceof Teacher t ? t : null;
        if (teacher == null) {
            return Collections.emptyList();
        }
        String selectedView = view.getSelectedViewType();
        boolean showAll = TeacherMainFrame.VIEW_TYPE_ALL_MODULES.equalsIgnoreCase(selectedView);
        return showAll ? moduleService.listAll() : moduleService.listByTeacher(teacher.getId());
    }

	private void saveNotes() {
        Module selectedModule = view.getSelectionModule();
        if (selectedModule == null) {
            view.showInfo("Seleccione un módulo antes de guardar notas.");
            return;
        }
        int selectedRow = view.getSelectedStudentRow();
        if (selectedRow < 0) {
            view.showInfo("Seleccione un alumno para actualizar su nota.");
            return;
        }
        Long studentId = view.getStudentIdAtRow(selectedRow);
        if (studentId == null) {
            view.showError("No se pudo identificar al alumno seleccionado.");
            return;
        }
        Double grade;
        try {
            grade = view.getGradeValueAtRow(selectedRow);
        } catch (NumberFormatException ex) {
            view.showError("La nota debe ser un número válido.");
            return;
        }
        try {
            enrollmentService.updateGrade(studentId, selectedModule.getId(), grade);
            view.showInfo("Nota guardada correctamente.");
            loadStudentsForSelectedModule();
        } catch (Exception ex) {
            view.showError(ex.getMessage());
        }
	}

	private void logout() {
        session.clear();
        view.dispose();
        SwingUtilities.invokeLater(UiLauncher::showLogin); // qué es SwingUtilities y por qué no simplemente UiLauncher.showLogin()?
    }

}
