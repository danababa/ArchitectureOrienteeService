package com.aos.tp.orderService.controller;


import com.aos.tp.orderService.model.Order;
import com.aos.tp.orderService.service.OrderService;
import com.aos.tp.userService.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * Create order
     * @param order model
     * @return order
     */
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@Valid  @RequestBody Order order) {
        return ResponseEntity.ok().body(orderService.createOrder(order));
    }

    /**
     * Get order by id
     * @param orderId of order
     * @return order
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(orderService.getOrderById(orderId));
    }

    /**
     * Get all orders
     * @return list of orders
     */
    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    /**
     * Get order by user id
     * @param userId of user
     * @return list of orders
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrderByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(orderService.getOrdersByUserId(userId));
    }

    /**
     * Update order
     * @param orderId of order
     * @param order model
     * @return updated order
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
        return ResponseEntity.ok().body(orderService.updateOrder(orderId, order));
    }

    /**
     * Delete order
     * @param orderId of order
     */
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }

}
