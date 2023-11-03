package com.aw.arbanware.domain.orderproduct.controller;

import com.aw.arbanware.domain.cart.service.CartService;
import com.aw.arbanware.domain.common.apiobj.DefaultResponse;
import com.aw.arbanware.domain.common.apiobj.ResponseMessage;
import com.aw.arbanware.domain.common.apiobj.StatusCode;
import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import com.aw.arbanware.domain.orderproduct.service.OrderProductService;
import com.aw.arbanware.global.config.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderProductController {

    private final OrderProductService orderProductService;
    private final CartService cartService;

    @PostMapping("/orderProduct/new")
    @ResponseBody
    public ResponseEntity orderProductRegister(@RequestBody List<OrderProduct> orderProduct, @AuthenticationPrincipal SecurityUser securityUser) {

        log.info(" orderProduct = {}", orderProduct.toString());
        List<OrderProduct> orderProducts = orderProductService.orderProductSaveAll(orderProduct);
        cartService.cartDelete(securityUser, orderProducts);
        return new ResponseEntity(new DefaultResponse(StatusCode.OK, ResponseMessage.NOT_DUPLICATE_LOGIN_ID)
                , HttpStatus.OK);
    }

}
