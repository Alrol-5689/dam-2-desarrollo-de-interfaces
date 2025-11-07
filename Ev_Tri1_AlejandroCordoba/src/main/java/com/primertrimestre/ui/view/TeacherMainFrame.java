package com.primertrimestre.ui.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.primertrimestre.auth.SessionContext;
import com.primertrimestre.model.Module;
import com.primertrimestre.model.Teacher;
import javax.swing.JTable;

public class TeacherMainFrame extends JFrame { // No implemento ActionListener porque de eso quiero que se encargue el TeacherController 

    private static final long serialVersionUID = 1L;
    
    // Commands --> Necesito que sean public para usarlos en el controller porque el switch no me deja usar get... y no quiro usar cadenas if
    public static final String CMD_LOGOUT = "LOGOUT";
    public static final String CMD_SAVE = "SAVE";
    public static final String CMD_REFRESH = "REFRESH";
    public static final String VIEW_TYPE_MY_MODULES = "Módulos que imparto";
    public static final String VIEW_TYPE_ALL_MODULES = "Todos los módulos";

    private final SessionContext session;
    private Teacher teacher;
	private JPanel contentPane;
	private JButton btnLogout;
	private JLabel teacherName;
	private JPanel footerPanel;
	private JPanel mainCenterPanel;
	private JComboBox<String> comboBoxViewType;
	private JButton btnRefresh;
	private JButton btnSaveNotes;
	private final DefaultTableModel studentTableModel = new DefaultTableModel(new Object[] {"ID", "Nick", "Nombre", "Nota"}, 0) { // {columna1, columna2...}, 0 filas iniciales
		private static final long serialVersionUID = 1L;
		@Override
		public boolean isCellEditable(int row, int column) {
			return column == 3; // Sólo la columna de nota se puede editar
		}
	};
	private JTable studentsTable;
	private final String[] studentsDates = new String[4];
	private final DefaultListModel<Module> moduleListModel = new DefaultListModel<>();
	private final JList<Module> moduleList = new JList<>(moduleListModel);

    public TeacherMainFrame(SessionContext session) {
        if (session == null || !(session.getCurrentUser() instanceof Teacher teacher)) {
            throw new IllegalArgumentException("La sesión debe contener un profesor autenticado");
        }
        this.session = session;
        this.setTeacher(teacher);
    	
    	setTitle("Ventana de profesores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 581);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		buildHeader();
		buildMainPanels();
		buildFooter();
    }

    private void buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        contentPane.add(header, BorderLayout.NORTH);

        teacherName = new JLabel(resolveTeacherDisplayName(teacher));
        teacherName.setBorder(new EmptyBorder(0, 10, 0, 10));
        teacherName.setHorizontalAlignment(SwingConstants.LEFT);
        header.add(teacherName, BorderLayout.WEST);

        btnLogout = new JButton("Cerrar sesión");
        btnLogout.setActionCommand(CMD_LOGOUT);
        header.add(btnLogout, BorderLayout.EAST);
    }

    private void buildMainPanels() {
        mainCenterPanel = new JPanel(new GridLayout(1, 2));
        contentPane.add(mainCenterPanel, BorderLayout.CENTER);
        mainCenterPanel.add(buildModulesPanel());
        mainCenterPanel.add(buildStudentsPanel());
    }

    //==>> MODULES <<==//

    private JPanel buildModulesPanel() {
        JPanel modulesPanel = new JPanel(new BorderLayout());
        modulesPanel.setBorder(new EmptyBorder(2, 2, 2, 2));

        JPanel modulesPanelNorth = new JPanel(new BorderLayout());
        modulesPanelNorth.setBorder(new EmptyBorder(3, 3, 3, 3));
        
        JLabel lblVer = new JLabel("Mostrar -->");
        lblVer.setBorder(new EmptyBorder(0, 10, 0, 10));
        modulesPanelNorth.add(lblVer, BorderLayout.WEST);

        comboBoxViewType = new JComboBox<>();
        comboBoxViewType.addItem(VIEW_TYPE_MY_MODULES);
        comboBoxViewType.addItem(VIEW_TYPE_ALL_MODULES);
        modulesPanelNorth.add(comboBoxViewType, BorderLayout.CENTER);

        modulesPanel.add(modulesPanelNorth, BorderLayout.NORTH);

        moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moduleList.setBorder(new EmptyBorder(1, 1, 1, 1));
        JScrollPane scrollPaneModules = new JScrollPane(moduleList); // creamos el scroll con la lista de modulos
        modulesPanel.add(scrollPaneModules, BorderLayout.CENTER);    // metemos el scroll en el centro
        return modulesPanel;
    }
    
    //==>> STUDENTS <<==//

