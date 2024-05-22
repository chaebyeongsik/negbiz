/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;

/**
 * Cross site scripting 보안 취약점 스크립트 처리 유틸
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2013. 6. 2.    방기배   신규 생성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class XssUtil {

    public static enum TYPE {
        SCRIPT, ALL
    }

    private static Logger logger = LoggerFactory.getLogger(XssUtil.class);

    private static String[] EVENTS = new String[] { "onload", "onclick", "onfocus", "onLoad", "onClick", "onFocus" };
    private static String[] NO_EVENTS = new String[] { "data-trash", "data-trash", "data-trash", "data-trash", "data-trash", "data-trash" };
    private static String[] SCRIPT = new String[] { "<script", "</script>", "<SCRIPT", "</SCRIPT>" };
    private static String[] NO_SCRIPT = new String[] { "&lt;script", "&lt;/script&gt;", "&lt;SCRIPT", "&lt;/SCRIPT&gt;" };

    /**
     * 모든 HTML TAG를 표시문자로 변환한다.
     * 
     * @param content
     * @return
     */
    public static String cleanTag(String content) {
        return cleanTag(content, TYPE.SCRIPT);
    }

    /**
     * type (SCRIPT, ALL) 에 따라서 스크립트 TAG 또는 모든 위협문자에 대한 치환을 한다.
     * 
     * @param content
     * @param type
     * @return
     * @see #cleanAll(String)
     */
    public static String cleanTag(String content, TYPE type) {

        if(type.equals(TYPE.ALL)) {
            return cleanAll(content);
        }

        return cleanScript(content);
    }

    /**
     * 모든 HTML Tag를 단순 표시 문자로 변환
     * 
     * @param content
     * @return
     */
    private static String cleanAll(String content) {

        if(Validate.isEmpty(content)) {
            return "";
        }

        StringBuffer buf = new StringBuffer();
        char[] c = content.toCharArray();
        int len = c.length;
        for(int i = 0 ; i < len ; i++) {
            if(c[i] == '&') {
                buf.append("&amp;");
            } else if(c[i] == '<') {
                buf.append("&lt;");
            } else if(c[i] == '>') {
                buf.append("&gt;");
            } else if(c[i] == '"') {
                buf.append("&quot;");
            } else if(c[i] == '\'') {
                buf.append("&apos;");
            } else if(c[i] == ';') {
                buf.append("&#059;");
            } else if(c[i] == '[') {
                buf.append("&#091;");
            } else if(c[i] == ']') {
                buf.append("&#093;");
            } else if(c[i] == '\\') {
                buf.append("&#092;");
            } else if(c[i] == '=') {
                buf.append("&#061;");
            } else {
                buf.append(c[i]);
            }
        }
        logger.debug("XSS Filter!!  before : {} --> after : {}", content, buf.toString());

        return buf.toString();
    }

    /**
     * JavaScript를 구동 시킬수 있는 <script /> 태그를 치환
     * 
     * @param content
     * @param target
     * @param replace
     * @return
     */
    private static String cleanScript(String content) {

        return cleanEvent(StringUtil.replaceEach(content, SCRIPT, NO_SCRIPT));
    }

    /**
     * JavaScript 문법중 특정 이벤트 속성을 사용되지 않도록 변경.
     * 
     * @param content
     * @return
     */
    private static String cleanEvent(String content) {

        return StringUtil.replaceEach(content, EVENTS, NO_EVENTS);
    }
}
