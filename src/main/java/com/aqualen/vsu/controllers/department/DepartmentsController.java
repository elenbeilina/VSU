package com.aqualen.vsu.controllers.department;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.entity.Department;
import com.aqualen.vsu.services.DepartmentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentsController {

    private final DepartmentService departmentService;

    @SimpleLog
    @GetMapping
    @ApiOperation(value = "Получить все новости")
    public ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity.ok(departmentService.getAll());
    }
}
