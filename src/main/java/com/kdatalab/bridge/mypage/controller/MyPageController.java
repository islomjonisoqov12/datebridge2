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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController extends BaseController {

    private final MyPageService myPageService;

    private final UserService userService;

    @RequestMapping(value = "/mypage", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView myPage(@RequestParam(value = "status", required = false, defaultValue = "INPROGRESS") String projectStatus) throws Exception {
        ModelAndView mv = new ModelAndView("/mypage/mypage.html");

        String userName = userService.getUserName();
        List<Project> projects = myPageService.getProjectList(userName, projectStatus);
        ProjectDetail projectDetail = myPageService.getProjectDetail(userName);

        mv.addObject("projects", projects);
        mv.addObject("totalPoint", projectDetail.getTotalPoint());
        mv.addObject("rejectedProjectCnt", projectDetail.getRejectedProjectCnt());
        mv.addObject("completedProjectCnt", projectDetail.getCompletedProjectCnt());
        mv.addObject("weeklyRank", projectDetail.getWeeklyRank());
        mv.addObject("currentStatus", projectStatus);

        return mv;
    }
}
