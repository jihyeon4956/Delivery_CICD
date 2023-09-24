package com.example.miniprojectdelivery.dto.review;

import com.example.miniprojectdelivery.model.Review;
import lombok.Getter;

@Getter
public class ReviewViewResponseDto {

    private String userName;
    private String content;
    private Integer star;

    public ReviewViewResponseDto(Review review) {
        this.userName = review.getUser().getUsername();
        this.content = review.getContent();
        this.star = review.getStar();
    }
}
