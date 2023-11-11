package com.aw.arbanware.domain.order.repository;

import com.aw.arbanware.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    @Query(value = "SELECT NEXT VALUE FOR ORDERS_SEQUENCE", nativeQuery = true)
    Long findSequence();

}
