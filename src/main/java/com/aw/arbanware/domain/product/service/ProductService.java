package com.aw.arbanware.domain.product.service;

import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> findByAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
