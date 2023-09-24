package com.example.miniprojectdelivery.model;

import com.example.miniprojectdelivery.dto.review.ReviewCreateRequestDto;
import com.example.miniprojectdelivery.dto.review.ReviewUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Review extends TimeStamp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;             // 리뷰 내용

    @Column(nullable = false)
    private int star;               // 리뷰 평점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;                  // 리뷰를 남긴 User

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;      // 리뷰 대상 음식점


    public void updateContentAndStars(ReviewUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.star = requestDto.getStar();
    }

    public void addContentAndStars(ReviewCreateRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.star = requestDto.getStar();
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void addRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
