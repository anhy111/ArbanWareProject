package com.aw.arbanware.domain.product.service;

import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.domain.product.repository.ProductInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductInfoService {

    private final ProductInfoRepository productInfoRepository;

    @Transactional
    public List<ProductInfo> cartOptionUpdate(Long productId){
        return productInfoRepository.findByProductId(productId);
    }

    public List<ProductInfo> saveAll(List<ProductInfo> productInfos) {
        return productInfoRepository.saveAll(productInfos);
    }

    public List<ProductInfo> findByProductId(Long productId) {
        return productInfoRepository.findByProductId(productId);
    }

}
