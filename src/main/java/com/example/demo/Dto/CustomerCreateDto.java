package com.example.demo.Dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateDto {
    @Size(min = 8, max = 30,message = "khong duoc de qua 30 ky tu va tren 8 ky tu")
    private String password;
    private String name;
    private String phoneNumber;
    private String email;

}
