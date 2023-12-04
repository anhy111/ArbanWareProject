package com.aw.arbanware.domain.order.service;

import com.aw.arbanware.domain.order.OrderStatus;
import com.aw.arbanware.domain.order.entity.Order;
import com.aw.arbanware.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepostiory;

    public Order orderRegister(Order order) {
        order.setId("arbanOrder" + todayToFolder() + orderRepostiory.findSequence());
        return orderRepostiory.save(order);
    }

    private String todayToFolder() {
        LocalDate now = LocalDate.now();
        return now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public Order orderSuccess(String orderId) {
        Order order = orderRepostiory.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문번호가 없습니다."));
        order.setStatus(OrderStatus.COMPLETE_PAYMENT);
        return order;
    }

    public Order orderCancel(String orderId) {
        Order order = orderRepostiory.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문번호가 없습니다."));
        order.setStatus(OrderStatus.EXPIRES_PAYMENT);
        return order;
    }

    public Order orderFind(String orderId) {
        Order order = orderRepostiory.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문번호가 없습니다."));
        return order;
    }

    public List<Order> orderDetails(Long memberId) {
        return orderRepostiory.findByMemberId(memberId);
    }

}
