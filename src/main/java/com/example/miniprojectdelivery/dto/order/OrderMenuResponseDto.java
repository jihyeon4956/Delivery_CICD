package com.example.miniprojectdelivery.dto.order;

import com.example.miniprojectdelivery.model.Menu;
import lombok.Getter;

@Getter
public class OrderMenuResponseDto {

    public Long id;
    private Long restaurantId;
    private String name;  //메뉴 이름
    private int cost;  //메뉴 가격
    private int count;  //수량

    public OrderMenuResponseDto(Menu menu,int count) {
        this.id = menu.getId();
        this.restaurantId = menu.getRestaurant().getId();
        this.name = menu.getName();
        this.cost = menu.getCost();
        this.count = count;
    }
}
