package com.kdatalab.bridge.adminpage.checkAdmin.service;

import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckAdminServiceImpl implements CheckAdminService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getAdminList() {
        return repository.selectUserByStatus('N');
    }

    @Override
    public void saveStatus(boolean status, String loginId, String name) {
        repository.saveStatus('S', status? 'Y':'N', loginId, name);
    }
}
