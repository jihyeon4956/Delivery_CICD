package com.example.miniprojectdelivery.repository;

import com.example.miniprojectdelivery.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByRestaurantId(Long id);
}
