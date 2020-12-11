package com.kdatalab.bridge.edu.controller;

import com.kdatalab.bridge.edu.dto.EduAnsRsltDto;
import com.kdatalab.bridge.edu.dto.EduDto;
import com.kdatalab.bridge.edu.service.EduService;
import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 교육 Controller
 * @author Enclouds
 * @since 2020.12.11
 */
@Controller
public class EduController {

    @Autowired
    private EduService eduService;

    @Autowired
    private UserService userService;

    /**
     * 문항 정답 확인 Ajax
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @GetMapping("/edu/ansChkAjax")
    public @ResponseBody Map<String, Object> ansChkAjax(@RequestParam Map<String, Object> paramsMap) throws Exception{

        EduDto params = new EduDto();
        Map<String, Object> result = new HashMap<String, Object>();

        params.setEduSeq(Integer.parseInt((String)paramsMap.get("eduSeq")));
        List<EduAnsRsltDto> eduAnsRsltList = eduService.selectEduAnsRsltList(params);

        for(int i=0 ; i<eduAnsRsltList.size() ; i++){
            String ansResult = ((EduAnsRsltDto)eduAnsRsltList.get(i)).getResult();

            String ans = (String)paramsMap.get("quelist"+String.valueOf(i+1));

            if(!ansResult.equals(ans)){
                result.put("msg", String.valueOf(i+1)+"번 문항에 정답을 확인 바랍니다.");
                result.put("code", -1);
                break;
            }else {
                result.put("msg", "모두 정답");
                result.put("code", 0);
            }
        }

        return result;
    }

    /**
     * 교육완료 처리
     *
     * @param paramsMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edu/saveEdu")
    public ModelAndView saveEduInfo(@RequestParam Map<String, Object> paramsMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sub/subComp.html");

        EduDto params = new EduDto();
        params.setEduSeq(Integer.parseInt((String)paramsMap.get("eduSeq")));

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loginId = "";
        UserDto userInfo = null;

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                loginId = userDetails.getUsername();
                userInfo = userService.getUserInfo(loginId);
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            loginId = auth2User.getName();
            userInfo = userService.getUserInfo(loginId);
        }

        params.setLoginId(loginId);
        params.setPoint(Integer.parseInt((String)paramsMap.get("point")));

        eduService.saveEduInfo(params);

        mv.addObject("userInfo", userInfo);

        return mv;
    }

}
