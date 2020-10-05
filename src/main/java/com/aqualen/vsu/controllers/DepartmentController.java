package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.entity.Department;
import com.aqualen.vsu.services.DepartmentService;
import com.aqualen.vsu.utils.Response;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    private static final String DEFAULT_ERROR_MESSAGE = "При работе с факультетами произошла непредвиденная ошибка";

    @ExceptionHandler(Exception.class)
    public Response handleException(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL mapping '" + request.getRequestURI() + "': ", e);
        return Response.fail(DEFAULT_ERROR_MESSAGE);
    }

    @SimpleLog
    @GetMapping("all")
    @ApiOperation(value = "Получить все новости")
    public Response getDepartments() {
        return Response.success(departmentService.getAll());
    }

    @SimpleLog
    @GetMapping("")
    public Response getDepartment(@RequestParam Long id) {
        return Response.success(departmentService.getById(id));
    }

    @SimpleLog
    @PutMapping("")
    public Response edit(@RequestBody Department department) {
        departmentService.update(department);
        return Response.success();
    }

    @SimpleLog
    @DeleteMapping("")
    public Response delete(@RequestParam Long id) {
        departmentService.delete(id);
        return Response.success();
    }

    @SimpleLog
    @PostMapping("")
    public Response add(@RequestBody Department department) {
        departmentService.update(department);
        return Response.success();
    }
}
