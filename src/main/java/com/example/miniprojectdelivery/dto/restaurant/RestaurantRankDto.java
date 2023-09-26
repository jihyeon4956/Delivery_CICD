package com.example.miniprojectdelivery.dto.restaurant;

import com.example.miniprojectdelivery.model.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@Getter
@NoArgsConstructor
public class RestaurantRankDto {
    private Long id;
    private URL image;
    private String name;

    public RestaurantRankDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.image = restaurant.getMenuList().get(0).getImage();
        this.name = restaurant.getName();
    }
}
