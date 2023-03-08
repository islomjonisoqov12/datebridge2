package com.kdatalab.bridge.adminpage.checkAdmin.service;

import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.mapper.UserMapper;
import com.kdatalab.bridge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckAdminServiceImpl implements CheckAdminService {
    private final UserMapper userMapper;
    private final UserRepository repository;

    @Override
    public List<UserDto> getAdminList() {
        return userMapper.selectUserByStatus('N');
    }

    @Override
    public void saveStatus(boolean status, String loginId, String name) {
        repository.saveStatus(status? 'S': 'R', status? 'Y':'N', loginId, name);
    }
}
