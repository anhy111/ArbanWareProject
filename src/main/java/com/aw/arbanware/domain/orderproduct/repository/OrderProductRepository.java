package com.aw.arbanware.domain.orderproduct.repository;

import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
