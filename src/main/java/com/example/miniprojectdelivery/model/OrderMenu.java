package com.example.miniprojectdelivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int orderPrice; //주문 가격
    private int count;  //주문 수량


    public static OrderMenu createOrderItem(Menu menu, int orderPrice, int count) {
        OrderMenu orderMenu = new OrderMenu();
        orderMenu.addMenu(menu);
        orderMenu.setOrderPrice(orderPrice);
        orderMenu.setCount(count);

        return orderMenu;
    }


    /**
     * 주문한 메뉴의 가격 합
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addOrder(Order order) {
        this.order = order;
    }

    public void addMenu(Menu menu) {
        this.menu = menu;
    }

}
