package com.kdatalab.bridge.adminpage.checkAdmin.service;

import com.kdatalab.bridge.user.dto.UserDto;

import java.util.List;

public interface CheckAdminService {

    List<UserDto> getAdminList();

    void saveStatus(boolean status, String loginId, String name);
}
