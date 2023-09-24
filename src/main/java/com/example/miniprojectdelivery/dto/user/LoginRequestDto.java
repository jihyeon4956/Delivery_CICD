package com.example.miniprojectdelivery.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginRequestDto {

    @Pattern(
            regexp = "^[a-z][a-z0-9]{4,20}+$",
            message = "username은  최소 4자 이상, 20자 이하 이어야 합니다."
    )
    private String username;

    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "password는  최소 8자 이상, 20자 이하 이어야 합니다."
    )
    private String password;
}
