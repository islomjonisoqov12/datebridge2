package com.kdatalab.bridge.rank.controller;

import com.kdatalab.bridge.rank.dto.RankDto;
import com.kdatalab.bridge.rank.service.RankService;
import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 랭킹 Controller
 * @author Enclouds
 * @since 2020.12.11
 */

@Controller
public class RankController {

    @Autowired
    private RankService rankService;

    @Autowired
    private UserService userService;

    /**
     * 주간랭킹 리스트
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value="rank", method = RequestMethod.GET)
    public ModelAndView rankList(@ModelAttribute("params") RankDto params) throws Exception{
        ModelAndView mv = new ModelAndView("rank/rank.html");
        List<RankDto> rankList = rankService.selectRankList(params);

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

        mv.addObject("rankList", rankList);
        mv.addObject("params", params);
        mv.addObject("userInfo", userInfo);
        
        return mv;
    }


}
