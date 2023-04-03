package com.kdatalab.bridge.join.controller;

import com.kdatalab.bridge.base.BaseController;
import com.kdatalab.bridge.join.dto.JoinDto;
import com.kdatalab.bridge.join.service.JoinUserService;
import com.kdatalab.bridge.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 회원가입 Controller
 * @author Enclouds
 * @since 2020.12.11
 */

@RestController
@RequestMapping("/join")
public class JoinController extends BaseController {

    @Autowired
    private JoinUserService joinUserService;
    /**
     * ID 중복 확인 Ajax
     *
     * @param loginId
     * @return
     * @throws Exception
     */
    @GetMapping("/duplLoginIdChkAjax")
    public Map<String, Object> getLoginIdDuplChk(@RequestParam String loginId) throws Exception{
        UserDto userInfo = joinUserService.getUserInfo(loginId);

        Map<String, Object> result = new HashMap<String, Object>();

        if(userInfo == null) {
            result.put("msg", "사용 가능한 ID 입니다.");
            result.put("code", 0);
        }else {
            result.put("msg", "사용중인 ID 입니다.");
            result.put("code", -1);
        }

        return result;
    }

    /**
     * 이메일 중복 확인 Ajax
     *
     * @param email
     * @return
     * @throws Exception
     */
    @GetMapping("/duplEmailChkAjax")
    public Map<String, Object> getEmailDuplChk(@RequestParam String email) throws Exception{
        int cnt = joinUserService.getEmailDuplChk(email);

        Map<String, Object> result = new HashMap<String, Object>();

        if(cnt < 1) {
            result.put("msg", "사용 가능한 이메일 입니다.");
            result.put("code", 0);
        }else {
            result.put("msg", "사용중인 이메일 입니다.");
            result.put("code", -1);
        }

        return result;
    }

    /**
     * 전화번호 중복 확인 Ajax
     *
     * @param tel
     * @return
     * @throws Exception
     */
    @GetMapping("/duplTelChkAjax")
    public Map<String, Object> getTelDuplChk(@RequestParam String tel) throws Exception{
        int cnt = joinUserService.getTelDuplChk(tel);

        Map<String, Object> result = new HashMap<String, Object>();

        if(cnt < 1) {
            result.put("msg", "사용 가능한 전화번호 입니다.");
            result.put("code", 0);
        }else {
            result.put("msg", "사용중인 전화번호 입니다.");
            result.put("code", -1);
        }

        return result;
    }

    /**
     * 가입 처리 완료
     *
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping("/confirm")
    public HttpEntity<Boolean> saveUserInfo(@RequestBody JoinDto params) throws Exception{
        if(params.getLoginId() == null){
            return ResponseEntity.ok(false);
        }
        joinUserService.saveUserInfo(params);
        return ResponseEntity.ok(true);
    }

    /**
     * Nice 정보 치환
     *
     * @param paramValue
     * @param gubun
     * @return
     */
    public String requestReplace (String paramValue, String gubun) {

        String result = "";

        if (paramValue != null) {

            paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            paramValue = paramValue.replaceAll("\\*", "");
            paramValue = paramValue.replaceAll("\\?", "");
            paramValue = paramValue.replaceAll("\\[", "");
            paramValue = paramValue.replaceAll("\\{", "");
            paramValue = paramValue.replaceAll("\\(", "");
            paramValue = paramValue.replaceAll("\\)", "");
            paramValue = paramValue.replaceAll("\\^", "");
            paramValue = paramValue.replaceAll("\\$", "");
            paramValue = paramValue.replaceAll("'", "");
            paramValue = paramValue.replaceAll("@", "");
            paramValue = paramValue.replaceAll("%", "");
            paramValue = paramValue.replaceAll(";", "");
            paramValue = paramValue.replaceAll(":", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll("#", "");
            paramValue = paramValue.replaceAll("--", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll(",", "");

            if(gubun != "encodeData"){
                paramValue = paramValue.replaceAll("\\+", "");
                paramValue = paramValue.replaceAll("/", "");
                paramValue = paramValue.replaceAll("=", "");
            }

            result = paramValue;

        }
        return result;
    }

}
