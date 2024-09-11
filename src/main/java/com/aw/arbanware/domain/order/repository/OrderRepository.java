package com.aw.arbanware.domain.order.repository;

import com.aw.arbanware.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    @Query(value = "SELECT nextVal(\"ORDERS_SEQUENCE\") FROM DUAL", nativeQuery = true)
    Long findSequence();

    @Query("select distinct o from Order o left join fetch o.orderProduct op left join fetch op.productInfo pi left join fetch pi.product p where o.member.id = :memberId order by o.orderDate desc")
    List<Order> findByMemberId(@Param("memberId")Long memberId);
}
