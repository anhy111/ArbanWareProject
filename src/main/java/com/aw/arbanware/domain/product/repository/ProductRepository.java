package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
@SuppressWarnings("NullableProblems")
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Override
    @Query("select p from Product p where p.deleteYn = com.aw.arbanware.domain.common.DeleteYn.N")
    Page<Product> findAll(Pageable pageable);
}
