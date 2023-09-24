package com.example.miniprojectdelivery.dto.order;

import com.example.miniprojectdelivery.enums.OrderStateEnum;
import com.example.miniprojectdelivery.model.Address;
import com.example.miniprojectdelivery.model.Delivery;
import com.example.miniprojectdelivery.model.Menu;
import com.example.miniprojectdelivery.model.Order;
import com.example.miniprojectdelivery.model.OrderMenu;
import com.example.miniprojectdelivery.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    private Long id;

    private List<OrderMenuResponseDto> menuList;

    private int totalPrice;

    private Address address;  //주소 정보

    private LocalDateTime orderDate;  //주문 시간

    private OrderStateEnum state;  //주문 상태 [READY, COMPLETE]

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.menuList = order.getOrderMenus().stream().map((orderMenu) ->
                new OrderMenuResponseDto(orderMenu.getMenu(), orderMenu.getCount())
        ).toList();
        this.totalPrice = order.getTotalPrice();
        this.address = order.getUser().getAddress();
        this.orderDate = order.getOrderDate();
        this.state = order.getState();

    }
}
