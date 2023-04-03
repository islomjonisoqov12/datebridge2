package com.kdatalab.bridge.adminpage.checkAdmin.controller;

import com.kdatalab.bridge.adminpage.checkAdmin.service.CheckAdminService;
import com.kdatalab.bridge.base.BaseController;
import com.kdatalab.bridge.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping (value = "/check-admin")
@RequiredArgsConstructor
public class CheckAdminController extends BaseController {

    private final CheckAdminService service;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<List<UserDto>> getAdmins() {
        List<UserDto> adminList = service.getAdminList();
        return ResponseEntity.ok(adminList);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean saveStatus(@PathVariable boolean status, @RequestParam String loginId, Authentication authentication){
        service.saveStatus(status, loginId, authentication.getName());
        return true;
    }


}
