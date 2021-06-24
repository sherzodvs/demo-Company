package com.example.task1.service;

import com.example.task1.entity.Address;
import com.example.task1.payload.ApiResponse;
import com.example.task1.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public HttpEntity<List<Address>> getAll() {
        List<Address> addressList = addressRepository.findAll();
        return ResponseEntity.ok(addressList);
    }

    public HttpEntity<Address> getById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()){
            Address address = optionalAddress.get();
            return ResponseEntity.ok(address);
        }
        return new ResponseEntity<Address>(HttpStatus.CONFLICT);
    }

    public ApiResponse add(Address address) {
        Address addressNew = new Address();
        addressNew.setHomeNumber(address.getHomeNumber());
        addressNew.setStreet(address.getStreet());
        addressRepository.save(addressNew);
       return new ApiResponse("Saqlandi!!!",true);
    }

    public ApiResponse edit(Integer id, Address address) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()){
            Address addressNew = optionalAddress.get();
            addressNew.setHomeNumber(address.getHomeNumber());
            addressNew.setStreet(address.getStreet());
            addressRepository.save(addressNew);
            return new ApiResponse("O'zgartirildi!!!",true);
        }
        return new ApiResponse("Bunday address mavjud emas!!!",false);
    }

    public ApiResponse delete(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("O'chirildi!!!",true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!",false);
        }
    }
}
