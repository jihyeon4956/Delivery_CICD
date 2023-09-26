package com.example.miniprojectdelivery.service;

import com.example.miniprojectdelivery.dto.order.OrderCreateRequestDto;
import com.example.miniprojectdelivery.dto.order.OrderCustomerViewDto;
import com.example.miniprojectdelivery.dto.order.OrderResponseDto;
import com.example.miniprojectdelivery.dto.order.OrderViewDto;
import com.example.miniprojectdelivery.model.*;
import com.example.miniprojectdelivery.repository.MenuRepository;
import com.example.miniprojectdelivery.repository.OrderRepository;
import com.example.miniprojectdelivery.repository.RestaurantRepository;
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
    private final RestaurantRepository restaurantRepository;

    /**
     * 유저 주문 조회
     * @param user 조회 대상 유저
     * @return 유저 주문 리스트
     */
    public List<OrderCustomerViewDto> getOrdersByUser(User user) {
        return orderRepository.findAllByUser(user).stream().map(OrderCustomerViewDto::new).toList();
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

    public List<OrderResponseDto> getOrdersByRestaurantId(User user) {

        Restaurant restaurant = restaurantRepository.findById(user.getRestaurant().getId()).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 음식점이 존재하지 않습니다.");
        });
        if (!restaurant.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("자신의 음식점의 주문만 조회할 수 있습니다.");
        }

        return orderRepository.findOrdersByRestaurantId(user.getRestaurant().getId()).stream()
                .map(OrderResponseDto::new).toList();
    }

    @Transactional
    public List<OrderViewDto> getRestaurantOrdersForView(User user) {
        Long restaurantId = user.getRestaurant().getId();

        return orderRepository.findOrdersByRestaurantId(restaurantId).stream()
                .map(OrderViewDto::new).toList();
    }

    /**
     * 주문 생성
     */
    @Transactional
    public OrderResponseDto createOrder(User user, OrderCreateRequestDto requestDto) {
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

        String owenrname = user.getUsername();
        //주문 생성
        Order order = Order.createOrder(user, delivery, orderMenu);
        System.out.println(orderMenu.getMenu().getRestaurant().getUser().getUsername());
        notificationService.send(orderMenu.getMenu().getRestaurant().getUser().getUsername(), "주문이 들어왔습니다.", "chat");

        orderRepository.save(order);
        return new OrderResponseDto(order);
    }

    /**
     * 주문 배송
     */
    @Transactional
    public void deliveryOrder(User user, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("배송하려는 주문을 찾을 수 없습니다.");
                }
        );

        OrderMenu orderMenu = order.getOrderMenus().get(0);
        Long ownerId = orderMenu.getMenu().getRestaurant().getUser().getId();

        if (!user.getId().equals(ownerId)) {
            throw new IllegalArgumentException("자신의 음식점의 주문만 배달 할 수 있습니다.");
        }

        notificationService.send(order.getUser().getUsername(), "배달 완료했습니다.", "chat");

        order.delivery();
    }
}
