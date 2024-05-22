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

import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;

/**
 * 단순 텍스트 문자열만 출력한다.
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
public class TextView extends AbstractView {

    public static final String DEFAULT_CONTENT_TYPE = "text/plain;charset="+BaseConfig.CHAR_SET;

    public TextView() {
        setContentType(DEFAULT_CONTENT_TYPE);
    }

    @Override
    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        String cont = response.getContentType();
        if(Validate.isEmpty(cont)) {
            response.setContentType(getContentType());
        }
        response.setCharacterEncoding(BaseConfig.CHAR_SET);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
        HttpServletResponse response) throws Exception {

        Object object = model.get(BaseConfig.TEXT_DATA_KEY);

        if(object != null) {
            response.getWriter().write(object.toString());
        } else {
            response.getWriter().write("");
        }
    }
}
