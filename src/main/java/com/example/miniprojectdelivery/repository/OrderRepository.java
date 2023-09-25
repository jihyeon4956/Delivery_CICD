package com.example.miniprojectdelivery.repository;

import com.example.miniprojectdelivery.model.Order;
import com.example.miniprojectdelivery.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderMenus om WHERE om.menu.restaurant.id = :restaurantId")
    List<Order> findOrdersByRestaurantId(Long restaurantId);
}
