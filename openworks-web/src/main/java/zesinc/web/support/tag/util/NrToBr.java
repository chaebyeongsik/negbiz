/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;

/**
 * 줄바꿈 특수문자를 웹표시 형태인 &lt;br&gt;로 변경
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 8. 10.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class NrToBr extends SimpleTagSupport {

    /** 줄바꿈 대상 내용 */
    private String content = null;

    /**
     * 대상 빈에서 해당 필드를 추출하여 값을 출력
     * 
     * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
     */
    @Override
    public void doTag() throws JspException, IOException {

        JspWriter writer = getJspContext().getOut();

        if(Validate.isNotEmpty(content)) {
            writer.print(StringUtil.replace(this.content, "\n", "<br />"));
        } else {
            writer.print("");
        }
    }

    /**
     * 줄바꿈 대상 문자열 설정
     * 
     * @param content 문자열
     */
    public void setContent(String content) {
        this.content = content;
    }
}
