package com.example.task1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull(message = "corpName To'ldirilmagan!!!")
    String corpName;

    @NotNull(message = "directorName To'ldirilmagan!!!")
    String directorName;

    Integer addressId;
}
