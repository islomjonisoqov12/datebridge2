package com.kdatalab.bridge.base;

import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.mapper.UserMapper;
import com.kdatalab.bridge.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
public class BaseController {
    @Autowired
    private UserMapper userMapper;

    @ModelAttribute(value = "userInfo")
    public UserDto getUserId(Authentication authentication) throws Exception {
        if (authentication==null) {
            return null;
        }else {
            UserDto userDto = new UserDto();
            userDto.setLoginId(authentication.getName());
            return userMapper.selectUserInfo(userDto);
        }
    }
    @ModelAttribute(value = "requestCount")
    public Integer getNewRequestCount(Authentication authentication) throws Exception {
        if (authentication==null) {
            return null;
        }else if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))){
            return userMapper.selectUserByStatus('N').size();
        }else {
            return null;
        }
    }
}
