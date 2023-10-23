package com.aw.arbanware.domain.delivery.service;

import com.aw.arbanware.domain.delivery.entity.Delivery;
import com.aw.arbanware.domain.delivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public Delivery deliverySave(Delivery delivery) { //배송지 저장
        return deliveryRepository.save(delivery);
    }



}
