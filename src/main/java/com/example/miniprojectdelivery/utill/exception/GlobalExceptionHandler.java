package com.example.miniprojectdelivery.utill.exception;

import com.example.miniprojectdelivery.dto.MessageResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public MessageResponseDto handlerIllegalArgumentException(HttpServletResponse response,
            IllegalArgumentException ex) {
        log.error(ex.getMessage());
        response.setStatus(400);
        String msg = ex.getMessage();
        return new MessageResponseDto(msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MessageResponseDto handlerMethodArgumentNotValidException(HttpServletResponse response,
            MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        response.setStatus(400);
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new MessageResponseDto(msg);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public MessageResponseDto ExHandler(Exception ex) {
        log.error(ex.getMessage());
        return new MessageResponseDto(ex.getMessage());
    }
}
