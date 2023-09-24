package com.example.miniprojectdelivery.dto.menu;

import com.example.miniprojectdelivery.model.Menu;
import lombok.Getter;

import java.net.URL;


@Getter
public class MenuResponseDto {
    public Long id;
    private Long restaurantId;
    private URL image;
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
