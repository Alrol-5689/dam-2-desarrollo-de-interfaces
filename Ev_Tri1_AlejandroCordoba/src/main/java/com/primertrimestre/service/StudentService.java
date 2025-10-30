package com.primertrimestre.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.primertrimestre.model.Student;
import com.primertrimestre.persistence.dao.StudentDao;
import com.primertrimestre.persistence.jpa.StudentDaoJpa;

public class StudentService {
	
	private final StudentDao studentDao;
	private static final int BCRYPT_COST = 12;
	
	public StudentService(StudentDao studentDao) {this.studentDao = studentDao;} // Si no le paso un StudentDaoJpa no puede usar findByUsername?
	public StudentService() {this.studentDao = new StudentDaoJpa();}
	
	public void registerStudent(Student student) {
		if(student == null || student.getUsername() == null || student.getPassword() == null)
			throw new IllegalArgumentException("Username/password required");
		
        if (studentDao.findByUsername(student.getUsername()) != null)
            throw new IllegalArgumentException("Username already exists");	
        
        String hashed = BCrypt.hashpw(student.getPassword(), BCrypt.gensalt(BCRYPT_COST));
        student.setPassword(hashed);
        studentDao.create(student);
	}
	public void updateStudent(Student student) {studentDao.update(student);}
//	public void createOrUpdate(Student student) {studentDao.createOrUpdate(student);}
	public void deleteStudent(Long id) {studentDao.delete(id);}
	
	public Student findByUsername(String username) {return studentDao.findByUsername(username);}
	public Student getStudentById(Long id) {return studentDao.findById(id);}
	public List<Student> listStudents() {return studentDao.findAll();}
	
	
	
	public Student authenticate(String username, String password) {
        if (username == null || password == null) return null;
        Student st = studentDao.findByUsername(username);
        if (st == null || st.getPassword() == null) return null;
        return BCrypt.checkpw(password, st.getPassword()) ? st : null;
	}
	
    public void changePassword(Long studentId, String currentPassword, String newPassword) {
        Student st = studentDao.findById(studentId);
        if (st == null) throw new IllegalArgumentException("Student not found");
        if (!BCrypt.checkpw(currentPassword, st.getPassword()))
            throw new IllegalArgumentException("Current password invalid");

        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt(BCRYPT_COST));
        st.setPassword(hashed);
        studentDao.update(st);
    }

}
