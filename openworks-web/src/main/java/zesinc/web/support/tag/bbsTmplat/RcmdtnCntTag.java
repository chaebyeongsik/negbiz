/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.bbsTmplat;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import zesinc.web.support.tag.bbsTmplat.support.BbsStyleSupport;
import zesinc.web.support.tag.bbsTmplat.support.BbsTagSupport;
import zesinc.web.vo.cache.BbsConfigCacheVO;

/**
 * 게시판템플릿 태그 : 추천횟수
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 7. 17.    황신욱   최초작성
 *  2015. 11. 1.    방기배   재작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class RcmdtnCntTag extends BbsTagSupport {

    /**
     * 필드명 설정
     */
    public RcmdtnCntTag() {
        this.fieldNm = "rcmdtnCnt";
    }

    /**
     * 대상 빈에서 해당 필드를 추출하여 값을 출력
     */
    @Override
    public void doTag() throws JspException, IOException {

        JspWriter writer = getJspContext().getOut();

        if("list".equals(this.type)) {
            writer.print(getValue());
        } else if("view".equals(this.type)) {
            BbsConfigCacheVO bbsConfigVo = getConfig();
            Integer bbsSn = getInteger("bbsSn");
            String bbsDocNo = getString("bbsDocNo");
            Integer inqCntLmtHrCnt = bbsConfigVo.getInqCntLmtHrCnt();
            Integer value = getInteger();

            StringBuilder html = new StringBuilder();

            html.append("<input type=\"button\" id=\"").append(this.fieldNm).append("\" value=\"추천 ").append(value)
                .append("\" class=\"").append(BbsStyleSupport.BBS_INNER_BUTTON_CLASS).append("\" onclick=\"opUpdateRecomend(this, '")
                .append(bbsSn).append("', '").append(bbsDocNo).append("', '").append(inqCntLmtHrCnt).append("');\" />");

            writer.print(html.toString());
        } else if("value".equals(this.type)) {
            writer.print(getValue());
        } else if("text".equals(this.type)) {
            writer.print(getText());
        } else if("label".equals(this.type)) {
            writer.print(makeLabel());
        } else if("desc".equals(this.type)) {
            writer.print(getDesc());
        } else if("form".equals(this.type)) {
            Integer value = getInteger();
            StringBuilder html = new StringBuilder();

            html.append("<input class=\"").append(BbsStyleSupport.BBS_INPUT_CLASS).append("\" type=\"text\" name=\"").append(this.fieldNm)
                .append("\" id=\"").append(this.fieldNm).append("\" value=\"").append(value).append("\" />");

            writer.print(html.toString());
        }

    }
}
