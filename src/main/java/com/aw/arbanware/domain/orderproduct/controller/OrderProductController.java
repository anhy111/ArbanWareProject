package com.aw.arbanware.domain.orderproduct.controller;

import com.aw.arbanware.domain.common.apiobj.DefaultResponse;
import com.aw.arbanware.domain.common.apiobj.ResponseMessage;
import com.aw.arbanware.domain.common.apiobj.StatusCode;
import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import com.aw.arbanware.domain.orderproduct.service.OrderProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderProductController {

    private final OrderProductService orderProductService;

    @PostMapping("/orderProduct/new")
    @ResponseBody
    public ResponseEntity orderProductRegister(@RequestBody OrderProduct orderProduct) {

        log.info(" orderProduct = {}", orderProduct.toString());


        return new ResponseEntity(new DefaultResponse(StatusCode.OK, ResponseMessage.NOT_DUPLICATE_LOGIN_ID)
                , HttpStatus.OK);
    }

}
