package com.example.miniprojectdelivery.model;

import com.example.miniprojectdelivery.enums.OrderStateEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;  //배송 정보

    private LocalDateTime orderDate;  //주문 시간

    @Enumerated(value = EnumType.STRING)
    private OrderStateEnum state;  //주문 상태 [READY, COMPLETE]

    public void addUser(User user) {
        this.user = user;
    }
    public void addOrderMenu(OrderMenu orderMenu) {
        this.orderMenus.add(orderMenu);
        orderMenu.addOrder(this);
    }

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.addOrder(this);
    }

    public void setState(OrderStateEnum state) {
        this.state = state;
    }

    public void setOrderDate(LocalDateTime date) {
        this.orderDate = date;
    }

    /**
     * 주문 생성 메소드
     *
     * @param user 주문 고객
     * @param delivery 주문 배달 정보
     * @param orderMenus 주문 메뉴
     * @return 생성된 주문 반환
     */
    public static Order createOrder(User user, Delivery delivery, OrderMenu... orderMenus) {
        Order order = new Order();
        order.addUser(user);
        order.addDelivery(delivery);
        for (OrderMenu orderMenu : orderMenus) {
            order.addOrderMenu(orderMenu);
        }
        order.setState(OrderStateEnum.READY);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    /**
     * 주문 배송 메소드
     */
    public void delivery() {
        this.setState(OrderStateEnum.COMPLETE); // 주문 배송 완료
        for (OrderMenu orderMenu : orderMenus) {  //메뉴별 판매액 가게 매출에 더하기
            Restaurant restaurant = orderMenu.getMenu().getRestaurant();
            restaurant.addTotalSales(orderMenu.getTotalPrice());
        }
    }

    /**
     * 전체 주문 가격 조회
     * @return 전체 주문 가격
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderMenu orderMenu : orderMenus) {
            totalPrice += orderMenu.getTotalPrice();
        }
        return totalPrice;
    }
}
