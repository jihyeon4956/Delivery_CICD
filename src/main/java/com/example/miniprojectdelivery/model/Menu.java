package com.example.miniprojectdelivery.model;

import com.example.miniprojectdelivery.dto.menu.MenuCreateRequestDto;
import com.example.miniprojectdelivery.dto.menu.MenuUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@Entity
@Getter
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private URL image;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int cost;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Menu(String name, int cost, Restaurant restaurant, URL image) {
        this.id = getId();
        this.restaurant = restaurant;
        this.image = image;
        this.name = name;
        this.cost = cost;
    }

    public void update(URL updatedImageUrlObject, String name, int cost) {
        this.image = updatedImageUrlObject;
        this.name = name;
        this.cost = cost;
    }
}
