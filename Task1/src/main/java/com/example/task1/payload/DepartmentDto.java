package com.example.task1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {
    @NotNull(message = "departName yozilmagan")
    String name;

    Integer companyId;
}
