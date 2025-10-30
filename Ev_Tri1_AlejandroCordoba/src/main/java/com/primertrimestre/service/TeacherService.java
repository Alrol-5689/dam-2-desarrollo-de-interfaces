package com.primertrimestre.service;

import com.primertrimestre.persistence.dao.TeacherDao;

public class TeacherService {
	
	//===>> FIELDS <<===//
	
	private final TeacherDao teacherDao;
	private static final int BCRYPT_COST = 12;
	
	//===>> CONSTRUCTORS <<===//
	
	public TeacherService(TeacherDao teacherDao) {this.teacherDao = teacherDao;} 
	
	//===>> METHODS <<===//
	
	// TODO: 
}
