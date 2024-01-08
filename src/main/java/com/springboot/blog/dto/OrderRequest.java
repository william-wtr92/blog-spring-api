package com.springboot.blog.dto;

import com.springboot.blog.entity.Order;
import com.springboot.blog.entity.Payment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Order order;
    private Payment payment;
}