    private JPanel buildStudentsPanel() {
        JPanel studentsPanel = new JPanel(new BorderLayout());
        studentsPanel.setBorder(new EmptyBorder(2, 2, 2, 2));

        JPanel studentsPanelNorth = new JPanel();
        studentsPanelNorth.setBorder(new EmptyBorder(3, 3, 4, 3));
        JLabel lblAlumnosInscritos = new JLabel("Alumnos inscritos");
        lblAlumnosInscritos.setHorizontalAlignment(SwingConstants.CENTER);
        studentsPanelNorth.add(lblAlumnosInscritos);
        studentsPanel.add(studentsPanelNorth, BorderLayout.NORTH);

        studentsTable = new JTable(studentTableModel);
        studentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentsTable.setFillsViewportHeight(true);

        JScrollPane scrollPaneStudents = new JScrollPane(studentsTable);
        studentsPanel.add(scrollPaneStudents, BorderLayout.CENTER);
        return studentsPanel;
    }
    
    //==>> FOOTER <<==//--> Refrescar y guardar notas

    private void buildFooter() {
        footerPanel = new JPanel(new GridLayout(1, 2));
        contentPane.add(footerPanel, BorderLayout.SOUTH);

        btnRefresh = new JButton("Refrescar");
        btnRefresh.setActionCommand(CMD_REFRESH);
        footerPanel.add(btnRefresh);

        btnSaveNotes = new JButton("Guardar notas");
        btnSaveNotes.setActionCommand(CMD_SAVE);
        footerPanel.add(btnSaveNotes);
    }
    
    //==>> METHODS (TEACHER AND MODULES) <<==//

    public void refreshTeacherFromSession() {
        Object current = session.getCurrentUser();
        if (current instanceof Teacher updatedTeacher) { // comprueba que sea Teacher y si sí, expone variable
            setTeacher(updatedTeacher);
            teacherName.setText(resolveTeacherDisplayName(updatedTeacher));
        }
    }
   
    private String resolveTeacherDisplayName(Teacher teacher) {
        if (teacher == null) return "Profesor desconocido";
        return teacher.getFullName() != null && !teacher.getFullName().isBlank()
                ? teacher.getFullName()
                : teacher.getUsername();
    }
    
    public void setModules(List<Module> modules) {
    	moduleListModel.clear();
    	if (modules == null) return;
    	modules.forEach(m -> moduleListModel.addElement(m));
    }
    
    public Module getSelectionModule() {
    	return moduleList.getSelectedValue();
    }
    
    public void addModuleSelectionListener(ListSelectionListener listener) {
        // Permite al controller enterarse cuando el profesor cambia de módulo en la lista.
        moduleList.addListSelectionListener(listener);
    }

    public void addViewTypeListener(ActionListener listener) {
        // Se dispara cada vez que el combo "Mostrar" cambia, para que el controller filtre módulos.
        comboBoxViewType.addActionListener(listener);
    }

    public String getSelectedViewType() {
        Object selected = comboBoxViewType.getSelectedItem();
        return selected != null ? selected.toString() : "";
    }
   
    //==>> STUDENTS TABLE METHODS <<==//
    
    public void clearStudents() {
        studentTableModel.setRowCount(0);
    }
    
    public void addStudentRow(Long studentId, String username, String fullName, Double grade) { // No le paso un estudiando porque la view solo ve sesionContext 
    	studentsDates[0] = studentId != null ? studentId.toString() : "";
    	studentsDates[1] = username != null ? username : "";
    	studentsDates[2] = fullName != null ? fullName : "";
    	studentsDates[3] = grade != null ? grade.toString() : "";
        studentTableModel.addRow(studentsDates.clone()); // clone() crea una copia para que cada fila mantenga su propio array y no se sobrescriba al reutilizar studentsDates
    }
    
    public int getSelectedStudentRow() {
        return studentsTable.getSelectedRow();
    }
    
    public Long getStudentIdAtRow(int rowIndex) {
        Object value = studentTableModel.getValueAt(rowIndex, 0);
        String text = value != null ? value.toString().trim() : "";
        return text.isEmpty() ? null : Long.valueOf(text);
    }
    
    public Double getGradeValueAtRow(int rowIndex) {
        Object value = studentTableModel.getValueAt(rowIndex, 3);
        String text = value != null ? value.toString().trim() : "";
        if (text.isEmpty()) {
            return null;
        }
        return Double.valueOf(text);
    }

    //==>> GETTERS AND SETTERS <<==//

    public JButton getBtnLogout() {return btnLogout;}
    public JButton getBtnSaveNotes() {return btnSaveNotes;}
    public JButton getBtnRefresh() {return btnRefresh;}

    public Teacher getTeacher() {return teacher;}
    public void setTeacher(Teacher teacher) {this.teacher = teacher;}

    //==>> DIALOGS <<==//
    
    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
