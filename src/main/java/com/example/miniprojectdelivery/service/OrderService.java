package com.example.miniprojectdelivery.service;

import com.example.miniprojectdelivery.dto.order.OrderCreateRequestDto;
import com.example.miniprojectdelivery.dto.order.OrderResponseDto;
import com.example.miniprojectdelivery.model.*;
import com.example.miniprojectdelivery.repository.MenuRepository;
import com.example.miniprojectdelivery.repository.OrderRepository;
import com.example.miniprojectdelivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final NotificationService notificationService;

    /**
     * 유저 주문 조회
     * @param user 조회 대상 유저
     * @return 유저 주문 리스트
     */
    public List<OrderResponseDto> getOrdersByUser(User user) {
        return orderRepository.findAllByUser(user).stream()
                .map(OrderResponseDto::new).toList();
    }

    /**
     * 주문 단일 조회
     * @param orderId 주문 ID
     * @return 주문 정보
     */
    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("해당 주문이 존재하지 않습니다.");
                }
        );
        return new OrderResponseDto(order);
    }

    public List<OrderResponseDto> getOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findOrdersByRestaurantId(restaurantId).stream()
                .map(OrderResponseDto::new).toList();
    }


    /**
     * 주문 생성
     */
    @Transactional
    public OrderResponseDto createOrder(Long userId, OrderCreateRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("해당 유저를 찾을 수 없습니다.");
                }
        );
        Menu menu = menuRepository.findById(requestDto.getMenuId()).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("주문하려는 메뉴를 찾을 수 없습니다.");
                }
        );

        //배송정보 생성
        Delivery delivery = new Delivery(); //주문 배송정보 생성
        delivery.setAddress(user.getAddress()); //유저의 배송정보 저장

        //주문상품 생성
        OrderMenu orderMenu = OrderMenu.createOrderItem(menu, menu.getCost(), requestDto.getCount());

        //주문 생성
        Order order = Order.createOrder(user, delivery, orderMenu);

        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    /**
     * 주문 배송
     */
    @Transactional
    public void deliveryOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("배송하려는 주문을 찾을 수 없습니다.");
                }
        );

        notificationService.send("원하는 대상의 Username", "배달 완료했습니다.", "chat");

        order.delivery();
    }
}
