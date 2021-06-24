package com.example.task1.controller;

import com.example.task1.entity.Company;
import com.example.task1.payload.ApiResponse;
import com.example.task1.payload.CompanyDto;
import com.example.task1.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping
    public HttpEntity<?> getAll(){
        List<Company> companyList = companyService.getAll();
    return ResponseEntity.ok(companyList);
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Company company = companyService.getById(id);
        return ResponseEntity.status(company!=null?200:409).body(company);
    }

    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.add(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@Valid @PathVariable Integer id, @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.edit(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);

    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
