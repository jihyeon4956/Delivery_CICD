package com.example.miniprojectdelivery.repository;

import com.example.miniprojectdelivery.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AuthRepository  extends JpaRepository<Auth, Long> {

    Optional<Auth> findByEmail(String email);

}
