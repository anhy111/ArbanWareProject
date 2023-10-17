package com.aw.arbanware.domain.product.service;

import com.aw.arbanware.domain.product.entity.ProductImage;
import com.aw.arbanware.domain.product.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

//    public List<ProductImage> saveAll(MultipartFile[] multipartFiles) {
//        final List<ProductImage> productImages = multipartToProductImages(multipartFiles);
//        return productImageRepository.saveAll(productImages);
//    }

//    private List<ProductImage> multipartToProductImages(MultipartFile[] multipartFiles) {
//        return Arrays.stream(multipartFiles).map(multipartFile -> {
//
//        });
//    }
}
