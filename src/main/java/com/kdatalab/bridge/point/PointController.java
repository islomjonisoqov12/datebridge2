package com.kdatalab.bridge.point;

import com.kdatalab.bridge.base.BaseController;
import com.kdatalab.bridge.point.model.PointHistory;
import com.kdatalab.bridge.point.service.PointService;
import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PointController extends BaseController {

    private final UserService userService;

    private final PointService pointService;

    @GetMapping("/point-history")
    @PreAuthorize(value = "hasRole('USER')")
    public String pointHistory(Model mv, Authentication authentication) throws Exception {

        UserDto userInfo = null;

        try {
            if (authentication.getPrincipal() != "anonymousUser") {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                userInfo = userService.getUserInfo(userDetails.getUsername());
            }
        } catch (Exception cce) {
            DefaultOAuth2User auth2User = (DefaultOAuth2User) authentication.getPrincipal();
            userInfo = userService.getUserInfo(auth2User.getName());
        }

        String loginId = userInfo.getLoginId();

//        loginId = "yanghee";// TODO after test this line should be removed
        List<PointHistory> pointHistory = pointService.getPointHistory(loginId);
        mv.addAttribute("pointHistory", pointHistory);
        mv.addAttribute("totalPoint", pointService.getTotalPointByLoginId(loginId));

        return "point/point-history";
    }
}
