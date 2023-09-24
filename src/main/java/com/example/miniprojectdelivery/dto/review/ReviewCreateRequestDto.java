package com.example.miniprojectdelivery.dto.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {

    @NotNull(message = "음식점이 지정되지 않았습니다!")
    private Long restaurantId; // 리뷰 대상 음식점 ID

    @NotBlank(message = "리뷰 내용을 입력해 주세요!")
    @Size(min = 0, max = 200, message = "리뷰는 200자 이내로 가능합니다.")
    private String content; // 리뷰 내용

    @NotNull(message = "평점을 남겨주세요!")
    private int star; // 리뷰 평점
}
