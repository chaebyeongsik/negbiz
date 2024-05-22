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
import zesinc.web.utils.Converter;
import zesinc.web.vo.cache.BbsConfigCacheVO;

/**
 * 게시판템플릿 태그 : 답변내용
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
public class AnsDtlCnTag extends BbsTagSupport {

    /**
     * 필드명 설정
     */
    public AnsDtlCnTag() {
        this.fieldNm = "ansDtlCn";
    }

    /**
     * 대상 빈에서 해당 필드를 추출하여 값을 출력
     */
    @Override
    public void doTag() throws JspException, IOException {

        JspWriter writer = getJspContext().getOut();

        if("list".equals(this.type)) {
            String value = getString();
            writer.print(Converter.translateBR(value));
        } else if("view".equals(this.type)) {
            String value = getString();
            writer.print(Converter.translateBR(value));
        } else if("value".equals(this.type)) {
            writer.print(getValue());
        } else if("text".equals(this.type)) {
            writer.print(getText());
        } else if("label".equals(this.type)) {
            writer.print(makeLabel());
        } else if("desc".equals(this.type)) {
            writer.print(getDesc());
        } else if("form".equals(this.type)) {
            BbsConfigCacheVO configVo = getConfig();
            String value = getString();
            String editorUseYn;
            if(SYSTEM_KIND.equals("intra")) {
                editorUseYn = configVo.getMngrEdtrUseYn();
            } else {
                editorUseYn = configVo.getUserEdtrUseYn();
            }
            StringBuilder html = new StringBuilder();
            html.append("<textarea class=\"").append(BbsStyleSupport.BBS_TEXTAREA_CLASS).append("\" name=\"").append(this.fieldNm).append("\" id=\"")
                .append(this.fieldNm).append("\" rows=\"5\" cols=\"80\">").append(value).append("</textarea>");

            if(editorUseYn.equals("Y")) {
                html.append("<script type=\"text/javascript\">");
                html.append("    //<![CDATA[\n");
                html.append("    $().ready(function() {\n");
                html.append("       $(\"#").append(this.fieldNm).append("\").ckeditor({\n");
                html.append("            toolbar : toolbar_config.default_toolbar,\n");
                html.append("            filebrowserImageUploadUrl: \"/component/file/ND_fileUpload.do\"\n");
                html.append("        });\n");
                html.append("    });\n");
                html.append("    //]]>\n");
                html.append("</script>");
            }
            writer.print(html.toString());
        }
    }
}
