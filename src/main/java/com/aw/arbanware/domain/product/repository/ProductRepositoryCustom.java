package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.controller.ProductSearchCondition;
import com.aw.arbanware.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    Page<ProductProductInfoDto> search(ProductSearchCondition condition, Pageable pageable);
}
