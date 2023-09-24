package com.example.miniprojectdelivery.service;

import com.example.miniprojectdelivery.dto.review.ReviewCreateRequestDto;
import com.example.miniprojectdelivery.dto.review.ReviewResponseDto;
import com.example.miniprojectdelivery.dto.review.ReviewUpdateRequestDto;
import com.example.miniprojectdelivery.model.Restaurant;
import com.example.miniprojectdelivery.model.Review;
import com.example.miniprojectdelivery.model.User;
import com.example.miniprojectdelivery.repository.RestaurantRepository;
import com.example.miniprojectdelivery.repository.ReviewRepository;
import com.example.miniprojectdelivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public ReviewResponseDto createReview(User user, ReviewCreateRequestDto requestDto) {
        Long restaurantId = requestDto.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("해당 ID의 음식점이 존재하지 않습니다.");
                }
        );

        Review review = new Review();
        review.addContentAndStars(requestDto);
        review.addRestaurant(restaurant);
        review.addUser(user);

        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, ReviewUpdateRequestDto requestDto, User user) {
        Review review = findReview(reviewId);

        if (!review.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("자신의 리뷰만 수정 가능합니다.");
        }

        review.updateContentAndStars(requestDto);

        ReviewResponseDto responseDto = new ReviewResponseDto(review);
        return responseDto;
    }

    public String  deleteReview(Long id, User user) { // 임시로 String 으로 반환
        Review review = findReview(id);

        if (!review.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("자신의 리뷰만 삭제 가능합니다.");
        }

        reviewRepository.delete(review);
        return "리뷰 삭제에 성공 했습니다.";

    }

    private Review findReview(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 id의 리뷰가 존재하지 않습니다. Review ID: " + id);
        });
    }
}
