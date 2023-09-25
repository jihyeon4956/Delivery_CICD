package com.example.miniprojectdelivery.controller;


import com.example.miniprojectdelivery.dto.MessageResponseDto;
import com.example.miniprojectdelivery.dto.user.SignupRequestDto;
import com.example.miniprojectdelivery.model.Mail;
import com.example.miniprojectdelivery.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<MessageResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<MessageResponseDto> deleteById(@PathVariable Long id){
        return userService.deleteById(id);
    }

    @PostMapping("/mailing")
    @ResponseBody
    public void mailing(@RequestBody Mail email){
        userService.Mailing(email);
    }

}
