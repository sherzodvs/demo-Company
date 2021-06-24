package com.example.task1.service;

import com.example.task1.entity.Address;
import com.example.task1.entity.Company;
import com.example.task1.payload.ApiResponse;
import com.example.task1.payload.CompanyDto;
import com.example.task1.repository.AddressRepository;
import com.example.task1.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()){
            return optionalCompany.get();
        }
        return null;
    }

    public ApiResponse add(CompanyDto companyDto) {

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (optionalAddress.isPresent()){
            Company company = new Company();
            Address address = optionalAddress.get();
            company.setAddress(address);
            company.setCorpName(companyDto.getCorpName());
            company.setDirectorName(companyDto.getDirectorName());
            companyRepository.save(company);
            return new ApiResponse("Saqlandi!!!",true);
        }
        return new ApiResponse("Addres topilmadi!!!",false);
    }

    public ApiResponse edit(Integer id, CompanyDto companyDto) {

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()){
            Company company = optionalCompany.get();
            Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
            if (optionalAddress.isPresent()){
                Address address = optionalAddress.get();
                company.setAddress(address);
                company.setCorpName(companyDto.getCorpName());
                company.setDirectorName(companyDto.getDirectorName());
                companyRepository.save(company);
                return new ApiResponse("O'zgartirildi!!!",true);
            }
            return new ApiResponse("Addres topilmadi!!!",false);

        }
        return new ApiResponse("Company topilmadi!!!",false);
    }

    public ApiResponse delete(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("O'chirildi!!!",true);
        }catch (Exception e){
            return new ApiResponse("Company topilmadi!!!",false);
        }

    }
}
