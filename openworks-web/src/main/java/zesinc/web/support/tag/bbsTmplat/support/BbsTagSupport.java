/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.bbsTmplat.support;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import zesinc.core.lang.FieldUtil;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.MessageUtil;
import zesinc.web.utils.XssUtil;
import zesinc.web.vo.cache.BbsConfigCacheVO;
import zesinc.web.vo.cache.BbsItemCacheVO;

/**
 * 게시판 템플릿 생성에 필요한 Tag Lib 공통 기능 지원 클레스
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
public class BbsTagSupport extends SimpleTagSupport {

    /** 시스템구분 intra 또는 user */
    protected static String SYSTEM_KIND = BaseConfig.SYSTEM_KIND;
    /** 게시판 설정 캐시 정보를 가진 객체명 */
    private static String BBS_CONFIG_VO_KEY = BaseConfig.BBS_CONFIG_VO_KEY;
    /** 대상 VO 객체 */
    protected Object obj;
    /** 객체의 변수명 */
    protected String fieldNm;
    /** 표시유형 */
    protected String type;
    /** 설정상 사용금지 */
    protected String NOT_ALLOW = MessageUtil.getMessage("bbs.notAllow");

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

            writer.print(html.toString());
        }
    }

    /**
     * fieldNm에 해당하는 이름을 반환(화면표시용
     * 
     * @return
     */
    protected String getText() {

        return getText(this.fieldNm);
    }

    /**
     * fieldNm에 해당하는 이름을 반환
     * 
     * @return
     */
    protected String getText(String fieldNm) {
        BbsItemCacheVO itemVo = getItem(fieldNm);
        if(Validate.isNotEmpty(itemVo)) {
            return itemVo.getColNm();
        }
        return "";
    }

    /**
     * fieldNm에 해당하는 Object가 가진 값을 반환
     * 
     * @return
     */
    protected Object getValue() {

        return getValue(this.fieldNm);
    }

    /**
     * fieldNm에 해당하는 Object가 가진 값을 반환
     * 
     * @return
     */
    protected Object getValue(String fieldNm) {
        Object value = null;
        if(Validate.isNotEmpty(getObj())) {
            value = FieldUtil.getValue(getObj(), fieldNm);
        }

        return value;
    }

    /**
     * fieldNm에 해당하는 Object가 가진 문자열 값을 반환
     * 
     * @return
     */
    protected String getString() {

        return getString(this.fieldNm);
    }

    /**
     * fieldNm에 해당하는 Object가 가진 문자열 값을 반환
     * 
     * @return
     */
    protected String getString(String fieldNm) {
        Object value = null;
        if(Validate.isNotEmpty(getObj())) {
            value = FieldUtil.getValue(getObj(), fieldNm);
        }
        if(Validate.isEmpty(value)) {
            return "";
        }

        return value.toString();
    }

    /**
     * fieldNm에 해당하는 Object가 가진 Integer 값을 반환
     * 
     * @return
     */
    protected Integer getInteger() {

        return getInteger(this.fieldNm);
    }

    /**
     * fieldNm에 해당하는 Object가 가진 Integer 값을 반환
     * 
     * @return
     */
    protected Integer getInteger(String fieldNm) {
        Integer value = null;
        Object obj = getObj();
        if(Validate.isNotEmpty(obj)) {
            value = (Integer) FieldUtil.getValue(obj, fieldNm);
        }

        return value;
    }

    /**
     * fieldNm에 해당하는 Object가 가진 값을 반환
     * 
     * @return
     */
    protected String getDesc() {
        BbsItemCacheVO itemVo = getItem(this.fieldNm);
        String desc = "";
        StringBuilder html = new StringBuilder();

        if(Validate.isNotEmpty(itemVo) && Validate.isNotEmpty(itemVo.getBbsColExpln())) {
            desc = itemVo.getBbsColExpln();

            html.append("<span class=\"help-block\">");
            html.append(desc);
            html.append("</span>");
        }

        return html.toString();
    }

    /**
     * 현재 게시판의 환경설정 객체를 반환
     * 
     * @return
     */
    protected BbsConfigCacheVO getConfig() {
        BbsConfigCacheVO bbsConfigVo = (BbsConfigCacheVO) getJspContext().getAttribute(BBS_CONFIG_VO_KEY, PageContext.REQUEST_SCOPE);

        return bbsConfigVo;
    }

    /**
     * 현재 게시판의 아이템을 Map으로 반환. 키는 맴버변수명을 사용(fieldNm)
     * 
     * @return
     */
    protected Map<String, BbsItemCacheVO> getItemMap() {

        return getConfig().getBbsItemMap();
    }

    /**
     * 현재 태그에 해당하는 컬럼 캐시 VO 정보를 반환
     * 
     * @return
     */
    protected BbsItemCacheVO getItem() {

        return getItemMap().get(this.fieldNm);
    }

    /**
     * 지정한 fieldNm 해당하는 컬럼 캐시 VO 정보를 반환
     * 
     * @param fieldNm
     * @return
     */
    protected BbsItemCacheVO getItem(String fieldNm) {

        return getItemMap().get(fieldNm);
    }

    /**
     * 현재 태그에 해당하는 label 태그 내용을 생성
     * 
     * @return
     */
    protected String makeLabel() {

        return makeLabel(this.fieldNm);
    }

    /**
     * 지정한 fieldNm 해당하는 label 태그 내용을 생성
     * 
     * @param fieldNm
     * @return
     */
    protected String makeLabel(String fieldNm) {

        BbsItemCacheVO itemVo = getItem(fieldNm);

        StringBuilder html = new StringBuilder();
        html.append("<label for=\"").append(fieldNm).append("\" class=\"").append(BbsStyleSupport.BBS_LABEL_CLASS).append("\">");

        // 필수여부에 따라 마스크
        if(itemVo.getEsntlYn().equals("Y")) {
            html.append("<span class=\"").append(BbsStyleSupport.BBS_REQUIRE_CLASS).append("\">*</span>");
        }

        html.append(getText(fieldNm)).append("</label>");

        return html.toString();
    }

    /**
     * 개시물 객체 반환
     * 
     * @return
     */
    protected Object getObj() {

        return this.obj;
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
     * 값 추출 대상 FieldNm
     * 
     * @param obj
     */
    public void setFieldNm(String fieldNm) {
        this.fieldNm = fieldNm;
    }

    /**
     * 출력할 값의 유형을 설정
     * label / desc / text / value / list / view / form 중 하나
     * 
     * @param type 을(를) String type로 설정
     */
    public void setType(String type) {
        this.type = type;
    }
}
