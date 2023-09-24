package com.example.miniprojectdelivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    // 반환사이트 확인용 임시 Controller
    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/store")
    public String store() {
        return "store";
    }
    @GetMapping("/customer")
    public String team() {
        return "customer";
    }
    @GetMapping("/owner")
    public String service() {
        return "owner";
    }


}
