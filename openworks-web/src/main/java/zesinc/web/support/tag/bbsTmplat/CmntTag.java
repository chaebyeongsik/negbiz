/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.bbsTmplat;

import zesinc.core.lang.Validate;
import zesinc.web.support.tag.bbsTmplat.support.BbsUiTagSupport;

/**
 * 게시판템플릿 태그 : 이전글 / 다음글
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 7. 17.    황신욱   최초작성
 *  2015. 11. 18.    방기배   재작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmntTag extends BbsUiTagSupport {

    /** jsp */
    private static final String DEFAULT_JSP = "bbsTmplat/cmntList.jsp";

    /**
     * 필드명 설정
     */
    public CmntTag() {
        this.fieldNm = "cmntList";
    }

    @Override
    protected String getPage() {

        if(Validate.isEmpty(this.page)) {
            return DEFAULT_JSP;
        }

        return this.page;
    }

    @Override
    public void beforeTag() {
        addAttribute("bbsObj", getObj());
        addAttribute("bbsType", this.type);
    }
}
