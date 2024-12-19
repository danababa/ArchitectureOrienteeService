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

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@Valid  @RequestBody Order order) {
        return ResponseEntity.ok().body(orderService.createOrder(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(orderService.getOrderById(orderId));
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrderByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(orderService.getOrdersByUserId(userId));
    }
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
        return ResponseEntity.ok().body(orderService.updateOrder(orderId, order));
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }

}
