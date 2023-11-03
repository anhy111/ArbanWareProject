package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class ProductRepositoryImplTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("productRepositoryTest")
    public void productRepositoryTest() throws Exception {
//        final List<Product> findProducts = productRepository.search("테스트");
//        log.info("findProducts = {}", findProducts);
    }
}