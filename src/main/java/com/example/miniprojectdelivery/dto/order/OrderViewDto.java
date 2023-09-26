package com.example.miniprojectdelivery.dto.order;

import com.example.miniprojectdelivery.enums.OrderStateEnum;
import com.example.miniprojectdelivery.model.Order;
import com.example.miniprojectdelivery.model.OrderMenu;
import lombok.Getter;

import java.net.URL;

@Getter
public class OrderViewDto {

    private Long id;
    private String menuName;
    private int totalPrice;
    private int count;
    private String customerName;
    private String address;
    private String state;

    public OrderViewDto(Order order) {
        OrderMenu orderMenu = order.getOrderMenus().get(0);
        this.id = order.getId();
        this.menuName = orderMenu.getMenu().getName();
        this.totalPrice = order.getTotalPrice();
        this.count = orderMenu.getCount();
        this.customerName = order.getUser().getUsername();
        this.address = order.getDelivery().getAddress().getAddress();
        this.state = order.getState().toString();

    }

}
