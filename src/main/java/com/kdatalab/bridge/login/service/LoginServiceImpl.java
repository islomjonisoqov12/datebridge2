package com.kdatalab.bridge.login.service;

import com.kdatalab.bridge.login.mapper.LoginMapper;
import com.kdatalab.bridge.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 로그인 Service
 * @author Enclouds
 * @since 2020.12.11
 */

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private LoginMapper loginMapper;

    /**
     * 로그인 정보 저장
     *
     * @param params
     * @throws Exception
     */
    public void saveLoginHist(UserDto params) throws Exception{
        loginMapper.saveLoginHist(params);
    }

}
