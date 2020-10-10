package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.Department;
import com.aqualen.vsu.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

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
