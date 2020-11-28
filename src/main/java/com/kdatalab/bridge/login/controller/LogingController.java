package com.kdatalab.bridge.login.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogingController {
	
	@RequestMapping("/login/login")
	public String login() throws Exception{
		return "login/login.html";
	}
	
	@RequestMapping("/login/join")
	public String join() throws Exception{
		return "login/join.html";
	}
	
	
}
