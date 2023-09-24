package com.example.miniprojectdelivery.dto.restaurant;

import com.example.miniprojectdelivery.dto.review.ReviewViewResponseDto;
import com.example.miniprojectdelivery.dto.menu.MenuViewResponseDto;
import com.example.miniprojectdelivery.model.Restaurant;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private String address;
    private List<MenuViewResponseDto> menuList = new ArrayList<>();
    private List<ReviewViewResponseDto> reviewList = new ArrayList<>();

    public RestaurantResponseDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        restaurant.getMenuList().forEach(menu -> menuList.add(new MenuViewResponseDto(menu)));
        restaurant.getReviewList().forEach(review -> reviewList.add(new ReviewViewResponseDto(review)));
    }
}
