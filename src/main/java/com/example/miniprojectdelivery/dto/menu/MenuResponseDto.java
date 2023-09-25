package com.example.miniprojectdelivery.dto.menu;

import com.example.miniprojectdelivery.model.Menu;
import lombok.Getter;


@Getter
public class MenuResponseDto {
    public Long id;
    private Long restaurantId;
    private String image; // S3연동예정, 더미필드(임시)
    private String name;
    private int cost;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.restaurantId = menu.getRestaurant().getId();
        this.image = menu.getImage();
        this.name = menu.getName();
        this.cost = menu.getCost();
    }
}
