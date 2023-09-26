package com.example.miniprojectdelivery.dto.menu;


import com.example.miniprojectdelivery.model.Menu;
import lombok.Getter;

import java.net.URL;

@Getter
public class MenuViewResponseDto {

    private Long id;
    private URL image;
    private String name;
    private int cost;

    public MenuViewResponseDto(Menu menu) {
        this.id = menu.getId();
        this.image = menu.getImage();
        this.name = menu.getName();
        this.cost = menu.getCost();
    }
}


