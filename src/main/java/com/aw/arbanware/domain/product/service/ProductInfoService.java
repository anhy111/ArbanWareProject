package com.aw.arbanware.domain.product.service;

import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import com.aw.arbanware.domain.product.controller.OrderProductForm;
import com.aw.arbanware.domain.product.controller.UpdateProductForm;
import com.aw.arbanware.domain.product.entity.Product;
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
@Transactional
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

    public UpdateProductForm findUpdateFormByProductId(Long productId) {
        final List<ProductInfo> findProductInfos = productInfoRepository.findByProductIdWithImage(productId);
        log.info("findProductInfos.size = {}", findProductInfos.size());
        return UpdateProductForm.createUpdateForm(findProductInfos);
    }

    public List<ProductInfo> findByProductIdWithImage(Long productId) {
        return productInfoRepository.findByProductIdWithImage(productId);
    }

    public ProductInfo findByProductAndColorAndSize(OrderProductForm productInfo) {
        return productInfoRepository.findByProductAndColorAndSize(productInfo.getProduct(), productInfo.getColor(), productInfo.getSize());
    }

    public ProductInfo findByProductAndColorAndSize(Product product, Color color, Size size) {
        return productInfoRepository.findByProductAndColorAndSize(product, color, size);
    }

}
