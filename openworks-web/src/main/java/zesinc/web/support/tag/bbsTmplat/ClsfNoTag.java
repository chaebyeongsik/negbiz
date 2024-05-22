/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.bbsTmplat;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import zesinc.core.lang.Validate;
import zesinc.web.support.tag.bbsTmplat.support.BbsStyleSupport;
import zesinc.web.support.tag.bbsTmplat.support.BbsTagSupport;
import zesinc.web.utils.MessageUtil;
import zesinc.web.vo.cache.BbsConfigCacheVO;
import zesinc.web.vo.cache.BbsCtgryCacheVO;

/**
 * 게시판템플릿 태그 : 분류코드
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
public class ClsfNoTag extends BbsTagSupport {

    /**
     * 필드명 설정
     */
    public ClsfNoTag() {
        this.fieldNm = "clsfNo";
    }

    /**
     * 대상 빈에서 해당 필드를 추출하여 값을 출력
     */
    @Override
    public void doTag() throws JspException, IOException {

        JspWriter writer = getJspContext().getOut();
        BbsConfigCacheVO bbsConfigVo = getConfig();

        if("list".equals(this.type)) {
            String clsfNm = getString("clsfNm");
            writer.print(clsfNm);
        } else if("view".equals(this.type)) {
            String clsfNm = getString("clsfNm");
            writer.print(clsfNm);
        } else if("value".equals(this.type)) {
            Integer clsfNo = getInteger();
            writer.print(clsfNo);
        } else if("text".equals(this.type)) {
            writer.print(getText());
        } else if("label".equals(this.type)) {
            writer.print(makeLabel());
        } else if("desc".equals(this.type)) {
            writer.print(getDesc());
        } else if("form".equals(this.type)) {
            StringBuilder html = new StringBuilder();
            Integer clsfNo = getInteger();

            html.append("<select id=\"").append(fieldNm).append("\" name=\"").append(fieldNm).append("\" class=\"")
                .append(BbsStyleSupport.BBS_SELECT_CLASS).append("\" onchange=\"opLoadLwrkClsfSn(this, '")
                .append(bbsConfigVo.getBbsSn()).append("', 'lwrkClsfSn');\" >");
            List<BbsCtgryCacheVO> ctgryList = bbsConfigVo.getBbsCtgryList();
            if(Validate.isNotEmpty(ctgryList)) {
                html.append("<option value=\"\">").append(MessageUtil.getMessage("common.selectOption")).append("</option>");
                for(BbsCtgryCacheVO bbsCtgryVo : ctgryList) {
                    if(bbsCtgryVo.getClsfNo().equals(clsfNo)) {
                        html.append("<option value=\"").append(bbsCtgryVo.getClsfNo()).append("\" selected=\"selected\">")
                            .append(bbsCtgryVo.getClsfNm()).append("</option>");
                    } else {
                        html.append("<option value=\"").append(bbsCtgryVo.getClsfNo()).append("\">").append(bbsCtgryVo.getClsfNm()).append("</option>");
                    }
                }
            } else {
                html.append("<option value=\"\">").append(MessageUtil.getMessage("bbs.emptyCl")).append("</option>");
            }
            html.append("</select>");

            writer.print(html.toString());
        }
    }
}
