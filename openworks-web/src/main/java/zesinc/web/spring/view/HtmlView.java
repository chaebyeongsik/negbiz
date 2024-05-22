/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import zesinc.web.support.BaseConfig;

/**
 * HTML 문서 유형을 출력한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 4.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class HtmlView extends AbstractView {

    public static final String DEFAULT_CONTENT_TYPE = "text/html;charset="+BaseConfig.CHAR_SET;

    public HtmlView() {
        setContentType(DEFAULT_CONTENT_TYPE);
    }

    @Override
    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(getContentType());
        response.setCharacterEncoding(BaseConfig.CHAR_SET);
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        Object object = model.get(BaseConfig.HTML_DATA_KEY);
        if(object != null) {
            response.getWriter().write(object.toString());
        } else {
            response.getWriter().write("");
        }
    }
}
