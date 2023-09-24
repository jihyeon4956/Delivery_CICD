package com.example.miniprojectdelivery.service;

import com.example.miniprojectdelivery.dto.MessageResponseDto;
import com.example.miniprojectdelivery.dto.restaurant.RestaurantRequestDto;
import com.example.miniprojectdelivery.dto.restaurant.RestaurantResponseDto;
import com.example.miniprojectdelivery.model.Restaurant;
import com.example.miniprojectdelivery.model.User;
import com.example.miniprojectdelivery.repository.RestaurantRepository;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class
RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // 업장 생성
    public RestaurantResponseDto restaurantCreate(User user, RestaurantRequestDto restaurantRequestDto ) {

        Optional<Restaurant> restaurant = restaurantRepository.findByUser(user);
        if(restaurant.isPresent()) {
            throw new IllegalArgumentException("하나의 업장만 개설 가능합니다.");
        }

        Restaurant newRestaurant = new Restaurant(restaurantRequestDto);
        newRestaurant.addUser(user);
        Restaurant saveRestaurant = restaurantRepository.save(newRestaurant);
        return new RestaurantResponseDto(saveRestaurant);
    }

    // 업장 수정
    @Transactional
    public RestaurantResponseDto restaurantUpdate(Long id, RestaurantRequestDto restaurantRequestDto) {
        Restaurant restaurant = findRestaurant(id);
        restaurant.update(restaurantRequestDto);
        return new RestaurantResponseDto(restaurant);
    }

    // 업장 삭제
    public ResponseEntity<MessageResponseDto> restaurantDelete(Long id) {
        Restaurant restaurant = findRestaurant(id);
        restaurantRepository.delete(restaurant);
        MessageResponseDto msg = new MessageResponseDto("업장 정보가 삭제되었습니다.");
        return ResponseEntity.status(200).body(msg);
    }

    // 업장 조회
    private Restaurant findRestaurant(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 업장이 없습니다."));
    }


    // 업장 상세 조회
    public RestaurantResponseDto getRestaurant(Long id) {
        Restaurant restaurant = findRestaurant(id);
        return new RestaurantResponseDto(restaurant);
    }

    // 업장 키워드 검색
    public List<RestaurantResponseDto> searchRestaurant(String keyword) {
        return restaurantRepository.findByNameContaining(keyword).stream().map(RestaurantResponseDto::new).toList();
    }

    // 랭킹 Top 4 조회
    public List<Restaurant> updateRanking() {
        return restaurantRepository.findTop4ByOrderByTotalSalesDesc();
    }
}
