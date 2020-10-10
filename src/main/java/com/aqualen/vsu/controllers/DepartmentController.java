package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.entity.Department;
import com.aqualen.vsu.services.DepartmentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @SimpleLog
    @GetMapping("all")
    @ApiOperation(value = "Получить все новости")
    public ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity.ok(departmentService.getAll());
    }

    @SimpleLog
    @GetMapping
    public ResponseEntity<Department> getDepartment(@RequestParam Long id) {
        return ResponseEntity.ok(departmentService.getById(id));
    }

    @SimpleLog
    @PutMapping
    public void edit(@RequestBody Department department) {
        departmentService.update(department);
    }

    @SimpleLog
    @DeleteMapping
    public void delete(@RequestParam Long id) {
        departmentService.delete(id);
    }

    @SimpleLog
    @PostMapping
    public ResponseEntity<Department> add(@RequestBody Department department) {
        departmentService.update(department);
        return ResponseEntity.ok(departmentService.update(department));
    }
}
