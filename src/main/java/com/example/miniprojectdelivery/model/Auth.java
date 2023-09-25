package com.example.miniprojectdelivery.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    @NotBlank(message = "이메일은 필수 값 입니다.")
    private String email;

    @Column(name = "password", nullable = false)
    private int paswword;

    public Auth(String email, int password) {
        this.email = email;
        this.paswword = password;
    }
}
