package com.aw.arbanware.domain.orderproduct.service;

import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import com.aw.arbanware.domain.orderproduct.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    public List<OrderProduct> orderProductSaveAll(List<OrderProduct> orderProduct) {
        List<OrderProduct> orderProducts = orderProductRepository.saveAll(orderProduct);
        return orderProducts;
    }

    public List<OrderProduct> orderProductFind(String orderId){
        return orderProductRepository.findByOrderId(orderId);
    }

    public Optional<OrderProduct> orderCheckByMember(Long productId, Long memberId) {
        final List<Long> findIds = orderProductRepository.findByMemberId(memberId);
        final List<OrderProduct> findOrderProduct = orderProductRepository.findByProductIdAndOrderProductIds(productId, findIds, PageRequest.of(0, 1));
        return Optional.ofNullable(findOrderProduct.isEmpty() ? null : findOrderProduct.get(0));
    }

}
