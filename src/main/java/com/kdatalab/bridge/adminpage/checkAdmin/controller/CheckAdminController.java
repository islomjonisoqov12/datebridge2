package com.kdatalab.bridge.adminpage.checkAdmin.controller;

import com.kdatalab.bridge.adminpage.checkAdmin.service.CheckAdminService;
import com.kdatalab.bridge.base.BaseController;
import com.kdatalab.bridge.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping (value = "/check-admin")
@RequiredArgsConstructor
public class CheckAdminController extends BaseController {

    private final CheckAdminService service;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAdmins(Model model) {
        List<UserDto> adminList = service.getAdminList();
        model.addAttribute("admins", adminList);
        return "admin/checkAdmin";
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public boolean saveStatus(@PathVariable boolean status, @RequestParam String loginId, Authentication authentication){
        service.saveStatus(status, loginId, authentication.getName());
        return true;
    }


}
