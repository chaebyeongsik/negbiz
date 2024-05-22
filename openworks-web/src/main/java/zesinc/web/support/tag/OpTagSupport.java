/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import zesinc.web.support.BaseConfig;

/**
 * JSP 파일 템플릿 기반 Taglib 부모 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 14.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public abstract class OpTagSupport extends SimpleTagSupport {

    /** VIEW에 해당하는 JSP 파일 루트 경로 */
    public final String TAGLIB_JSP_ROOT = BaseConfig.INCLUDE_TAGLIB_BASE;

    /** JSP 파일명 */
    protected String page;

    /**
     * JSP 파일 설정
     * 
     * @param page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * 이름에 해당하는 <code>Object</code>를 반환
     * 
     * @param name
     * @return
     */
    protected Object getPageObject(String name) {
        return getJspContext().getAttribute(name, PageContext.PAGE_SCOPE);
    }

    /**
     * JSP 파일에 넘길 <code>Object</code> 추가(키/값 쌍)
     * 
     * @param key
     * @param value
     */
    protected void addAttribute(String key, Object value) {
        getJspContext().setAttribute(key, value, PageContext.REQUEST_SCOPE);
    }

    /**
     * <code>HttpServletRequest</code> 객체의 <code>Attribute</code>를 반환한다.
     * 
     * @param key
     * @param value
     */
    protected Object getAttribute(String key) {
        return getJspContext().getAttribute(key, PageContext.REQUEST_SCOPE);
    }

    /**
     * 개별 구현 대상
     * <p />
     * 우선 처리되어야 할 메소드 정의
     */
    protected void beforeTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {

        ServletConfig cfg = (ServletConfig) getPageObject(PageContext.CONFIG);
        ServletContext sc = cfg.getServletContext();

        String name = TAGLIB_JSP_ROOT + getPage();

        // 파일 존재여부 확인
        if(sc.getResource(name) != null) {
            RequestDispatcher disp = sc.getRequestDispatcher(name);
            if(disp != null) {
                // 전처리 메소드 호출
                beforeTag();
                try {
                    HttpServletRequest request = (HttpServletRequest) getPageObject(PageContext.REQUEST);
                    disp.include(request, new TagResponseWrapper(
                        (HttpServletResponse) getPageObject(PageContext.RESPONSE), new PrintWriter(getJspContext().getOut())));
                } catch (ServletException e) {
                    throw new JspException(e);
                } finally {}

                return;
            }
        } else {
            getJspContext().getOut().write("<div class=\"error\">" + page + " 파일을 찾을 수 업습니다.</div>");
        }
    }

    /**
     * Taglib에서 사용되는 JSP 파일명 설정
     * 
     * @return
     */
    protected abstract String getPage();

}
