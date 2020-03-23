package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.Department;
import com.aqualen.vsu.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Department getById(long id) {
        return departmentRepository.getOne(id);
    }

    public Department update(Department department) {
        return departmentRepository.saveAndFlush(department);
    }

    public void delete(long id) {
        departmentRepository.deleteById(id);
    }
}
