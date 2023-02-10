package com.kdatalab.bridge.login.mapper;

import com.kdatalab.bridge.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 로그인 Mapper
 * @author Enclouds
 * @since 2020.12.11
 */

@Mapper
public interface LoginMapper {

    /**
     * 로그인 정보 저장
     *
     * @param params
     * @throws Exception
     */
    public void saveLoginHist(UserDto params) throws Exception;

}
