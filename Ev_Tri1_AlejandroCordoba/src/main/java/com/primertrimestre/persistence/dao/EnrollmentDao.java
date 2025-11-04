package com.primertrimestre.persistence.dao;

import java.util.List;

import com.primertrimestre.model.Enrollment;

public interface EnrollmentDao extends GenericDao<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByModuleId(Long moduleId);

    Enrollment findByStudentAndModule(Long studentId, Long moduleId);
}
