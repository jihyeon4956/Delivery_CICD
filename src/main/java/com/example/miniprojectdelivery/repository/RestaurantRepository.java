package com.example.miniprojectdelivery.repository;

import com.example.miniprojectdelivery.model.Restaurant;
import com.example.miniprojectdelivery.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByNameContaining(String keyword);

    Optional<Restaurant> findByUser(User user);

    // totalSales를 기준으로 상위 4개 음식점을 가져오는 쿼리
    @Query("SELECT r FROM Restaurant r ORDER BY r.totalSales DESC limit 4")
    List<Restaurant> findTop4ByOrderByTotalSalesDesc();
}
