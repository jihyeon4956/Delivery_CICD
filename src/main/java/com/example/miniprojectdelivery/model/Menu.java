package com.example.miniprojectdelivery.model;

import com.example.miniprojectdelivery.dto.menu.MenuCreateRequestDto;
import com.example.miniprojectdelivery.dto.menu.MenuUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;  // S3연동예정, 더미필드(임시)

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int cost;

    @ManyToOne   // Restaurant에서 @OneToMany 필요함
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Menu(MenuCreateRequestDto requestDto, Restaurant restaurant) {
        this.id = getId();
        this.restaurant = restaurant; // 임의 Restaurant 생성
        this.image = requestDto.getImage();
        this.name = requestDto.getName();
        this.cost = requestDto.getCost();
    }

    public void update(MenuUpdateRequestDto requestDto) {
        this.image = requestDto.getImage();
        this.name = requestDto.getName();
        this.cost = requestDto.getCost();
    }
}
