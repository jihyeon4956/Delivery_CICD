package com.example.miniprojectdelivery.controller;

import com.example.miniprojectdelivery.dto.MessageResponseDto;
import com.example.miniprojectdelivery.dto.review.ReviewCreateRequestDto;
import com.example.miniprojectdelivery.dto.review.ReviewResponseDto;
import com.example.miniprojectdelivery.dto.review.ReviewUpdateRequestDto;
import com.example.miniprojectdelivery.service.ReviewService;
import com.example.miniprojectdelivery.utill.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;


    /**
     * 음식점 리뷰 생성 메소드
     * @param userDetails 리뷰 남기는 유저의 정보
     * @param requestDto 리뷰 내용
     */
    @Secured("ROLE_CUSTOMER")
    @PostMapping
    public ReviewResponseDto createReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid ReviewCreateRequestDto requestDto) {
        ReviewResponseDto responseDto = reviewService.createReview(userDetails.getUser(), requestDto);
        return responseDto;
    }

    /**
     * 음식점 리뷰 수정 메소드
     * @param id 수정하려는 리뷰 ID
     * @param requestDto
     */
    @Secured("ROLE_CUSTOMER")
    @PutMapping("/{id}")
    public ReviewResponseDto updateReview(@PathVariable Long id,
            @RequestBody @Valid ReviewUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ReviewResponseDto responseDto = reviewService.updateReview(id, requestDto, userDetails.getUser());
        return responseDto;
    }

    /**
     * 음식점 리뷰 삭제 메소드
     * @param id 삭제하려는 리뷰 ID
     */
    @Secured("ROLE_CUSTOMER")
    @DeleteMapping("/{id}")
    public MessageResponseDto deleteReview(@PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        String message = reviewService.deleteReview(id, userDetails.getUser());
        return new MessageResponseDto(message);
    }
}
