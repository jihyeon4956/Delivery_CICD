package com.example.miniprojectdelivery.controller;

import com.example.miniprojectdelivery.dto.order.OrderCustomerViewDto;
import com.example.miniprojectdelivery.dto.order.OrderViewDto;
import com.example.miniprojectdelivery.dto.restaurant.RestaurantRankDto;
import com.example.miniprojectdelivery.model.User;
import com.example.miniprojectdelivery.service.OrderService;
import com.example.miniprojectdelivery.service.RestaurantService;
import com.example.miniprojectdelivery.utill.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageHandler {

    private final OrderService orderService;
    private final RestaurantService restaurantService;

    @GetMapping("/")
    public String getIndexPage(Model model){
        List<RestaurantRankDto> ranks = restaurantService.getRestaurantRank();
        model.addAttribute("ranks",ranks);
        return "index";
    }

    // 사용자가 자신의 주문현황 확인하는 api
    @Secured("ROLE_CUSTOMER")
    @GetMapping("/view/myorderlists")
    public String getMyOrders(@AuthenticationPrincipal UserDetailsImpl userDetails
            , Model model) {
        User user = userDetails.getUser();
        List<OrderCustomerViewDto> orders = orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "customer";
    }


    @Secured("ROLE_OWNER")
    @GetMapping("/view/mypages")
    public String getMyRestaurant(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model
    ) {
        List<OrderViewDto> orders = null;
        if (userDetails.getUser().getRestaurant() != null) {
            orders = orderService.getRestaurantOrdersForView(userDetails.getUser());
            for (OrderViewDto order : orders) {
                System.out.println(order.getAddress());
            }
        }
        model.addAttribute("orders",orders);


        return "owner";
    }


    // 레스토랑 상세 페이지
    @GetMapping("/view/restaurants/{restaurantId}")
    public String getRestaurant(
            @PathVariable Long restaurantId,
            Model model
    ) {
        model.addAttribute("Menus", restaurantService.getRestaurant(restaurantId));
        return "store";
    }

    //주문 페이지
    @GetMapping("/view/search")
    public String search() {
        return "search";
    }
}
