package com.example.miniprojectdelivery.dto.restaurant;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RestaurantRequestDto {

    @NotBlank
    @Size(min =1 , max = 20)
    private String name;

    @NotBlank
    @Size(min = 1, max = 100)
    private String address;

}
