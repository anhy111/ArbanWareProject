package com.aw.arbanware.domain.product;

import com.aw.arbanware.domain.common.DeleteYn;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProductTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("상품_테스트")
    public void 상품_테스트() throws Exception {
        // given
        final Product product = new Product();
        product.setContent("히히히");
        product.setCost(585858);
        product.setPrice(95959595);
        product.setDeleteYn(DeleteYn.N);
        em.persist(product);

        final ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setStoredPath("setStoredPath");
        productImage.setStoredFileName("setStoredFileName");
        productImage.setOriginalFileName("setOriginalFileName");
        productImage.setFileSize(989898L);
        em.persist(productImage);
        // when

        final Product findProduct = em.find(Product.class, product.getId());
        log.info("findProduct = {}", findProduct);

        // then
    }
}