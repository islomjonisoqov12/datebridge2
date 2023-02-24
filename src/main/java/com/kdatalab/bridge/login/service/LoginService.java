package com.kdatalab.bridge.login.service;

import com.kdatalab.bridge.user.dto.UserDto;

/**
 * 로그인 Service Interface
 *
 * @author Enclouds
 * @since 2020.12.11
 */

public interface LoginService {

    /**
     * 로그인 정보 저장
     *
     * @param params
     * @throws Exception
     */
    void saveLoginHist(UserDto params) throws Exception;

}
