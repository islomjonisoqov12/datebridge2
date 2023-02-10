package com.kdatalab.bridge.join.controller;

import com.kdatalab.bridge.join.dto.JoinDto;
import com.kdatalab.bridge.join.service.JoinUserService;
import com.kdatalab.bridge.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 회원가입 Controller
 * @author Enclouds
 * @since 2020.12.11
 */

@Controller
public class JoinController {

    @Autowired
    private JoinUserService joinUserService;

    /**
     * 가입 처리
     *
     * @param session
     * @param joinDto
     * @return
     * @throws Exception
     */
    @RequestMapping("/join")
    public ModelAndView join(HttpSession session, @ModelAttribute("joinDto") JoinDto joinDto) throws Exception{
        ModelAndView mv = new ModelAndView("login/join.html");

        //인증 후 결과값이 null로 나오는 부분은 관리담당자에게 문의 바랍니다.
        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

        String sEncodeData = requestReplace(joinDto.getEncodeData(), "encodeData");

        String sSiteCode = "BT328";				// NICE로부터 부여받은 사이트 코드
        String sSitePassword = "bd6sxEQv9Sc5";			// NICE로부터 부여받은 사이트 패스워드

        String sCipherTime = "";			// 복호화한 시간
        String sRequestNumber = "";			// 요청 번호
        String sResponseNumber = "";		// 인증 고유번호
        String sAuthType = "";				// 인증 수단
        String sName = "";					// 성명
        String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)
        String sConnInfo = "";				// 연계정보 확인값 (CI_88 byte)
        String sBirthDate = "";				// 생년월일(YYYYMMDD)
        String sGender = "";				// 성별
        String sNationalInfo = "";			// 내/외국인정보 (개발가이드 참조)
        String sMobileNo = "";				// 휴대폰번호
        String sMobileCo = "";				// 통신사
        String sMessage = "";
        String sPlainData = "";

        int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

        if( iReturn == 0 ){
            sPlainData = niceCheck.getPlainData();
            sCipherTime = niceCheck.getCipherDateTime();

            // 데이타를 추출합니다.
            java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

            sRequestNumber  = (String)mapresult.get("REQ_SEQ");
            sResponseNumber = (String)mapresult.get("RES_SEQ");
            sAuthType		= (String)mapresult.get("AUTH_TYPE");
            sName			= (String)mapresult.get("NAME");
            sName			= (String)mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
            sBirthDate		= (String)mapresult.get("BIRTHDATE");
            sGender			= (String)mapresult.get("GENDER");
            sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
            sDupInfo		= (String)mapresult.get("DI");
            sConnInfo		= (String)mapresult.get("CI");
            sMobileNo		= (String)mapresult.get("MOBILE_NO");
            sMobileCo		= (String)mapresult.get("MOBILE_CO");

            String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
            if(!sRequestNumber.equals(session_sRequestNumber)){
                sMessage = "세션값 불일치 오류입니다.";
                sResponseNumber = "";
                sAuthType = "";
            }else {
                mv.addObject("mapResult", mapresult);
            }
        } else if( iReturn == -1){
            sMessage = "복호화 시스템 오류입니다.";
        } else if( iReturn == -4){
            sMessage = "복호화 처리 오류입니다.";
        } else if( iReturn == -5){
            sMessage = "복호화 해쉬 오류입니다.";
        } else if( iReturn == -6){
            sMessage = "복호화 데이터 오류입니다.";
        } else if( iReturn == -9){
            sMessage = "입력 데이터 오류입니다.";
        } else if( iReturn == -12){
            sMessage = "사이트 패스워드 오류입니다.";
        } else{
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }

        if(iReturn < 0){
            return new ModelAndView("redirect:/join/sign");
        }

