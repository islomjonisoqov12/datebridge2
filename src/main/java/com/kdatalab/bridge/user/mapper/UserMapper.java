package com.kdatalab.bridge.user.mapper;

import com.kdatalab.bridge.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 사용자 Mapper
 * @author Enclouds
 * @since 2020.12.11
 */

@Mapper
public interface UserMapper {

    /**
     * 사용자 정보 조회
     *
     * @param params
     * @return
     * @throws UsernameNotFoundException
     */
    UserDto selectUserInfo(UserDto params) throws UsernameNotFoundException;
    UserDto selectAllUserInfo(UserDto params) throws UsernameNotFoundException;

}
