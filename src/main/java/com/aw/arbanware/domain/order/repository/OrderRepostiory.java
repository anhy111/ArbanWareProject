package com.aw.arbanware.domain.order.repository;

import com.aw.arbanware.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepostiory extends JpaRepository<Order, Long> {
}
