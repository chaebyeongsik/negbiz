/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import zesinc.web.support.request.XssHttpServletRequest;

/**
 * XSS 보안 모듈 적용을 위한 필터
 * <p />
 * Multipart 컨텐츠 인경우 Spring 내부 로직에 따르므로 아래 See Also 항목의 클레스를 참조한다.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2013. 6. 2.    방기배   신규 생성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see zesinc.web.support.request.XssHttpServletRequest
 * @see zesinc.web.support.request.XssMultipartHttpServletRequest
 */
public class XSSFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        /*
         * XSS 필터 대상인 경우 파라미터 내용에서 지정된 태그를 제거한다.
         * Multipart 인경우는 본 클레스의 설명부분은 참조한다.
         */
        if(!isMultipartContent(req) && isTarget(req)) {
            chain.doFilter(new XssHttpServletRequest(request), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * web.xml 파일의 XssFilter 설정에서 대상 URI를 설정하고, 그에 따라 적용여부를 결정한다.
     *
     * @param req
     * @return
     */
    public static boolean isTarget(HttpServletRequest request) {

        String uri = request.getRequestURI();

        // 적용 제외 대상 URI 여부 판단
        String excludeUri = request.getSession().getServletContext().getInitParameter("XssExclude");
        if(excludeUri != null && !"".equals(excludeUri)) {
            String[] arrExclude = excludeUri.split(",");

            for(String exclude : arrExclude) {
                exclude = exclude.trim();
                if(uri.startsWith(exclude)) {
                    return Boolean.FALSE;
                }
            }
        }
        // 적용 대상 URI 여부 판단
        String includeUri = request.getSession().getServletContext().getInitParameter("XssInclude");
        if(includeUri != null) {
            String[] arrInclude = includeUri.split(",");

            for(String include : arrInclude) {
                include = include.trim();
                if(uri.startsWith(include)) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 요청이 multipart 컨텐츠 인지 확인
     *
     * @param request
     * @return
     */
    public static final boolean isMultipartContent(HttpServletRequest request) {
        if(!("post".equals(request.getMethod().toLowerCase()))) {
            return false;
        }
        String contentType = request.getContentType();
        if(contentType == null) {
            return false;
        }

        return (contentType.toLowerCase().startsWith("multipart/"));
    }

}
