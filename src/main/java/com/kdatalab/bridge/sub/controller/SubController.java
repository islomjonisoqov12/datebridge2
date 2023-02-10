package com.kdatalab.bridge.sub.controller;

import com.kdatalab.bridge.edu.dto.EduDto;
import com.kdatalab.bridge.edu.dto.EduQueDto;
import com.kdatalab.bridge.edu.service.EduService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * 교육 페이지 Controller
 * @author Enclouds
 * @since 2020.12.11
 */

@Controller
public class SubController {

    @Autowired
    private EduService eduService;

    @Autowired
    private UserService userService;

    /**
     * 교육 리스트
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value="sub", method = RequestMethod.GET)
    public ModelAndView eduList(@ModelAttribute("params") EduDto params) throws Exception{
        ModelAndView mv = new ModelAndView("sub/sub.html");
        List<EduDto> eduList = eduService.selectEduList(params);

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

        mv.addObject("eduList", eduList);
        mv.addObject("params", params);
        mv.addObject("userInfo", userInfo);

        return mv;
    }

    /**
     * 교육 상세 정보
     *
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "sub/subDtl", method = RequestMethod.GET)
    public ModelAndView eduDtl(@ModelAttribute("params") EduDto params, HttpServletResponse response) throws Exception{
        ModelAndView mv = new ModelAndView("sub/subDtl.html");
        EduDto eduInfo = eduService.selectEduInfo(params);
        List<EduQueDto> queList = eduService.selectEduQueList(params);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";
        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("eduInfo", eduInfo);

        if(eduInfo.getEduActiveYn().equals("N")){
            PrintWriter out = response.getWriter();
            response.setContentType("text/html; charset=utf-8");
            out.println("<script language='javascript'>");
            out.println("alert('완료된 교육 입니다.')");
            out.println("location.href='/sub'");
            out.println("</script>");
            out.flush();
        }else {
            params.setLoginId(userId);
            int cnt = eduService.compEduCount(params);
            if(cnt > 0){
                PrintWriter out = response.getWriter();
                response.setContentType("text/html; charset=utf-8");
                out.println("<script language='javascript'>");
                out.println("alert('이수 완료한 교육 입니다.')");
                out.println("location.href='/sub'");
                out.println("</script>");
                out.flush();
            }
        }

        mv.addObject("queList", queList);
        mv.addObject("params", params);
        mv.addObject("userInfo", userInfo);

        return mv;
    }

}
