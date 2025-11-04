package com.primertrimestre.persistence.jpa;

import com.primertrimestre.model.Student;
import com.primertrimestre.persistence.dao.StudentDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class StudentDaoJpa extends GenericDaoJpa<Student, Long> implements StudentDao {
	
	public StudentDaoJpa() { super(Student.class); }
	
	@Override
	public Student findByUsername(String username) {
	    if (username == null) return null;
	    EntityManager em = em(); //--> No importamos JpaUtil porque lo hace GenericDaoJpa
	    try {
	        return em.createQuery(
	            "SELECT s FROM Student s WHERE s.username = :username", Student.class)
		            .setParameter("username", username)
		            .getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}
}