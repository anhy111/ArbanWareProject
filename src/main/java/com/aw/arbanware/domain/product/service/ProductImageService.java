package com.aw.arbanware.domain.product.service;

import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import com.aw.arbanware.domain.product.entity.ProductImage;
import com.aw.arbanware.domain.product.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private static final String UPLOAD_FOLDER = "C:\\arbanWare\\upload\\";


    public List<ProductImage> saveAll(MultipartFile[] multipartFiles) {
        final List<ProductImage> productImages = Arrays.stream(multipartFiles).map(multipartFile -> {

            String uploadPath = AttachFileInfo.todayToFolder();

            final ProductImage productImage = new ProductImage();
            productImage.setAttachFileInfo(new AttachFileInfo(multipartFile, uploadPath));
            log.info("{} 파일 업로드 시작", productImage.getAttachFileInfo().getOriginalFileName());

            final File saveFile = new File(UPLOAD_FOLDER + uploadPath + productImage.getAttachFileInfo().getStoredFileName());
            if (saveFile.mkdirs()) {
                try {
                    multipartFile.transferTo(saveFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return productImage;
        }).collect(Collectors.toList());
        return productImageRepository.saveAll(productImages);
    }

    public List<ProductImage> findByIdList(List<Long> ids) {
        return productImageRepository.findAllById(ids);
    }

    public ProductImage findByStoredFileName(String storedFileName) {
        return productImageRepository.findByStoredFileName(storedFileName);
    }

    public ProductImage save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }
}
