package com.example.miniprojectdelivery.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class MailRequestDto {

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;
}
