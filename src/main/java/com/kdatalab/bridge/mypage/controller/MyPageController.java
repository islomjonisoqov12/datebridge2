package com.kdatalab.bridge.mypage.controller;

import com.kdatalab.bridge.base.BaseController;
import com.kdatalab.bridge.mypage.model.Project;
import com.kdatalab.bridge.mypage.model.ProjectDetail;
import com.kdatalab.bridge.mypage.service.MyPageService;
import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MyPageController extends BaseController {

    private final MyPageService myPageService;

    private final UserService userService;

    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    @PreAuthorize(value = "isAuthenticated()")
    public ModelAndView myPage() throws Exception {
        ModelAndView mv = new ModelAndView("/mypage/mypage.html");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userInfo = userService.getUserInfo(userDetails.getUsername());
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userInfo = userService.getUserInfo(auth2User.getName());
        }

        List<Project> projects = myPageService.getProjectList(userInfo.getLoginId());
        ProjectDetail projectDetail = myPageService.getProjectDetail(userInfo.getLoginId());

        mv.addObject("projects", projects);
        mv.addObject("totalPoint", projectDetail.getTotalPoint());
        mv.addObject("rejectedProjectCnt", projectDetail.getRejectedProjectCnt());
        mv.addObject("completedProjectCnt", projectDetail.getCompletedProjectCnt());
        mv.addObject("weeklyRank", projectDetail.getWeeklyRank());

        return mv;
    }
}
