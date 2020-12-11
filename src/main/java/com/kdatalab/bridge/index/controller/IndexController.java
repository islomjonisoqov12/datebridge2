package com.kdatalab.bridge.index.controller;

import com.kdatalab.bridge.edu.dto.EduDto;
import com.kdatalab.bridge.edu.service.EduService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 메인페이지 Controller
 * @author Enclouds
 * @since 2020.12.11
 */

@Controller
public class IndexController {

	@Autowired
	private EduService eduService;

	@Autowired
	private RankService rankService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, @ModelAttribute("params") EduDto params) throws Exception {

		ModelAndView mv = new ModelAndView("index.html");

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

		List<EduDto> eduList = eduService.selectEduListTop8(params);
		List<RankDto> rankTop5List = rankService.selectRankTop5List();

		mv.addObject("eduList", eduList);
		mv.addObject("rankTop5List", rankTop5List);
		mv.addObject("userInfo", userInfo);
		mv.addObject("params", params);

		return mv;
	}
}
