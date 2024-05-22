/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.help;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import zesinc.core.cache.CacheService;
import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.cache.HpcmCacheVO;


/**
 * 도움말 태그
 *
 *<pre>
 *<< 개정이력(Modification Information) >>
 *   
 *    수정일       수정자   수정내용
 *--------------  --------  -------------------------------
 * 2015. 4. 20.    ZES-INC   최초작성
 *</pre>
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class HelpTag extends SimpleTagSupport {

    /** 메뉴 코드 */
    private Integer menuSn;
    /** 도움말 목록 */
    private HashMap<Integer, HpcmCacheVO> helpMap;

    /**
     * 도움말목록으로 html tag를 생성한다.
     */
    @Override
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();

        try {
            this.helpMap = helpInfoMap();
        } catch (Exception e) {
            writer.write(e.toString() + "<br />");
            return;
        }

        if(Validate.isEmpty(helpMap)) {
            writer.write("<p>도움말 정보가 없습니다. 도움말을 등록해 주세요.</p>");
            return;
        } else {
            writer.write(createHelpTag());
        }
    }

    /**
     * 도움말 html 생성
     * 
     * @return
     */
    private String createHelpTag() {
        StringBuilder html = new StringBuilder();

        HpcmCacheVO helpVo = new HpcmCacheVO();
        if(helpMap.get(menuSn) != null) {
            helpVo = helpMap.get(menuSn);
            if(helpVo.getDocCn() != null) {
                html.append("<p>" + StringUtil.replace(helpVo.getDocCn(), "\n", "<br />") + "</p>");
            } else {
                html.append("<p>메뉴의 도움말 내용이 없습니다.</p>");
            }
        } else {
            html.append("<p>해당 메뉴로 등록된 도움말 정보가 없습니다.</p>");
        }

        return html.toString();
    }

    /**
     * 도움말 목록을 가져 온다
     * cache에서 추출
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    private HashMap<Integer, HpcmCacheVO> helpInfoMap() {
        // 케쉬에서 도움말 Map을 가져 온다.
        String cacheKey = BaseConfig.HPCM_CACHE_KEY;
        HashMap<Integer, HpcmCacheVO> menuHelpMap = (HashMap<Integer, HpcmCacheVO>) CacheService.get(cacheKey);

        return menuHelpMap;
    }

    /**
     * 메뉴코드 설정
     * @param menuSn 을(를) Integer menuSn로 설정
     */
    public void setMenuSn(Integer menuSn) {

        this.menuSn = menuSn;
    }
}
