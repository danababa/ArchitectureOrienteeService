package com.aos.tp.orderService.repository;

import com.aos.tp.orderService.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository  extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long orderId);

    Optional<Order> findByUserId(Long userId);

    Optional<Order> findByProduct(String product);

    List<Order> findAllByUserId (Long userId);
}
