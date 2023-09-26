package com.example.miniprojectdelivery.controller;

import com.example.miniprojectdelivery.dto.menu.MenuCreateRequestDto;
import com.example.miniprojectdelivery.dto.menu.MenuResponseDto;
import com.example.miniprojectdelivery.dto.menu.MenuUpdateRequestDto;
import com.example.miniprojectdelivery.dto.MessageResponseDto;
import com.example.miniprojectdelivery.model.User;
import com.example.miniprojectdelivery.service.MenuService;
import com.example.miniprojectdelivery.utill.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    /**
     * 메뉴 생성 메소드 - 자신의 음식점에만 메뉴 생성 가능
     *
     * @param image        생성하려는 메뉴의 이미지
     * @param name         생성하려는 메뉴의 이름
     * @param cost         생성하려는 메뉴의 가격
     */
    @Secured("ROLE_OWNER")
    @PostMapping
    public String createMenu(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestParam("image") MultipartFile image,
//            @Valid @RequestParam("restaurantId") Long restaurantId,
            @Valid @RequestParam("name") String name,
            @Valid @RequestParam("cost") int cost) throws IOException {
        User user = userDetails.getUser();
//        return menuService.createMenu(user,image, restaurantId, name, cost);
        menuService.createMenu(userDetails.getUser(),image, name, cost);
        return "redirect:/view/mypages";
    }

    @ResponseBody
    @GetMapping
    public List<MenuResponseDto> getMenus(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return menuService.getMenus(userDetails.getUser());
    }

    @ResponseBody
    @GetMapping("/{id}")
    public MenuResponseDto selectMenu(@PathVariable Long id){
        return menuService.selectMenu(id);
    }

    /**
     * 메뉴 정보 수정 메소드
     *
     * @param id 수정할 메뉴의 id
     * @param newImage 수정할 메뉴의 이미지
     * @param name 수정 후 메뉴의 이름
     * @param cost 수정 후 메뉴의 가격
     */

    @ResponseBody
    @Secured("ROLE_OWNER")
    @PutMapping("/{id}")
    public MenuResponseDto updateMenu(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @RequestParam(value = "image") MultipartFile newImage,
            @RequestParam("name") String name,
            @RequestParam("cost") int cost) throws IOException {
        User user = userDetails.getUser();
        return menuService.update(user, id, newImage, name, cost);
    }

    /**
     * 메뉴 정보 삭제 메소드
     *
     * @param id 삭제하려는 메뉴 ID
     */

    @ResponseBody
    @Secured("ROLE_OWNER")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteMenu(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        User user = userDetails.getUser();
        return ResponseEntity.ok().body(menuService.deleteMenu(user, id));
    }
}
