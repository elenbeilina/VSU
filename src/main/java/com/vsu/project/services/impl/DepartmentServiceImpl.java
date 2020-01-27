package com.vsu.project.services.impl;

import com.vsu.project.entity.Department;
import com.vsu.project.repository.DepartmentRepository;
import com.vsu.project.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    private DepartmentRepository departmentRepository;

    @Autowired
    DepartmentServiceImpl(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department addDepartment(Department department) {
        return departmentRepository.saveAndFlush(department);
    }

    @Override
    public void delete(long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department getById(long id) {
        return departmentRepository.getOne(id);
    }

    @Override
    public Department updateDepartment(Department department) {
        return departmentRepository.saveAndFlush(department);
    }

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }
}
