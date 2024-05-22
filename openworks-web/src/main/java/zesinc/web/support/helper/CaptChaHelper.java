/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.helper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import zesinc.core.config.Config;
import zesinc.core.http.METHOD;
import zesinc.core.http.PROTOCOL;
import zesinc.core.lang.Validate;
import zesinc.core.utils.HttpUtil;

/**
 * Google recaptcha 인증 지원 도움 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 9. 22.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CaptChaHelper {

    /** Google recaptcha 등록 키 */
    private static String SECRET_KEY = Config.getString("webapp-config.captcha.secretKey");

    /**
     * Captcha 인증토큰 확인
     */
    public static boolean isValid(HttpServletRequest request) {
        String capthaStr = request.getParameter("opCaptchaResponse");
        if(Validate.isEmpty(capthaStr)) {
            return false;
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("secret", SECRET_KEY);
        param.put("response", capthaStr);
        String result = HttpUtil.get(PROTOCOL.HTTPS, "www.google.com", 443, "/recaptcha/api/siteverify", param, 3000, METHOD.POST);
        if(result.indexOf("success") > -1 && result.indexOf("true") > -1) {
            return true;
        }
        return false;
    }

}
