package com.example.miniprojectdelivery.controller;


import com.example.miniprojectdelivery.dto.MessageResponseDto;
import com.example.miniprojectdelivery.dto.user.SignupRequestDto;
import com.example.miniprojectdelivery.dto.user.MailRequestDto;
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

    @GetMapping("/login-redirect")
    public String loginRedirect() {
        return "redirect:/api/users/login-page";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);
        return "redirect:/api/users/login-page";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<MessageResponseDto> deleteById(@PathVariable Long id){
        return userService.deleteById(id);
    }

    @PostMapping("/mailing")
    @ResponseBody
    public void mailing(@RequestBody @Valid MailRequestDto email){
        userService.Mailing(email);
    }

}
