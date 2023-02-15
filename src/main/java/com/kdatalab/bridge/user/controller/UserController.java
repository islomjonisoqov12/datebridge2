package com.kdatalab.bridge.user.controller;

import com.kdatalab.bridge.user.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

import static com.kdatalab.bridge.user.service.UserService.GENERATED_CODES;

/**
 * 사용자 정보 Controller
 * @author Enclouds
 * @since 2020.12.11
 */

@Controller
@RequestMapping("/user")
public class UserController {

   private final UserService userService;

   public UserController(UserService userService) {
      this.userService = userService;
   }

   /**
    * 로그인 페이지 이동
    *
    * @return
    */
   @GetMapping("/login")
   public String login(){
      return "/login/login";
   }

   /**
    * 로그아웃 처리
    *
    * @return
    */
   @GetMapping("/logout")
   public String logout(){
      return "/login/login";
   }

   /**
    * get check password page
    * @return string page check password
    */
   @GetMapping("/check-password")
   public String getCheckPasswordPage(){
      return "user/checkPassword";
   }

   /**
    * check password is correct
    * @param inputPassword  path variable input param password for check
    * @param authentication get current logged in user
    * @return bad request or success
    */
   @GetMapping("/check-password/{inputPassword}")
   @ResponseBody
   @PreAuthorize(value = "isFullyAuthenticated()")
   public HttpEntity<String> checkPassword(@PathVariable String inputPassword, Authentication authentication){
      return userService.checkPassword(inputPassword, authentication.getName());
   }

   /**
    * get user info page
    * @param code for check
    * @param authentication get current logged in user
    * @param model for return params
    * @return page or redirect checkPassword page
    */
   @GetMapping("/info-edit")
   @PreAuthorize(value = "isAuthenticated()")
   public String getEditPage(@RequestParam String code, Authentication authentication, Model model) {
      String existsCode = GENERATED_CODES.get(authentication.getName());
      if(existsCode==null || !existsCode.equals(code)){
         return "redirect:/user/check-password";
      }
      GENERATED_CODES.remove(authentication.getName());
      model.addAttribute("user", userService.getAllUserInfo(authentication.getName()));
      return "user/editAccountInfo";
   }

   /**
    * save user info
    * @param password edited password
    * @param email edited email
    * @param authentication get current logged in user
    * @param model return params
    * @return next page
    */
   @PostMapping("/info-edit")
   @PreAuthorize(value = "isAuthenticated()")
   public String saveEditPage(
           @RequestParam(required = false, defaultValue = "") String password,
           @RequestParam String email,
           Authentication authentication,
           Model model
   ){
      return userService.editUserInfo(password.trim(), email.trim(), authentication.getName(), model);
   }


   @PreAuthorize(value = "isFullyAuthenticated()")
   @GetMapping("/leave-membership")
   public String getLeaveTheMembershipPage(){
      return "user/leaveMembership";
   }
   @GetMapping( "/leave-membership/post")
   @ResponseBody
   @PreAuthorize(value = "isFullyAuthenticated()")
   public boolean leaveTheMembership(Authentication authentication){
      return userService.leaveTheMembership(authentication.getName());
   }
}

