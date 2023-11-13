package com.aw.arbanware.domain.review.repository;

import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query( value = "select r from Review r join fetch r.orderProduct op join fetch op.productInfo pi join fetch" +
            " pi.product p where p.id = :productId",
            countQuery = "select count(r) from Review r join r.orderProduct op join op.productInfo pi join " +
            " pi.product p where p.id = :productId")
    Page<Review> findByProduct(@Param("productId") Long productId, Pageable pageable);
}
