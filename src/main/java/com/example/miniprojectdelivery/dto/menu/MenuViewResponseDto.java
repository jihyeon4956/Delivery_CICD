package com.example.miniprojectdelivery.dto.menu;


import com.example.miniprojectdelivery.model.Menu;
import lombok.Getter;

import java.net.URL;

@Getter
public class MenuViewResponseDto {

    private URL image;
    private String name;
    private int cost;

    public MenuViewResponseDto(Menu menu) {
        this.image = menu.getImage();
        this.name = menu.getName();
        this.cost = menu.getCost();
    }
}


