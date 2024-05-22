/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.bbsTmplat;

import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.support.tag.bbsTmplat.support.BbsUiTagSupport;

/**
 * 해당 화면에 따른 버튼 자동 생성
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 11. 15.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ButtonTag extends BbsUiTagSupport {

    /** jsp */
    private static final String DEFAULT_JSP = "bbsTmplat/button.jsp";

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

        Object sessVo = null;
        if(SYSTEM_KIND.equals("intra")) {
            sessVo = OpHelper.getMgrSession();
        } else {
            sessVo = OpHelper.getUserSession();
        }
        addAttribute(BaseConfig.KEY_LOGIN_VO, sessVo);
    }
}
