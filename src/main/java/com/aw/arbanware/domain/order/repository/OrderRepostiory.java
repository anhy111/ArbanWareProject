package com.aw.arbanware.domain.order.repository;

import com.aw.arbanware.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepostiory extends JpaRepository<Order, Long> {

    @Query(value = "SELECT NEXT VALUE FOR ORDERS_SEQUENCE", nativeQuery = true)
    Long findSequence();
}
