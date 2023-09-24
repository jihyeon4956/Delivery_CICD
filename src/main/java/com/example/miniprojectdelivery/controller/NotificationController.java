package com.example.miniprojectdelivery.controller;

import com.example.miniprojectdelivery.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Log4j2
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping(value = "/subscribe/{token}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable String token, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId){
        return notificationService.subscribe(token, lastEventId);
    }

    @GetMapping("/noti/{userId}")
    public void sendNoti(@PathVariable String userId){
        notificationService.send(userId, "알람입니다.","chat");
    }
}