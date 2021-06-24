package com.example.task1.controller;

import com.example.task1.entity.Worker;
import com.example.task1.payload.ApiResponse;
import com.example.task1.payload.WorkerDto;
import com.example.task1.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/worker")
public class workerController {
    @Autowired
    WorkerService workerService;

    @GetMapping
    public HttpEntity<?> getAll(@RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page,size);
        List<Worker> workers = workerService.getAll(pageable);
        return ResponseEntity.ok(workers);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Worker worker = workerService.getById(id);
        return ResponseEntity.status(worker!=null?200:409).body(worker);

    }
    @PostMapping
    public HttpEntity<?> add(@Valid  @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.add(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.edit(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

}
