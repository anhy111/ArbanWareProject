package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
