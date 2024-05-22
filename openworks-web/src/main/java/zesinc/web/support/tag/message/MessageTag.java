/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.message;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import zesinc.web.utils.MessageUtil;

/**
 * 다국어 메시지관리와 연동된 메시지를 JSP 에서 사용할 수 있도록하는 tag lib
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 17.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class MessageTag extends SimpleTagSupport {

    /** 메시지코드값 */
    private String cdId;

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();

        String message = MessageUtil.getMessage(this.cdId);
        writer.write(message);
    }

    /**
     * 메시지 코드값을 설정
     * 
     * @param cdId 을(를) String code로 설정
     */
    public void setCdId(String cdId) {
        this.cdId = cdId;
    }
}
