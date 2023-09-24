package com.example.miniprojectdelivery.utill.scheduler;

import com.example.miniprojectdelivery.model.Restaurant;
import com.example.miniprojectdelivery.repository.RedisRepository;
import com.example.miniprojectdelivery.repository.RestaurantRepository;
import com.example.miniprojectdelivery.service.RestaurantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final RestaurantService restaurantService;
    private final RedisRepository redisRepository;

    // 초, 분, 시, 일, 주, 월 순서
    @Scheduled(cron = "0 0 1 * * *") // 매일 새벽 1시
    public void updateRank() {
        log.info("랭킹 업데이트 실행");
        List<Restaurant> restaurantRanking = restaurantService.updateRanking();

        int rank = 1;
        for (Restaurant restaurant : restaurantRanking) {
            String restaurantName = restaurant.getName();
            log.info(restaurantName);
            redisRepository.save("restaurant:rank" + rank, restaurantName);
            ++rank;
        }
    }
}