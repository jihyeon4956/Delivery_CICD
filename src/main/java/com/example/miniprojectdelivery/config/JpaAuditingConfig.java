package com.example.miniprojectdelivery.config;

import com.example.miniprojectdelivery.model.User;
import com.example.miniprojectdelivery.utill.jwt.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<User> auditorAware() {
        return SecurityUtil::getPrincipal;
    }
}
