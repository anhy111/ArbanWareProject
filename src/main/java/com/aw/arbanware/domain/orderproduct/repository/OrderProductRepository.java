package com.aw.arbanware.domain.orderproduct.repository;

import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("select op from OrderProduct op join fetch op.order o where op.order.id = :orderId")
    OrderProduct findByOrderId(@Param("orderId")String orderId);

}
