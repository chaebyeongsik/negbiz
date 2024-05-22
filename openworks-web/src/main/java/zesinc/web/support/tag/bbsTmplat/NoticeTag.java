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
import zesinc.web.vo.cache.BbsConfigCacheVO;

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
public class NoticeTag extends BbsTagSupport {

    /**
     * 필드명 설정
     */
    public NoticeTag() {
        this.fieldNm = "ntcPstYn";
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
                writer.print("공지");
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
            BbsConfigCacheVO bbsConfigVo = getConfig();
            // 공지사용 여부 우선 확인
            if(bbsConfigVo.getNtcUseYn().equals("Y")) {
                String value = getString(this.fieldNm);
                StringBuilder html = new StringBuilder();

                String checked = value.equals("Y") ? " checked=\"checked\" " : "";

                html.append("    <label for=\"").append(this.fieldNm).append("\" class=\"").append(BbsStyleSupport.BBS_CHECKBOXLABEL_CLASS)
                    .append("\" >");
                html.append("    <input type=\"checkbox\" name=\"").append(this.fieldNm).append("\" id=\"").append(this.fieldNm)
                    .append("\" value=\"Y\" onclick=\"opActivityNotice(this);\" ").append(checked).append("class=\"")
                    .append(BbsStyleSupport.BBS_CHECKBOX_CLASS).append("\" />");
                html.append("공지로 지정");
                html.append("    </label>");

                html.append("<script type=\"text/javascript\">");
                html.append("    //<![CDATA[ \n\r");
                html.append("    var opActivityNotice = function(elObj) {");
                html.append("        var isChecked = $(elObj).prop(\"checked\");");
                html.append("        $(\"#ntcBgngDt\").prop(\"disabled\", !isChecked);");
                html.append("        $(\"#ntcEndDt\").prop(\"disabled\", !isChecked);");
                html.append("        $(\"#pstgBgngDt\").prop(\"disabled\", isChecked);");
                html.append("        $(\"#pstgEndDt\").prop(\"disabled\", isChecked);");
                html.append("    }; \n\r");
                html.append("    //]]>");
                html.append("</script>");

                writer.print(html.toString());
            } else {
                writer.print("공지를 사용할 수 없습니다. 게시판 설정을 변경해야 합니다.");
            }
        }
    }
}
