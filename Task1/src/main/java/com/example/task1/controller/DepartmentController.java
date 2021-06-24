package com.example.task1.controller;

import com.example.task1.entity.Department;
import com.example.task1.payload.ApiResponse;
import com.example.task1.payload.DepartmentDto;
import com.example.task1.service.DepartmentService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public HttpEntity<?> getAll(){
        List<Department> departments = departmentService.getAll();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Department department = departmentService.getById(id);
        return ResponseEntity.status(department!=null?200:409).body(department);
    }
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.add(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit( @PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.edit(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }
}
