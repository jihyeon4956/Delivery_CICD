package com.example.miniprojectdelivery.dto.order;

import com.example.miniprojectdelivery.model.Order;
import com.example.miniprojectdelivery.model.OrderMenu;
import lombok.Getter;

@Getter
public class OrderCustomerViewDto {

    private String restaurantName;
    private String menuName;
    private int count;
    private int totalPrice;
    private String state;

    public OrderCustomerViewDto(Order order) {
        OrderMenu orderMenu = order.getOrderMenus().get(0);
        this.restaurantName = orderMenu.getMenu().getRestaurant().getName();
        this.menuName = orderMenu.getMenu().getName();
        this.count = orderMenu.getCount();
        this.totalPrice = order.getTotalPrice();
        this.state = order.getState().toString();
    }
}
