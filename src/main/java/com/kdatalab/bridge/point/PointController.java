package com.kdatalab.bridge.point;

import com.kdatalab.bridge.base.BaseController;
import com.kdatalab.bridge.point.model.PointHistory;
import com.kdatalab.bridge.point.service.PointService;
import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController extends BaseController {

    private final UserService userService;

    private final PointService pointService;

    @RequestMapping(value = "/point-history", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView pointHistory() throws Exception {

        ModelAndView mv = new ModelAndView("point/point-history.html");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userInfo = userService.getUserInfo(userDetails.getUsername());
            }
        } catch (Exception cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userInfo = userService.getUserInfo(auth2User.getName());
        }

        String loginId = userInfo.getLoginId();
        List<PointHistory> pointHistory = pointService.getPointHistory(loginId);
        mv.addObject("pointHistory", pointHistory);
        mv.addObject("totalPoint", userInfo.getNowpoint());

        return mv;
    }
}
