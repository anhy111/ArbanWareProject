package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import com.aw.arbanware.domain.product.controller.ProductSearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.aw.arbanware.domain.product.entity.QProduct.product;
import static com.aw.arbanware.domain.product.entity.QProductInfo.productInfo;
import static com.querydsl.core.group.GroupBy.*;
import static org.springframework.util.StringUtils.*;

@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ProductProductInfoDto> search(ProductSearchCondition condition, Pageable pageable){
        final List<ProductProductInfoDto> ids = queryFactory
                .selectDistinct(new QProductProductInfoDto(product.id, product.registrationTime))
                .from(product)
                .leftJoin(productInfo).on(productInfo.product.id.eq(product.id))
                .where(
                        nameEq(condition.getName()),
                        priceBetween(condition.getMinPrice(), condition.getMaxPrice()),
                        colorEq(condition.getColors()),
                        sizeEq(condition.getSizes())
                )
                .orderBy(getOrderSpecifier(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        final Map<Long, ProductProductInfoDto> resultMap = queryFactory
                .from(product)
                .leftJoin(productInfo).on(productInfo.product.id.eq(product.id))
                .where(
                        product.id.in(ids.stream()
                                .map(ProductProductInfoDto::getProductId)
                                .collect(Collectors.toList()))
                )
                .orderBy(getOrderSpecifier(pageable.getSort()))
                .transform(groupBy(product.id).as(new QProductProductInfoDto(
                        product.id,
                        product.name,
                        product.price,
                        product.thumbnail,
                        product.registrationTime,
                        set(new QProductProductInfoDto_Colors(productInfo.color))
                )));

        final List<ProductProductInfoDto> result = resultMap.keySet().stream()
                .map(resultMap::get)
                .collect(Collectors.toList());

        log.info("transformResult: {}", result);

        final int total = queryFactory
                .selectDistinct(new QProductProductInfoDto(product.id, product.registrationTime))
                .from(product)
                .leftJoin(productInfo).on(productInfo.product.id.eq(product.id))
                .where(
                        nameEq(condition.getName()),
                        priceBetween(condition.getMinPrice(), condition.getMaxPrice()),
                        colorEq(condition.getColors()),
                        sizeEq(condition.getSizes())
                )
                .fetch()
                .size();

        log.info("resultTotal: {}", total);

        return new PageImpl<>(result, pageable, total);
    }

    private BooleanExpression colorEq(List<Color> colors) {
        return colors != null && !colors.isEmpty() ? productInfo.color.in(colors) : null;
    }

    private BooleanExpression sizeEq(List<Size> sizes) {
        return sizes != null && !sizes.isEmpty() ? productInfo.size.in(sizes) : null;
    }

    private BooleanBuilder priceBetween(String  minPrice, String  maxPrice) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(minPrice(minPrice));
        builder.and(maxPrice(maxPrice));
        return builder;
    }

    private BooleanExpression minPrice(String  minPrice) {
        return hasText(minPrice) ? product.price.goe(Integer.parseInt(minPrice)) : null;
    }

    private BooleanExpression maxPrice(String  maxPrice) {
        return hasText(maxPrice) ? product.price.loe(Integer.parseInt(maxPrice)) : null;
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? product.name.eq(name) : null;
    }

    private OrderSpecifier<?>[] getOrderSpecifier(Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        sort.forEach(order -> {
            final Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            final String property = order.getProperty();
            final Path<Object> path = Expressions.path(Object.class, product, property);
            orderSpecifiers.add(new OrderSpecifier(direction, path));
        });
        return orderSpecifiers.toArray(value -> new OrderSpecifier<?>[value]);
    }
}
