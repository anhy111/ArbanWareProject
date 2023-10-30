package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {

    @Query("select pi from ProductInfo pi join fetch pi.product p where pi.product.id = :productId")
    List<ProductInfo> findByProductId(@Param("productId") Long productId);

    @Query("select distinct pi from ProductInfo pi join fetch pi.product p join fetch p.productImages pim where p.id = :productId")
    List<ProductInfo> findByProductIdWithImage(@Param("productId") Long productId);
}
