package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Query("select pi from ProductImage pi where pi.attachFileInfo.storedFileName like concat('%', :storedFileName, '%')")
    ProductImage findByStoredFileName(@Param("storedFileName") String storedFileName);
}
