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

    @Query( value = "select new com.aw.arbanware.domain.review.repository.ReviewDto(" +
            "r.id, r.rating, r.modificationTime, r.registrationTime, m.id, m.name, r.content, pi.color, pi.size, r.attachFileId) " +
            " from Review r join r.member m join r.orderProduct op join op.productInfo pi join" +
            " pi.product p where p.id = :productId",
            countQuery = "select count(r) from Review r join r.orderProduct op join op.productInfo pi join " +
            " pi.product p where p.id = :productId")
    Page<ReviewDto> findByProduct(@Param("productId") Long productId, Pageable pageable);
}
