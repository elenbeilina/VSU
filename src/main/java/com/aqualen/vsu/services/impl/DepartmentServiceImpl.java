package com.aqualen.vsu.services.impl;

import com.aqualen.vsu.entity.Department;
import com.aqualen.vsu.services.DepartmentService;
import com.aqualen.vsu.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

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
