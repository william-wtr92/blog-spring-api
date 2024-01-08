package com.springboot.blog.controller;

import com.springboot.blog.dto.OrderRequest;
import com.springboot.blog.dto.OrderResponse;
import com.springboot.blog.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse response = orderService.placeOrder(orderRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
