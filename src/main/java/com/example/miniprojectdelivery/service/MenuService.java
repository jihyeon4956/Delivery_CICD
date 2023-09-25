package com.example.miniprojectdelivery.service;

import com.example.miniprojectdelivery.dto.menu.MenuResponseDto;
import com.example.miniprojectdelivery.dto.menu.MenuCreateRequestDto;
import com.example.miniprojectdelivery.dto.menu.MenuUpdateRequestDto;
import com.example.miniprojectdelivery.dto.MessageResponseDto;
import com.example.miniprojectdelivery.model.Menu;
import com.example.miniprojectdelivery.model.Restaurant;
import com.example.miniprojectdelivery.repository.MenuRepository;
import com.example.miniprojectdelivery.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuResponseDto createMenu(MenuCreateRequestDto requestDto) {
        Restaurant restaurant = restaurantRepository.findById(requestDto.getRestaurantId()).orElseThrow(
                ()-> new IllegalArgumentException("음식점을 찾을 수 없습니다"));
        Menu menu = menuRepository.save(new Menu(requestDto, restaurant));
       return new MenuResponseDto(menu);
    }

    public List<MenuResponseDto> getMenus() {
        List<Menu> menuList = menuRepository.findAll();
        return menuList.stream().map(MenuResponseDto::new).toList();
    }

    public MenuResponseDto selectMenu(Long id) {
        Menu menu = findMenu(id);
        return new MenuResponseDto(menu);
    }

    @Transactional
    public MenuResponseDto update(Long id, MenuUpdateRequestDto requestDto) {
        Menu menu = findMenu(id);
        menu.update(requestDto);
        return new MenuResponseDto(menu);
    }

    public MessageResponseDto deleteMenu(Long id) {
        Menu menu = findMenu(id);
        menuRepository.delete(menu);
        return new MessageResponseDto("메뉴 삭제 성공!");
    }
    // 선택한 메뉴가 존재하는지 검사하는 메서드
    private Menu findMenu(Long id) {
        return menuRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메뉴는 존재하지 않습니다."));
    }


}
