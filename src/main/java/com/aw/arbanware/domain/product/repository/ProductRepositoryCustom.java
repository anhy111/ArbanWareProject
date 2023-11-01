package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> search(String name);
}