        return mv;
    }

    /**
     * Nice에서 받은 정보 부모창 전송
     *
     * @param session
     * @param EncodeData
     * @return
     * @throws Exception
     */
    @RequestMapping("/join/location")
    public ModelAndView joinLocation(HttpSession session, @RequestParam("EncodeData") String EncodeData, HttpServletResponse response) throws Exception{
        ModelAndView mv = new ModelAndView("login/joinLocation.html");

        String sSiteCode = "BT328";				// NICE로부터 부여받은 사이트 코드
        String sSitePassword = "bd6sxEQv9Sc5";			// NICE로부터 부여받은 사이트 패스워드
        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

        String sPlainData = "";
        String sDupInfo = "";				// 중복가입 확인값 (DI_64 byte)

        int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, EncodeData);

        if( iReturn == 0 ){
            sPlainData = niceCheck.getPlainData();
            // 데이타를 추출합니다.
            java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

            sDupInfo		= (String)mapresult.get("DI");
            int cnt = joinUserService.getUserDuplInfo(sDupInfo);

            if(cnt > 0){
                PrintWriter out = response.getWriter();
                response.setContentType("text/html; charset=utf-8");
                out.println("<script language='javascript'>");
                out.println("alert('이미 가입된 정보 입니다.')");
                out.println("opener.location.href='/user/login'");
                out.println("self.close();");
                out.println("</script>");
                out.flush();
            }
        }

        mv.addObject("EncodeData", EncodeData);
        return mv;
    }

    /**
     * 본인 인증
     *
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/join/sign")
    public ModelAndView joinSignView(HttpSession session) throws Exception{
        ModelAndView mv = new ModelAndView("login/joinSign.html");

        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

        String sSiteCode = "BT328";			// NICE로부터 부여받은 사이트 코드
        String sSitePassword = "bd6sxEQv9Sc5";		// NICE로부터 부여받은 사이트 패스워드

        String sRequestNumber = "REQ0000000001";        	// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로
        // 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
        sRequestNumber = niceCheck.getRequestNO(sSiteCode);
        session.setAttribute("REQ_SEQ" , sRequestNumber);	// 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.

        String sAuthType = "";      	// 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서

        String popgubun 	= "Y";		//Y : 취소버튼 있음 / N : 취소버튼 없음
        String customize 	= "";		//없으면 기본 웹페이지 / Mobile : 모바일페이지

        String sGender = ""; 			//없으면 기본 선택 값, 0 : 여자, 1 : 남자

        // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
        //리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~
        String sReturnUrl = "http://localhost:8080/join/location";      // 성공시 이동될 URL
        String sErrorUrl = "http://localhost:8080/";          // 실패시 이동될 URL

        // 입력될 plain 데이타를 만든다.
        String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize +
                "6:GENDER" + sGender.getBytes().length + ":" + sGender;

        String sMessage = "";
        String sEncData = "";

        int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
        if( iReturn == 0 ){
            sEncData = niceCheck.getCipherData();
        } else if( iReturn == -1){
            sMessage = "암호화 시스템 에러입니다.";
        } else if( iReturn == -2){
            sMessage = "암호화 처리오류입니다.";
        } else if( iReturn == -3){
            sMessage = "암호화 데이터 오류입니다.";
        } else if( iReturn == -9){
            sMessage = "입력 데이터 오류입니다.";
        } else{
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }

        mv.addObject("sEncData", sEncData);

        return mv;
    }

    /**
     * ID 중복 확인 Ajax
     *
     * @param loginId
     * @return
     * @throws Exception
     */
    @GetMapping("/join/duplLoginIdChkAjax")
    public @ResponseBody Map<String, Object> getLoginIdDuplChk(@RequestParam String loginId) throws Exception{
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
    @GetMapping("/join/duplEmailChkAjax")
    public @ResponseBody Map<String, Object> getEmailDuplChk(@RequestParam String email) throws Exception{
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
    @GetMapping("/join/duplTelChkAjax")
    public @ResponseBody Map<String, Object> getTelDuplChk(@RequestParam String tel) throws Exception{
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
    @RequestMapping("/join/confirm")
    @ResponseBody
    public ModelAndView saveUserInfo(@ModelAttribute("joinDto") JoinDto params) throws Exception{
        ModelAndView mv = new ModelAndView("login/joinCnf.html");

        if(params.getLoginId() == null){
            return new ModelAndView("redirect:/join/sign");
        }

        joinUserService.saveUserInfo(params);

        return mv;
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
