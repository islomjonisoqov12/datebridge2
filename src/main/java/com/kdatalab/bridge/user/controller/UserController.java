package com.kdatalab.bridge.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 사용자 정보 Controller
 * @author Enclouds
 * @since 2020.12.11
 */

@Controller
public class UserController {

   /**
    * 로그인 페이지 이동
    *
    * @return
    */
   @GetMapping("/user/login")
   public String login(){
      return "/login/login";
   }

   /**
    * 로그아웃 처리
    *
    * @return
    */
   @GetMapping("/user/logout")
   public String logout(){
      return "/login/login";
   }

}
