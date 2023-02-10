package com.kdatalab.bridge.user.dto;

import lombok.Data;

/**
 * 사용자 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class UserDto {

    private String loginId;
    private String password;
    private String name;
    private String eMail;
    private String tel;
    private String birthDt;
    private String genderCd;
    private Integer nowpoint;
    private String loginIp;

}
