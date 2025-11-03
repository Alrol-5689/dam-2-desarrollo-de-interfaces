package com.primertrimestre.service;

import org.mindrot.jbcrypt.BCrypt;

import com.primertrimestre.model.Student;
import com.primertrimestre.model.Teacher;
import com.primertrimestre.persistence.dao.TeacherDao;

public class TeacherService {
	
	//===>> FIELDS <<===//
	
	private final TeacherDao teacherDao;
	private static final int BCRYPT_COST = 12;
	
	//===>> CONSTRUCTORS <<===//
	
	public TeacherService(TeacherDao teacherDao) {this.teacherDao = teacherDao;}

	//===>> METHODS <<===//
	
	public Teacher authenticate(String username, String password) {
        if (username == null || password == null) return null;
        Teacher st = teacherDao.findByUsername(username);
        if (st == null || st.getPassword() == null) return null;
        return BCrypt.checkpw(password, st.getPassword()) ? st : null;
	} 
}
