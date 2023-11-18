package com.aw.arbanware.domain.orderproduct.repository;

import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("select op from OrderProduct op join fetch op.order o where op.order.id = :orderId")
    List<OrderProduct> findByOrderId(@Param("orderId")String orderId);

    @Query("select op.id from OrderProduct op join op.order o where o.member.id = :memberId")
    List<Long> findByMemberId(@Param("memberId") Long memberId);

    @Query("select op from OrderProduct op join op.productInfo pi join pi.product p " +
            "where p.id = :productId and op.id in :orderProductIds order by op.registrationTime desc")
    List<OrderProduct> findByProductIdAndOrderProductIds(@Param("productId") Long productId, @Param("orderProductIds") List<Long> orderProductIds , Pageable pageable);

}
