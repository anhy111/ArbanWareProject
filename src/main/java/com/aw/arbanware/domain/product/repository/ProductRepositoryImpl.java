package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Product> search(String name){
        final QProduct p = new QProduct("p");
        return queryFactory
                .select(p)
                .from(p)
                .where(name != null ? p.name.like("%" + name + "%") : null)
                .fetch();
    }
}
