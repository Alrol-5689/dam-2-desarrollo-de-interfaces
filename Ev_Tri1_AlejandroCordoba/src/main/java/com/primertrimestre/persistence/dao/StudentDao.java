package com.primertrimestre.persistence.dao;

import com.primertrimestre.model.Student;

public interface StudentDao extends GenericDao<Student, Long> {
	
	Student findByUsername(String username);
	
//	Student findByUsernameAndPassword(String username, String password);

}
