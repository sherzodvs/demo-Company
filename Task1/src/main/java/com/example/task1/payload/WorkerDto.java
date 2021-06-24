package com.example.task1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {
    @NotNull(message = "name kiritilmagan!!!")
    String name;

    @NotNull(message = "phoneNumber kiritilmagan!!!")
    String phoneNumber;

    @NotNull
    String street;

    String homeNumber;
    Integer departmentId;
}
