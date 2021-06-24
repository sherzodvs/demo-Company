package com.example.task1.service;

import com.example.task1.entity.Company;
import com.example.task1.entity.Department;
import com.example.task1.payload.ApiResponse;
import com.example.task1.payload.DepartmentDto;
import com.example.task1.repository.CompanyRepository;
import com.example.task1.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Department getById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent())
            return optionalDepartment.get();
        return null;
    }

    public ApiResponse add(DepartmentDto departmentDto) {
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            Department department = new Department();
            department.setName(departmentDto.getName());
            department.setCompany(company);
            departmentRepository.save(department);
            return new ApiResponse("Saqlandi!!!",true);
        }
        return new ApiResponse("Company topilmadi!!!", false);
    }

    public ApiResponse edit(Integer id, DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()){
            Department department = optionalDepartment.get();
            Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
            if (optionalCompany.isPresent()){
                Company company = optionalCompany.get();
                department.setCompany(company);
                department.setName(departmentDto.getName());
                departmentRepository.save(department);
                return new ApiResponse("O'zgartirildi!!!",true);
            }
            return new ApiResponse("Company topilmadi!!!",false);
        }
        return new ApiResponse("Department topilmadi!!!",false);
    }

    public ApiResponse delete(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("O'chirildi!!!",true);

        }catch (Exception e){
            return new ApiResponse("Department topilmadi!!!",false);
        }

    }
}
