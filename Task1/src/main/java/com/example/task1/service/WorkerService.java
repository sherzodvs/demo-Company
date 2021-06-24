package com.example.task1.service;

import com.example.task1.entity.Address;
import com.example.task1.entity.Department;
import com.example.task1.entity.Worker;
import com.example.task1.payload.ApiResponse;
import com.example.task1.payload.WorkerDto;
import com.example.task1.repository.AddressRepository;
import com.example.task1.repository.DepartmentRepository;
import com.example.task1.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public List<Worker> getAll(Pageable pageable) {
        return (List<Worker>) workerRepository.findAll(pageable);
    }

    public Worker getById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()){
            Worker worker = optionalWorker.get();
            return worker;
        }
        return null;
    }

    public ApiResponse add(WorkerDto workerDto) {
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (!existsByPhoneNumber){
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isPresent()){
            Department department = optionalDepartment.get();
            Worker worker = new Worker();
            worker.setDepartment(department);
            worker.setPhoneNumber(workerDto.getPhoneNumber());
            Address address = new Address();
            address.setStreet(workerDto.getStreet());
            address.setHomeNumber(workerDto.getHomeNumber());
            Address savedAddresss = addressRepository.save(address);
            worker.setAddress(savedAddresss);
            workerRepository.save(worker);
            return new ApiResponse("Saqlandi!!!",true);
        }
        return new ApiResponse("Department topilmadi!!!",false);
        }
        return new ApiResponse("Bunday telefon raqam bazada bor!!!",false);

    }

    public ApiResponse edit(Integer id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()){
            Worker worker = optionalWorker.get();
            boolean phoneNumberAndIdNot = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
            if (!phoneNumberAndIdNot){
                Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
                if (optionalDepartment.isPresent()){
                    Department department = optionalDepartment.get();
                    worker.setDepartment(department);
                    worker.setPhoneNumber(workerDto.getPhoneNumber());
                    Address address = worker.getAddress();
                    address.setStreet(workerDto.getStreet());
                    address.setHomeNumber(workerDto.getHomeNumber());
                    Address savedAddresss = addressRepository.save(address);
                    worker.setAddress(savedAddresss);
                    workerRepository.save(worker);
                    return new ApiResponse("O'zgartirildi!!!",true);
                }
                return new ApiResponse("Department topilmadi!!!",false);
            }
            return new ApiResponse("Bunday telefon raqam bazada bor!!!",false);

        }
        return new ApiResponse("Worker topilmadi!!!",false);

    }

    public ApiResponse delete(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("O'chirildi!!!",true);

        }catch (Exception e){
            return new ApiResponse("Worker topilmadi!!!",false);
        }

    }
}
