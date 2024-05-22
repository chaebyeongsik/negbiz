/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;

/**
 * <pre>
 * JavaScript 를 일관적으로 관리하기 위한 태그.
 * names는 해당 스크립트의 약칭을 사용한다.
 * (/config/commons/js-plugins.xml 파일 내용을 참조한다.)
 * <p />
 * type은 현재 openworks, jquery 두개의 네이밍을 지원한다. names가 설정되지 않는 경우 core에 해당하는 항목만 자동으로 설정된다.
 * 
 * 사용 예 :
 * <op:jsTag type="jquery" items="validate,form,blockUI" />
 * <op:jsTag type="openworks" items="validate" />
 * </pre>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 4. 1.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ScriptIncludeTag extends SimpleTagSupport {

    /** 기본키 prefix */
    private String DEFALT_PREFIX = "javascript-config";
    /** 호출시 기본적으로 포함되는 항목 */
    private String DEFALT_KEY = "base";
    /** 별칭 이름 */
    private String items;
    /** libs 또는 openworks */
    private String type = "libs";
    /** 줄바꿈 */
    private String CR = "\n    ";

    /**
     * String items을 반환
     * 
     * @return String items
     */
    public String getItems() {
        return items;
    }

    /**
     * items을 설정
     * 
     * @param items 을(를) String items로 설정
     */
    public void setItems(String items) {
        this.items = items;
    }

    /**
     * String type을 반환
     * 
     * @return String type
     */
    public String getType() {
        return type;
    }

    /**
     * type을 설정
     * 
     * @param type 을(를) String type로 설정
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 네이밍에 해당하는 스크립트들을 생성 출력한다.
     */
    @Override
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();

        List<String> nameList = new LinkedList<String>();

        if(Validate.isNotEmpty(this.items)) {
            nameList.addAll(Arrays.asList(this.items.split(",")));
        } else {
            nameList.add(DEFALT_KEY);
        }

        String html = getScripts(nameList);
        writer.write(html);
    }

    /**
     * JavaScript 및 CSS를 환경설정에서 읽어 설정한다.
     * 
     * @param nameList
     * @return
     */
    private String getScripts(List<String> nameList) {
        HttpServletRequest request = (HttpServletRequest)
            getJspContext().getAttribute(PageContext.REQUEST, PageContext.PAGE_SCOPE);

        String contextPath = request.getContextPath();

        StringBuilder sb = new StringBuilder();
        String prefix = DEFALT_PREFIX + "." + this.type + ".";

        String cont;
        String cssCont;
        String jsCont;

        for(String name : nameList) {
            name = name.trim();
            if(name.contains(".")) {
                cont = Config.getString(prefix + name);
                if(cont != null) {
                    sb.append(cont);
                    sb.append(CR);
                }
            } else {
                cssCont = Config.getString(prefix + name + ".css");
                jsCont = Config.getString(prefix + name + ".js");
                if(cssCont != null) {
                    sb.append(cssCont);
                    sb.append(CR);
                }
                if(jsCont != null) {
                    sb.append(jsCont);
                    sb.append(CR);
                }
            }

        }
        String staticCont = sb.toString();
        if(!staticCont.equals("")) {
            staticCont = StringUtil.replace(staticCont, "${contextPath}", contextPath);
        }

        return staticCont;
    }
}
