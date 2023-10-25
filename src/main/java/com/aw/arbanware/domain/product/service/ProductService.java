package com.aw.arbanware.domain.product.service;

import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import com.aw.arbanware.domain.product.controller.CreateProductForm;
import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.entity.ProductImage;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final ProductInfoService productInfoService;

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> findByAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public Product addProduct(CreateProductForm form) {
        // 상품추가
        final Product product = CreateProductForm.createProduct(form);
        // 대표이미지 추가
        final ProductImage thumbnail = productImageService.saveAll(new MultipartFile[]{form.getThumbnail()})
                .get(0);
        final AttachFileInfo thumbnailFileInfo = thumbnail.getAttachFileInfo();
        product.setThumbnail(thumbnailFileInfo.getStoredPath() + thumbnailFileInfo.getStoredFileName());
        final Product saveProduct = productRepository.save(product);

        // 상품정보추가
        final List<ProductInfo> productInfos = CreateProductForm.createProductInfo(form, product);
        productInfoService.saveAll(productInfos);

        // 상품이미지 업데이트 ( 상품없는 상품이미지들과 관계맺기 )
        final List<String> StrProductImages = Arrays.stream(
                                                    form.getProductImages().split(",")
                                                ).collect(Collectors.toList());
        StrProductImages.add(thumbnail.getId().toString());
        final List<Long> productImages = new ArrayList<>();
        for (String s : StrProductImages) {
            productImages.add(Long.parseLong(s));
        }

        final List<ProductImage> findProductImages = productImageService.findByIdList(productImages);
        findProductImages.forEach(pi -> {
            pi.setProduct(product);
        });

        return saveProduct;
    }
}
