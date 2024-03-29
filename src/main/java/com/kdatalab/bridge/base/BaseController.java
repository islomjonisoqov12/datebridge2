package com.kdatalab.bridge.base;

import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
public class BaseController {
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute(value = "userInfo")
    public UserDto getUserId(Authentication authentication) throws Exception {
        if (authentication==null) {
            return null;
        }else {
            UserDto userDto = new UserDto();
            userDto.setLoginId(authentication.getName());
            return userRepository.selectUserInfo(userDto.getLoginId());
        }
    }
    @ModelAttribute(value = "requestCount")
    public Integer getNewRequestCount(Authentication authentication) throws Exception {
        if (authentication==null) {
            return null;
        }else if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))){
            return userRepository.selectUserByStatus('N').size();
        }else {
            return null;
        }
    }
}
