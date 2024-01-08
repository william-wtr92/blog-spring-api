package com.springboot.blog.service;

import com.springboot.blog.dto.OrderRequest;
import com.springboot.blog.dto.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);
}
