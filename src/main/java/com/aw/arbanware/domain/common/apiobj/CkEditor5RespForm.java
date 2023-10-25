package com.aw.arbanware.domain.common.apiobj;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class CkEditor5RespForm {
    private Map<String, String > urls = new HashMap<>();
    private Long imageId;
}
