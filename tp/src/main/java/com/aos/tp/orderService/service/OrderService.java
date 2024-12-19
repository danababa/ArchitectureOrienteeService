package com.aos.tp.orderService.service;

import com.aos.tp.orderService.model.Order;
import com.aos.tp.orderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Create order method
     * @param newOrder model
     * @return added order
     */
    public Order createOrder(Order newOrder) {
        if (newOrder.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if(orderRepository.findByUserId(newOrder.getUserId()).isPresent()
                && orderRepository.findByProduct(newOrder.getProduct()).isPresent()){
            throw new RuntimeException("Order already exists");
        }

        return orderRepository.save(newOrder);
    }

    /**
     * Get order by id
     * @param orderId of order
     * @return order
     */
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /**
     * Get all orders
     * @return list of orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Update order
     * @param orderId of order
     * @param updatedOrder model
     * @return updated order
     */
    public Order updateOrder(Long orderId, Order updatedOrder) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setQuantity(updatedOrder.getQuantity());
        return orderRepository.save(order);
    }

    /**
     * Delete order
     * @param orderId of order
     */
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

}
