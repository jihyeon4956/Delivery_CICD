package com.example.miniprojectdelivery.dto.menu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.net.URL;

@Getter
public class MenuCreateRequestDto {

    @NotNull(message = "음식점 ID는 필수 입니다.")
    private Long restaurantId;

    private URL image;

    @NotBlank(message = "메뉴 이름을 입력해 주세요")
    @Size(min = 1,max = 30,message = "메뉴의 이름은 1~30자로 입력해 주세요")
    private String name;

    @NotNull(message = "가격을 입력해 주세요.")
    private int cost;
}
