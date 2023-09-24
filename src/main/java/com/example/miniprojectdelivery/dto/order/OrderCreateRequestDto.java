package com.example.miniprojectdelivery.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderCreateRequestDto {

    @NotNull(message = "메뉴를 선택해 주세요")
    private Long menuId; //메뉴 종류

    @NotNull(message = "수량을 입력해 주세요")
    private int count; //메뉴 수량
}
