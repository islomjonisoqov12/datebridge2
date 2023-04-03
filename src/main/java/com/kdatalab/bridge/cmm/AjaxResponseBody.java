package com.kdatalab.bridge.cmm;

import lombok.Data;


/**
 * Ajax 사용 공통 DTO
 * @author Enclouds
 * @since 2020.12.11
 */
@Data
public class AjaxResponseBody {

    private String msg;
    private Integer code;

}
