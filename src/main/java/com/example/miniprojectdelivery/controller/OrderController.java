package com.example.miniprojectdelivery.controller;

import com.example.miniprojectdelivery.dto.MessageResponseDto;
import com.example.miniprojectdelivery.dto.order.OrderCreateRequestDto;
import com.example.miniprojectdelivery.dto.order.OrderResponseDto;
import com.example.miniprojectdelivery.model.User;
import com.example.miniprojectdelivery.service.OrderService;
import com.example.miniprojectdelivery.utill.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * 자신의 주문 내역 조회
     *
     * @param userDetails
     * @return
     */
    @GetMapping
    public List<OrderResponseDto> getOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return orderService.getOrdersByUser(user);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    /**
     * 사장님의 자신의 음식점 고객들 주문 조회 메소드
     *
     * @param restaurantId 음식점 ID
     * @return
     */
    @Secured("ROLE_OWNER")
    @GetMapping("/restaurants/{restaurantId}")
    public List<OrderResponseDto> getOrdersByRestaurantId(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long restaurantId) {
        User user = userDetails.getUser();
        return orderService.getOrdersByRestaurantId(user, restaurantId);
    }

    /**
     * 고객의 주문 생성 메소드
     *
     * 고객만 주문 생성 가능
     *
     * @param userDetails 주문하는 고객의 정보
     * @param requestDto  주문 내역
     */
    @Secured("ROLE_CUSTOMER")
    @PostMapping
    public OrderResponseDto createOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody
            OrderCreateRequestDto requestDto) {
        User user = userDetails.getUser();
        return orderService.createOrder(user, requestDto);
    }

    /**
     * 사장님의 주문 배달 완료 처리
     * <p>
     * 자신의 가게만 주문 배달 완료 처리 가능
     *
     * @param orderId 배달 완료 처리할 주문 ID
     * @return
     */
    @Secured("ROLE_OWNER")
    @PostMapping("{orderId}/deliver")
    public MessageResponseDto deliveryOrder(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long orderId) {
        User owner = userDetails.getUser();
        orderService.deliveryOrder(owner,orderId);
        return new MessageResponseDto("배달 완료");
    }

}
