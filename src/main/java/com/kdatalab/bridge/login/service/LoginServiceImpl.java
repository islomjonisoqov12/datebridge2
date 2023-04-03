package com.kdatalab.bridge.login.service;

import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 로그인 Service
 *
 * @author Enclouds
 * @since 2020.12.11
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 로그인 정보 저장
     *
     * @param params
     * @throws Exception
     */
    public void saveLoginHist(UserDto params) throws Exception {
        userRepository.saveLoginHist(params);
    }

}
