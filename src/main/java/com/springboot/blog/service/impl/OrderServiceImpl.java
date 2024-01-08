package com.springboot.blog.service.impl;

import com.springboot.blog.dto.OrderRequest;
import com.springboot.blog.dto.OrderResponse;
import com.springboot.blog.entity.Order;
import com.springboot.blog.entity.Payment;
import com.springboot.blog.exception.PaymentException;
import com.springboot.blog.repository.OrderRepository;
import com.springboot.blog.repository.PaymentRepository;
import com.springboot.blog.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public OrderServiceImpl(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    /* @Transactional is here to execute the operations in method in 1 time, orderRepository.save(order) &
    orderRepository.save(order) are executed in same operation not 2 separated */
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        // Get the order from the body of request
        Order order = orderRequest.getOrder();
        // Set randomTrackId to the order
        order.setOrderTrackingNumber(UUID.randomUUID().toString());
        // Set is statut on IN PROGRESS
        order.setStatus("In Progress");
        // Save the order
        orderRepository.save(order);

        // Get the payment from the body of request
        Payment payment = orderRequest.getPayment();

        // Check the type of Credit Card
        if(!payment.getType().equals("DEBIT")){
            throw new PaymentException("Payment Card Type not supported");
        }

        // Add a linked orderId to the Payment
        payment.setOrderId(order.getId());
        // Save the payment into database
        orderRepository.save(order);

        // Creation of the back response
        OrderResponse response = new OrderResponse();
        response.setOrderTrackingNumber(order.getOrderTrackingNumber());
        response.setStatus(order.getStatus());
        response.setMessage("Success");

        return response;
    }
}
