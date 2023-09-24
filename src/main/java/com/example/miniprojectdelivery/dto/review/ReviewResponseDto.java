package com.example.miniprojectdelivery.dto.review;

import com.example.miniprojectdelivery.model.Review;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private Long id;
    private Long user_id;
    private Long restaurant_id;
    private String content;
    private Integer star;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.user_id = review.getUser().getId();
        this.restaurant_id = review.getRestaurant().getId();
        this.content = review.getContent();
        this.star = review.getStar();
        this.createdAt = review.getCreatedAt();
        this.modifiedAt = review.getModifiedAt();
    }
}