package com.aw.arbanware.domain.order.service;

import com.aw.arbanware.domain.order.entity.Order;
import com.aw.arbanware.domain.order.repository.OrderRepostiory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepostiory orderRepostiory;

    public Order orderRegister(Order order) {
        order.setId("arbanwareOrder_" + orderRepostiory.findSequence());
        return orderRepostiory.save(order);
    }


}
