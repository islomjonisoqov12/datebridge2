package com.kdatalab.bridge.login.dto;

import lombok.Data;

/**
 * 로그인 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class LoginDto {

    private String loginId;
    private String username;
    private String password;
    private String loginIp;

}
