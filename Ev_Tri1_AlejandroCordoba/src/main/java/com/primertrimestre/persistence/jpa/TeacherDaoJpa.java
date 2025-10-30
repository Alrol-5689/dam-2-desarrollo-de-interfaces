package com.primertrimestre.persistence.jpa;

import com.primertrimestre.model.Teacher;
import com.primertrimestre.persistence.dao.TeacherDao;
import com.primertrimestre.persistence.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class TeacherDaoJpa extends GenericDaoJpa<Teacher, Long> implements TeacherDao {

	protected TeacherDaoJpa() {super(Teacher.class);}

	@Override
	public Teacher findByUsername(String username) {
	    if (username == null) return null;
	    EntityManager em = JpaUtil.getEntityManager();
	    try {
	        return em.createQuery(
	            "SELECT t FROM Teacher t WHERE t.username = :username", Teacher.class)
		            .setParameter("username", username)
		            .getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}

}
