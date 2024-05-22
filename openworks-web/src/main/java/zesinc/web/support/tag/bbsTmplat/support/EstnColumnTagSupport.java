/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.bbsTmplat.support;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import zesinc.core.lang.Validate;
import zesinc.web.utils.Converter;
import zesinc.web.utils.XssUtil;
import zesinc.web.vo.cache.BbsItemCacheVO;

/**
 * 게시판템플릿 태그 : 확장컬럼 지원 Tag
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 10. 27.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class EstnColumnTagSupport extends BbsTagSupport {

    /**
     * 대상 빈에서 해당 필드를 추출하여 값을 출력
     */
    @Override
    public void doTag() throws JspException, IOException {

        JspWriter writer = getJspContext().getOut();

        if("list".equals(this.type)) {
            BbsItemCacheVO itemVo = getItem();

            String value = getString();
            if(Validate.isNotEmpty(itemVo.getColTypeNm()) && itemVo.getColTypeNm().equals(BbsColumnTy.TEXTAREA.name())) {
                value = Converter.cleanHtml(value);
            }
            writer.print(value);
        } else if("view".equals(this.type)) {
            BbsItemCacheVO itemVo = getItem();

            String value = getString();
            if(Validate.isNotEmpty(itemVo.getColTypeNm()) && itemVo.getColTypeNm().equals(BbsColumnTy.TEXTAREA.name())) {
                value = Converter.translateBR(value);
            }
            writer.print(value);
        } else if("value".equals(this.type)) {
            writer.print(getString());
        } else if("text".equals(this.type)) {
            writer.print(getText());
        } else if("label".equals(this.type)) {
            writer.print(makeLabel());
        } else if("desc".equals(this.type)) {
            writer.print(getDesc());

        } else if("form".equals(this.type)) {
            BbsItemCacheVO itemVo = getItem();
            String value = getString();

            StringBuilder html = new StringBuilder();
            if(Validate.isNotEmpty(itemVo.getColTypeNm())) {
                if(itemVo.getColTypeNm().equals(BbsColumnTy.TEXTAREA.name())) {
                    // textarea의 경우는 html 코드를 html ascii 문자로 변환한다.
                    html.append("<textarea class=\"").append(BbsStyleSupport.BBS_TEXTAREA_CLASS).append("\" name=\"").append(this.fieldNm)
                        .append("\" id=\"").append(this.fieldNm).append("\" rows=\"5\" cols=\"80\">")
                        .append(XssUtil.cleanTag(value, XssUtil.TYPE.ALL)).append("</textarea>");
                } else if(itemVo.getColTypeNm().equals(BbsColumnTy.EMAIL.name())) {
                    html.append("<input class=\"").append(BbsStyleSupport.BBS_EMAIL_CLASS).append("\" type=\"email\" name=\"").append(this.fieldNm).
                        append("\" id=\"").append(this.fieldNm).append("\" value=\"").append(value).append("\" />");
                } else if(itemVo.getColTypeNm().equals(BbsColumnTy.DATE.name())) {
                    html.append("<input class=\"").append(BbsStyleSupport.BBS_DATE_CLASS).append("\" type=\"date\" name=\"").append(this.fieldNm)
                        .append("\" id=\"").append(this.fieldNm).append("\" value=\"").append(value).append("\" />");
                } else {
                    html.append("<input class=\"").append(BbsStyleSupport.BBS_INPUT_CLASS).append("\" type=\"text\" name=\"").append(this.fieldNm)
                        .append("\" id=\"").append(this.fieldNm).append("\" value=\"").append(value).append("\" />");
                }
            } else {
                html.append("<input class=\"").append(BbsStyleSupport.BBS_INPUT_CLASS).append("\" type=\"text\" name=\"").append(this.fieldNm)
                    .append("\" id=\"").append(this.fieldNm).append("\" value=\"").append(value).append("\" />");
            }
            writer.print(html);
        }
    }
}
