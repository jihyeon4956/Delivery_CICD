package com.example.miniprojectdelivery.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor
public class SignupRequestDto {

    @Pattern(
            regexp = "^[a-z][a-z0-9]{4,20}+$",
            message = "username은  최소 4자 이상, 20자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 한다."
    )
    private String username;

    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "password는  최소 8자 이상, 20자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 한다."
    )
    private String password;

    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "password는  최소 8자 이상, 20자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 한다."
    )
    private String checkpassword;   // 패스워드 확인

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    private int checkemail;     // email로 보낸 코드

    private String address; // 지번 주소
    private String zipcode; // 주소 번호

    private boolean admin = false;
    private String adminToken ="";

    private boolean customer = false;

}

