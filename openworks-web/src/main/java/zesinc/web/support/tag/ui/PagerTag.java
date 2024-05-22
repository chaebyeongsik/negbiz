/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.ui;

import zesinc.core.lang.Validate;
import zesinc.web.support.pager.Pager;
import zesinc.web.support.tag.OpTagSupport;

/**
 * 목록 페이징 태그. <code>Pager</code> 객체를 입력받아 페이징 UI를 생성한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 5.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class PagerTag extends OpTagSupport {

    /** 페이징 객체 */
    private Pager<?> pager;
    /** 미지정 시 기본 JSP 템플릿 */
    private static final String DEFAULT_JSP = "pager/pager.jsp";
    private static final String DEFAULT_JS = "opMovePage";


    /**
     * 페이징 객체 설정
     * 
     * @param pager
     */
    public void setPager(Pager<?> pager) {
        this.pager = pager;
    }

    /** 페이징 UI에서 페이지 번호를 클릭시 사용될 <code>Javascript</code> 함수 */
    private String script;

    /**
     * <code>Javascript</code> 함수 설정
     * 예) onclick="func()"
     * 
     * @param script
     */
    public void setScript(String script) {
        this.script = script;
    }

    @Override
    protected String getPage() {
        if(Validate.isEmpty(page)) {
            return DEFAULT_JSP;
        }

        return this.page;
    }

    @Override
    protected void beforeTag() {
        addAttribute("pager", pager);
        if(Validate.isEmpty(script)) {
            addAttribute("script", DEFAULT_JS);
        } else {
            addAttribute("script", script);
        }
    }
}
