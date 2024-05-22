/*
 * Copyright (c) 2010 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.util;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import zesinc.core.lang.FieldUtil;
import zesinc.core.utils.StringUtil;

/**
 * <pre>
 * Beans(VO 객체)에서 DB Filed명에 해당하는 필드의 값을 가져 온다.
 * 
 * - 빈의 필드명 또는 메소드 명을 통하여 해당 값을 출력한다.
 * - 기타 유사 기능을 추가 관리한다.
 * </pre>
 *
 *<pre>
 *<< 개정이력(Modification Information) >>
 *   
 *    수정일       수정자   수정내용
 *--------------  --------  -------------------------------
 * 2015. 6. 9.    방기배   최초작성
 *</pre>
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class BeanUtilTag extends SimpleTagSupport {

    /** 대상 VO 객체 */
    private Object obj = null;
    /** DB Field 명(예 :FILE_ID) */
    private String field = null;

    /**
     * 대상 빈에서 해당 필드를 추출하여 값을 출력
     * 
     * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
     */
    @Override
    public void doTag() throws JspException, IOException {

        JspWriter writer = getJspContext().getOut();

        Field field = FieldUtil.getField(this.obj.getClass(), this.field);

        Object value = null;
        try {
            value = field.get(this.obj);
        } catch (Exception e) {
            writer.print(e.getMessage());
        }

        if(null != value) {
            writer.print(value);
        } else {
            writer.print("");
        }
    }

    /**
     * 값 추출 대상 Object
     * 
     * @param obj
     */
    public void setObj(Object obj) {
        this.obj = obj;
    }

    /**
     * 값 추출 대상 메소드명
     * Camel Case 표시법으로 변경한다.
     * 
     * @param field
     */
    public void setField(final String field) {

        this.field = StringUtil.camelCase(field);
    }
}
