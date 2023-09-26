package com.example.miniprojectdelivery.service;

import com.example.miniprojectdelivery.dto.MessageResponseDto;
import com.example.miniprojectdelivery.dto.restaurant.RestaurantRankDto;
import com.example.miniprojectdelivery.dto.restaurant.RestaurantRequestDto;
import com.example.miniprojectdelivery.dto.restaurant.RestaurantResponseDto;
import com.example.miniprojectdelivery.model.Restaurant;
import com.example.miniprojectdelivery.model.User;
import com.example.miniprojectdelivery.repository.RedisRepository;
import com.example.miniprojectdelivery.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.Optional;

import com.example.miniprojectdelivery.utill.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RedisRepository redisRepository;


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

    // 오너 토큰으로 업장 조회
    public RestaurantResponseDto OwnerSearchRestaurant(User user) {
        Restaurant restaurant = findRestaurant(user.getRestaurant().getId());
        return new RestaurantResponseDto(restaurant);
    }

    public List<RestaurantRankDto> getRestaurantRank() {
        List<RestaurantRankDto> restaurantRank = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            ObjectMapper objectMapper = new ObjectMapper();
            String stringRank = redisRepository.getValue("restaurant:rank" + i);
            if (stringRank != null) {
                try {
                    RestaurantRankDto rankDto = objectMapper.readValue(stringRank, RestaurantRankDto.class);
                    restaurantRank.add(rankDto);
                } catch (JsonProcessingException e) {

                    throw new RuntimeException("음식점 랭킹을 불러오는데 실패하였습니다.");
                }
            }
        }
        return restaurantRank;
    }
}
