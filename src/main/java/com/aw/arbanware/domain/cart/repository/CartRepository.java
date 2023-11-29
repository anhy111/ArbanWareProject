package com.aw.arbanware.domain.cart.repository;

import com.aw.arbanware.domain.cart.entity.Cart;
import com.aw.arbanware.domain.cart.entity.CartKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, CartKey> {

//    List<Cart> findByMemberId(Long memberId);

    @Query("select c from Cart c join fetch c.productInfo pi join fetch pi.product p where c.member.id = :memberId")
    List<Cart> findByMemberId(@Param("memberId") Long memberId);

    @Query("select c from Cart c join fetch c.productInfo pi join fetch pi.product p where c.member.id = :memberId and c.productInfo.id = :productInfoId")
    Cart findByMemberIdAndProductInfoId(@Param("memberId") Long memberId, @Param("productInfoId") Long productInfoId);

    @Query("select count(c) from Cart c where c.member.id = :memberId")
    int findCountByMember(@Param("memberId") Long memberId);
}
