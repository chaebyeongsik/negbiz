/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.utils.RequestPathMatcher.PathVO;
import zesinc.web.utils.RequestUtil;

/**
 * 관리자단 로그인 체크 Interceptor
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 2. 27.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ManageLoginCheckInterceptor extends BaseInterceptor {

    protected final Log logger = LogFactory.getLog(getClass());

    /** 임시 URL 저장 세션 키 */
    private static String URL_SESSION_KEY = Config.getString("webapp-config.urls.urlSessionKey");
    /** 세션 종료 안내 페이지 */
    private static String MGR_LOST_SESSION = Config.getString("webapp-config.urls.mgrLostSession");

    /**
     * 세션 체크는 모든 URL에 대하여 체크해야 하므로 true만 반환한다.
     */
    @Override
    public boolean supported(Object handler) {

        return true;
    }

    /**
     * 관리자 로그인 정보 세션 만료 여부 확인후 Method가 POST가 아닌 경우에 한하여 이전 페이지로
     * 되돌아 갈수 있도록 세션에 요청 페이지 URL을 저장하여 LOGIN 처리에서 사용할 수 있도록 한다.
     * 
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
     *      (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     *      java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!supported(handler)) {
            return false;
        }

        PathVO resultVo = validate(request);
        if(Validate.isNotEmpty(resultVo)) {

            Object principal = OpHelper.getMgrSession();

            if(Validate.isNull(principal)) {
                String redirectUrl = "";
                String contextPath = request.getContextPath();
                if(Validate.isNotEmpty(contextPath)) {
                    redirectUrl += contextPath;
                }

                // POST 인경우는 등록등의 페이지 일수 있으므로 되돌아갈 페이지로 사용하지 않는다.
                String requestUri = request.getRequestURI();
                if(!request.getMethod().equals("POST") && requestUri.indexOf("BD_") > -1) {
                    String queryString = RequestUtil.getQueryString(request);
                    if(Validate.isNotEmpty(queryString)) {
                        requestUri += "?" + RequestUtil.getQueryString(request);
                    }
                    OpHelper.setSession(request, URL_SESSION_KEY, requestUri);
                }

                response.sendRedirect(redirectUrl + MGR_LOST_SESSION);
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }
}
