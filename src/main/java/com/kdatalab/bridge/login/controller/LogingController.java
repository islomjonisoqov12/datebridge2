package com.kdatalab.bridge.login.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 로그인 Controller
 * @author Enclouds
 * @since 2020.12.11
 */

@Controller
public class LogingController {

	/**
	 * 로그인 View
	 * 현재 사용 안함.
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request) throws Exception{
		return "login/login.html";
	}
	
}
