package com.aw.arbanware.domain.review.repository;

import com.aw.arbanware.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
