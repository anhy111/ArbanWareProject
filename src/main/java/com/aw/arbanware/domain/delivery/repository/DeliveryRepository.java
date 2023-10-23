package com.aw.arbanware.domain.delivery.repository;

import com.aw.arbanware.domain.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
