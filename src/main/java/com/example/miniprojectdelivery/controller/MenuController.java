package com.example.miniprojectdelivery.controller;

import com.example.miniprojectdelivery.dto.menu.MenuCreateRequestDto;
import com.example.miniprojectdelivery.dto.menu.MenuResponseDto;
import com.example.miniprojectdelivery.dto.menu.MenuUpdateRequestDto;
import com.example.miniprojectdelivery.dto.MessageResponseDto;
import com.example.miniprojectdelivery.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public MenuResponseDto createMenu(@Valid @RequestBody MenuCreateRequestDto requestDto){
        return menuService.createMenu(requestDto);
    }

    @GetMapping
    public List<MenuResponseDto> getMenus(){
        return menuService.getMenus();
    }

    @GetMapping("/{id}")
    public MenuResponseDto selectMenu(@PathVariable Long id){
        return menuService.selectMenu(id);
    }

    @PutMapping("/{id}")
    public MenuResponseDto updateMenu(
            @PathVariable Long id,
            @Valid @RequestBody MenuUpdateRequestDto requestDto){
        return menuService.update(id, requestDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteMenu(@PathVariable Long id){
        return ResponseEntity.ok().body(menuService.deleteMenu(id));
    }
}
