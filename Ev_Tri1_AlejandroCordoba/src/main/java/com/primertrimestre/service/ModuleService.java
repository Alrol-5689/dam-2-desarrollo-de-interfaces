package com.primertrimestre.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.primertrimestre.model.Module;
import com.primertrimestre.model.Teacher;
import com.primertrimestre.persistence.dao.ModuleDao;
import com.primertrimestre.persistence.dao.TeacherDao;

public class ModuleService {

    private final ModuleDao moduleDao;
    private final TeacherDao teacherDao;

    public ModuleService(ModuleDao moduleDao, TeacherDao teacherDao) {
        this.moduleDao = moduleDao;
        this.teacherDao = teacherDao;
    }

    public List<Module> listAll() {
        return moduleDao.findAll();
    }

    public Module findById(Long moduleId) {
        return moduleDao.findById(moduleId);
    }

    public List<Module> listByTeacher(Long teacherId) {
        return moduleDao.findByTeacherId(teacherId);
    }

    public Module assignTeacher(Long moduleId, Long teacherId) {
        if (moduleId == null || teacherId == null) {
            throw new IllegalArgumentException("Module and teacher identifiers are required");
        }
        Module module = moduleDao.findById(moduleId);
        if (module == null) throw new IllegalArgumentException("Module not found");
        Teacher teacher = teacherDao.findById(teacherId);
        if (teacher == null) throw new IllegalArgumentException("Teacher not found");
        module.setTeacher(teacher);
        if (teacher.getModules() != null && !teacher.getModules().contains(module)) {
            teacher.getModules().add(module);
        }
        return moduleDao.update(module);
    }

    public List<Module> listAvailableForStudent(List<Module> enrolledModules) {
        List<Module> all = new ArrayList<>(moduleDao.findAll());
        if (enrolledModules == null || enrolledModules.isEmpty()) return all;
        Set<Long> enrolledIds = new HashSet<>();
        for (Module module : enrolledModules) {
            if (module != null && module.getId() != null) {
                enrolledIds.add(module.getId());
            }
        }
        all.removeIf(module -> module != null && enrolledIds.contains(module.getId()));
        return all;
    }
}
