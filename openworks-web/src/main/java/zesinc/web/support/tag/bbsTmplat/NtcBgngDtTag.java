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
 * 게시판템플릿 태그 : 공지시작일
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
public class NtcBgngDtTag extends BbsTagSupport {

    /**
     * 필드명 설정
     */
    public NtcBgngDtTag() {
        this.fieldNm = "ntcBgngDt";
    }

    /**
     * 대상 빈에서 해당 필드를 추출하여 값을 출력
     */
    @Override
    public void doTag() throws JspException, IOException {

        JspWriter writer = getJspContext().getOut();

        if("list".equals(this.type)) {
            writer.print(getString());
        } else if("view".equals(this.type)) {
            writer.print(getString());
        } else if("value".equals(this.type)) {
            writer.print(getString());
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
                String value = getString();
                StringBuilder html = new StringBuilder();

                html.append("<input class=\"").append(BbsStyleSupport.BBS_DATE_CLASS).append("\" type=\"date\" name=\"")
                    .append(this.fieldNm).append("\" id=\"").append(this.fieldNm).append("\" value=\"").append(value).append("\"");

                String ntcPstYn = getString("ntcPstYn");
                if(Validate.isNotEmpty(ntcPstYn) && !ntcPstYn.equals("Y")) {
                    html.append(" disabled=\"disabled\"");
                }
                html.append(" />");

                writer.print(html.toString());
            } else {
                writer.print("공지를 사용할 수 없습니다. 게시판 설정을 변경해야 합니다.");
            }
        }
    }
}
