package com.kdatalab.bridge.join.service;

import com.kdatalab.bridge.join.dto.JoinDto;
import com.kdatalab.bridge.user.dto.UserDto;

/**
 * 회원가입 Service Interface
 * @author Enclouds
 * @since 2020.12.11
 */

public interface JoinUserService {

    /**
     * 사용자 정보 조회
     *
     * @param loginId
     * @return
     * @throws Exception
     */
    UserDto getUserInfo(String loginId) throws Exception;

    /**
     * 회원 가입 처리
     *
     * @param params
     * @throws Exception
     */
    void saveUserInfo(JoinDto params) throws Exception;

    /**
     * 본인 인증시 중복 가입 방지
     *
     * @param duplInfo
     * @return
     * @throws Exception
     */
    int getUserDuplInfo(String duplInfo) throws Exception;

}
