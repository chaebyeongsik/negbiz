/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import zesinc.web.support.BaseConfig;
import zesinc.web.utils.RequestUtil;

/**
 * 프레임워크 지원 클레스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 5. 9.    방기배   신규 등록
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class OpHelper {

    /**
     * 관리자 로그인 세션을 반환
     *
     * @return
     */
    public static Object getMgrSession() {
        Object principal = null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            principal = auth.getPrincipal();
            if(String.class.isAssignableFrom(principal.getClass())) {
                return null;
            }
        }
        return principal;
    }

    /**
     * 사용자 로그인 세션을 반환
     *
     * @return
     */
    public static Object getUserSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        return OpHelper.getSession(request, BaseConfig.USER_SESSION_KEY);
    }

    /**
     * 세션정보 얻기
     *
     * @param request
     * @param sessionKey
     * @return
     */
    public static Object getSession(HttpServletRequest request, String sessionKey) {

        HttpSession session = request.getSession();

        Object sessionAttribute = session.getAttribute(sessionKey);

        return sessionAttribute;
    }

    /**
     * 세션정보 저장
     *
     * @param request
     * @param sessionKey
     * @param sessionValue
     * @return
     */
    public static void setSession(HttpServletRequest request, String sessionKey, Object sessionValue) {

        HttpSession session = request.getSession();

        session.setAttribute(sessionKey, sessionValue);
    }

    /**
     * 세션 삭제
     *
     * @param request
     * @param sessionKey
     */
    public static void removeSession(HttpServletRequest request, String sessionKey) {

        HttpSession session = request.getSession();

        session.removeAttribute(sessionKey);
    }

    /**
     * 모든 파라미터를 URL 형식으로 반환(GET,POST 모두)
     *
     * @param request 요청객체
     * @return 키, 값으로 이루어진 QueryString
     */
    public static String getQueryString(HttpServletRequest request) {

        return RequestUtil.getQueryString(request);
    }

    /**
     * 모든 검색용 파라미터를 URL 형식으로 반환(GET,POST 모두)
     *
     * @param request 요청객체
     * @return 키, 값으로 이루어진 QueryString
     * @throws UnsupportedEncodingException
     */
    public static String getSearchQueryString(HttpServletRequest request) {

        String paramStr = RequestUtil.getSearchQueryString(request);

        // 파라메터중 한글 인코딩처리
        char[] txtChar = paramStr.toCharArray();
        for (int j = 0; j < txtChar.length; j++) {
            if (txtChar[j] >= '\uAC00' && txtChar[j] <= '\uD7A3') {
                String targetText = String.valueOf(txtChar[j]);
                try {
                    paramStr = paramStr.replace(targetText, URLEncoder.encode(targetText, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    System.out.println("Error : UnsupportedEncodingException");
                } catch (Exception e) {
                    System.out.println("Error : Exception");
                }
            }
        }

        return paramStr;
    }

    /**
     * QueryString 문자열 중 특정 parameter의 값을 변경한다.
     *
     * @param queryString
     * @param paramKey
     * @param paramValue
     * @return paramKey의 값이 paramValue의 값으로 대체된 결과 문자열 반환
     */
    public static String getReplaceParamValue(String queryString, String paramKey, Object paramValue) {

        return RequestUtil.getReplaceParamValue(queryString, paramKey, paramValue);
    }

}
