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

import zesinc.core.lang.Validate;
import zesinc.web.support.tag.bbsTmplat.support.BbsStyleSupport;
import zesinc.web.support.tag.bbsTmplat.support.BbsTagSupport;

/**
 * 게시판템플릿 태그 : 공지여부
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
public class OthbcTag extends BbsTagSupport {

    /**
     * 필드명 설정
     */
    public OthbcTag() {
        this.fieldNm = "rlsYn";
    }

    /**
     * 대상 빈에서 해당 필드를 추출하여 값을 출력
     */
    @Override
    public void doTag() throws JspException, IOException {

        JspWriter writer = getJspContext().getOut();

        if("list".equals(this.type)) {
            String value = getString(this.fieldNm);
            if(Validate.isNotEmpty(value) && value.equals("Y")) {
                writer.print("공개");
            } else {
                writer.print("비공개");
            }
        } else if("view".equals(this.type)) {
            writer.print(getString(this.fieldNm));
        } else if("value".equals(this.type)) {
            writer.print(getString(this.fieldNm));
        } else if("text".equals(this.type)) {
            writer.print(getText());
        } else if("label".equals(this.type)) {
            writer.print(makeLabel());
        } else if("desc".equals(this.type)) {
            writer.print(getDesc());
        } else if("form".equals(this.type)) {
            String value = getString(this.fieldNm);
            StringBuilder html = new StringBuilder();

            String yChecked = value.equals("Y") ? " checked=\"checked\" " : "";
            String nChecked = value.equals("N") ? " checked=\"checked\" " : "";

            html.append("    <label for=\"").append(this.fieldNm).append("\" class=\"").append(BbsStyleSupport.BBS_RADIOLABEL_CLASS)
                .append("\" >");
            html.append("    <input type=\"radio\" name=\"").append(this.fieldNm).append("\" id=\"").append(this.fieldNm)
                .append("Y\" value=\"Y\" ").append(yChecked).append(" class=\"").append(BbsStyleSupport.BBS_RADIO_CLASS).append("\" />");
            html.append("공개");
            html.append("    </label>");

            html.append("    <label for=\"").append(this.fieldNm).append("\" class=\"").append(BbsStyleSupport.BBS_RADIOLABEL_CLASS)
                .append("\" >");
            html.append("    <input type=\"radio\" name=\"").append(this.fieldNm).append("\" id=\"").append(this.fieldNm)
                .append("N\" value=\"N\" ").append(nChecked).append(" class=\"").append(BbsStyleSupport.BBS_RADIO_CLASS).append("\" />");
            html.append("비공개");
            html.append("    </label>");

            writer.print(html.toString());
        }
    }
}
