/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import zesinc.core.cache.CacheService;
import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.MessageUtil;
import zesinc.web.vo.cache.CodeCacheVO;

/**
 * 코드를 jsp에서 taglib로 사용하기 위한 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 9.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CdIdTag extends SimpleTagSupport {

    /** 안내문구 */
    private static final String NOT_FOUND = "해당코드를 찾을 수없습니다.";
    private static final String NOT_SUPPORT = "지원되지 않는 유형입니다.";

    /** 줄바꿈문자 */
    private static final String CR = "\n";
    /** 결과를 request에 설정할때 사용되는 attribute 명칭 */
    private static final String RESULT_LIST_NAME = "OpResultCodeList";

    /** 현재값("," 로 복수를 입력할 수 있다.) */
    private Object values;
    /** 코드 */
    private String hghrkCdId;
    /** 코드 */
    private String cdId;
    /** 코드선택자 */
    private String choice;
    /** html id 속성 */
    private String id;
    /** html tag 유형 기본값은 코드명만 표시하는 text */
    private TAG_TYPE type = TAG_TYPE.text;
    /** label css class 속성 인라인 지원 않함 */
    private String labelClass;
    /** label css class 속성 인라인 지원 않함 */
    private String tagClass;
    /** 기타 html tag에 추가하고자하는 문자열 */
    private String etc;
    /** Click 이벤트 처리 함수 */
    private String click;
    /** Change 이벤트 처리 함수 */
    private String change;
    /** select 사용시 화면에 한번에 표시할 줄수 기본값은 1 */
    private String size;
    /** select tag의 기본 option 문구 */
    private String defaultOption;

    @Override
    public void doTag() throws JspException, IOException {

        JspWriter writer = getJspContext().getOut();

        // 목록의 경우는 request 객체를 통하여 사용한다.
        if(type.equals(TAG_TYPE.list)) {
            createList();
        } else {
            // 기초값 초기화
            init();
            if(type.equals(TAG_TYPE.text)) {
                writer.write(createText());
            } else if(type.equals(TAG_TYPE.select)) {
                writer.write(createSelectTag());
            } else if(type.equals(TAG_TYPE.checkbox)) {
                writer.write(createCheckbox());
            } else if(type.equals(TAG_TYPE.radio)) {
                writer.write(createRadio());
            } else {
                writer.write(NOT_SUPPORT);
            }
        }
    }

    /**
     * 기본값 초기화
     */
    private void init() {
        if(Validate.isEmpty(this.labelClass)) {
            if(type.equals(TAG_TYPE.select)) {
                this.labelClass = "sr-only";
            } else if(type.equals(TAG_TYPE.checkbox)) {
                this.labelClass = "checkbox-inline";
            } else if(type.equals(TAG_TYPE.radio)) {
                this.labelClass = "radio-inline radio-primary";
            }
        }
        if(Validate.isEmpty(this.tagClass)) {
            if(type.equals(TAG_TYPE.select)) {
                this.tagClass = "select";
            } else if(type.equals(TAG_TYPE.checkbox)) {
                this.tagClass = "styled";
            } else if(type.equals(TAG_TYPE.radio)) {
                this.tagClass = "styled";
            }
        }
    }

    /**
     * values에 담긴 코드들만 목록으로 생성하여 OpResultCodeList 값으로 request에 추가한다.
     * 사용예 :
     * List&lt;CodeCacheVO&gt; resultCodeList = request.getAttribute("OpResultCodeList");
     * <p />
     * &lt;c:forEach ... items="OpResultCodeList" /&gt
     * 
     * @return
     */
    protected void createList() {

        List<CodeCacheVO> cacheCodeList = getCodeListCache(this.cdId);
        List<String> values = getValues();
        // 둘중 하나라도 값이 없는 경우 빈 List 반환
        if(Validate.isEmpty(cacheCodeList)) {
            getJspContext().setAttribute(RESULT_LIST_NAME, Collections.EMPTY_LIST, PageContext.PAGE_SCOPE);
        }

        if(Validate.isEmpty(values)) {
            getJspContext().setAttribute(RESULT_LIST_NAME, cacheCodeList, PageContext.PAGE_SCOPE);
        } else {
            List<CodeCacheVO> resultCodeList = new ArrayList<CodeCacheVO>();
            CodeCacheVO targetVo;

            for(CodeCacheVO cacheCode : cacheCodeList) {
                for(String cdId : values) {
                    if(cacheCode.getCdId().equals(cdId)) {
                        targetVo = createCodeCacheVo(cacheCode);
                        // 다국어 사용시
                        if(Validate.isNotEmpty(cacheCode.getMtlngCdNm())) {
                            targetVo.setCdNm(MessageUtil.getMessage(cacheCode.getMtlngCdNm()));
                        }
                        resultCodeList.add(targetVo);
                    }
                }
            }
            getJspContext().setAttribute(RESULT_LIST_NAME, resultCodeList, PageContext.PAGE_SCOPE);
        }
    }

    /**
     * 다국어 처리를 위하여 복제한 VO를 사용한다. 원본정보가 변경되면 안되므로.
     * 
     * @param sourceVo
     * @return
     */
    private CodeCacheVO createCodeCacheVo(CodeCacheVO sourceVo) {

        CodeCacheVO targetVo = new CodeCacheVO();
        targetVo.setCdId(sourceVo.getCdId());
        targetVo.setCdChcId(sourceVo.getCdChcId());
        targetVo.setCdExpln(sourceVo.getCdExpln());
        targetVo.setCdNm(sourceVo.getCdNm());
        targetVo.setHghrkCdId(sourceVo.getHghrkCdId());
        targetVo.setUpCdId(sourceVo.getUpCdId());
        targetVo.setUpCdIdNm(sourceVo.getUpCdIdNm());
        targetVo.setMtlngCdNm(sourceVo.getMtlngCdNm());
        targetVo.setPbadmsStdCdId(sourceVo.getPbadmsStdCdId());
        targetVo.setPbadmsStdCdYn(sourceVo.getPbadmsStdCdYn());

        return targetVo;
    }

    /**
     * 코드명 text 생성
     * 
     * @return
     */
    protected String createText() {
        CodeCacheVO codeVo = getCodeMapCache();
        if(codeVo != null) {
            String cdNm = "";
            // 다국어 사용시
            if(Validate.isNotEmpty(codeVo.getMtlngCdNm())) {
                cdNm = MessageUtil.getMessage(codeVo.getMtlngCdNm());
            } else {
                cdNm = codeVo.getCdNm();
            }
            return cdNm;
        }

        return NOT_FOUND;
    }

    /**
     * select html 생성
     * 
     * @return
     */
    protected String createSelectTag() {
        StringBuilder select = new StringBuilder();
        List<CodeCacheVO> codeList = getCodeListCache(this.cdId);

        if(Validate.isEmpty(codeList)) {
            return NOT_FOUND;
        }

        select.append("<select name=\"");
        select.append(this.id);
        select.append("\" ");
        select.append("id=\"");
        select.append(this.id);
        select.append("\" ");

        if(Validate.isNotEmpty(this.size)) {
            select.append("size=\"");
            select.append(this.size);
            select.append("\" ");

            int valCnt = getValues().size();
            if(valCnt > 1) {
                select.append("multiple=true ");
            }

        }

        if(Validate.isNotEmpty(this.click)) {
            select.append("onclick=\"");
            select.append(this.click);
            select.append("\" ");
        }

        if(Validate.isNotEmpty(this.change)) {
            select.append("onchange=\"");
            select.append(this.change);
            select.append("\" ");
        }

        if(Validate.isNotEmpty(this.tagClass)) {
            select.append("class=\"");
            select.append(this.tagClass);
            select.append("\" ");
        }

        if(Validate.isNotEmpty(this.etc)) {
            select.append(this.etc);
        }

        select.append(" >");
        select.append(CR);

        if(Validate.isNotEmpty(this.defaultOption)) {
            select.append("<option value=\"\">");
            select.append(this.defaultOption);
            select.append("</option>");
        } else {
            select.append("<option value=\"\">");
            select.append(MessageUtil.getMessage("common.selectOption"));
            select.append("</option>");
        }
        select.append(CR);

        String cdNm;
        for(CodeCacheVO codeVo : codeList) {
            select.append("<option value=\"");
            select.append(codeVo.getCdId());
            select.append("\" ");
            select.append(createStatus(codeVo.getCdId()));
            select.append(">");

            // 다국어 사용시
            if(Validate.isNotEmpty(codeVo.getMtlngCdNm())) {
                cdNm = MessageUtil.getMessage(codeVo.getMtlngCdNm());
            } else {
                cdNm = codeVo.getCdNm();
            }

            select.append(cdNm);
            select.append("</option>");
            select.append(CR);
        }

        select.append("</select>");
        select.append(CR);

        return select.toString();
    }

    /**
     * checkbox html 생성
     * 
     * @return
     */
    protected String createCheckbox() {
        StringBuilder checkbox = new StringBuilder();
        List<CodeCacheVO> codeList = getCodeListCache(this.cdId);

        if(Validate.isEmpty(codeList)) {
            return NOT_FOUND;
        }

        String cdNm;
        for(CodeCacheVO codeVo : codeList) {
            checkbox.append("<label for=\"");
            checkbox.append(this.id);
            checkbox.append("_");
            checkbox.append(codeVo.getCdId());
            checkbox.append("\" ");
            if(Validate.isNotEmpty(this.labelClass)) {
                checkbox.append("class=\"");
                checkbox.append(this.labelClass);
                checkbox.append("\" ");
            }
            checkbox.append(">");
            checkbox.append(CR);

            checkbox.append("<input type=\"checkbox\" name=\"");
            checkbox.append(this.id);
            checkbox.append("\" ");

            checkbox.append("id=\"");
            checkbox.append(this.id);
            checkbox.append("_");
            checkbox.append(codeVo.getCdId());
            checkbox.append("\" ");

            checkbox.append("value=\"");
            checkbox.append(codeVo.getCdId());
            checkbox.append("\" ");

            if(Validate.isNotEmpty(this.click)) {
                checkbox.append("onclick=\"");
                checkbox.append(this.click);
                checkbox.append("\" ");
            }

            if(Validate.isNotEmpty(this.tagClass)) {
                checkbox.append("class=\"");
                checkbox.append(this.tagClass);
                checkbox.append("\" ");
            }

            if(Validate.isNotEmpty(this.etc)) {
                checkbox.append(this.etc);
            }

            checkbox.append(createStatus(codeVo.getCdId()));

            checkbox.append("/>");
            checkbox.append(CR);

            // 다국어 사용시
            if(Validate.isNotEmpty(codeVo.getMtlngCdNm())) {
                cdNm = MessageUtil.getMessage(codeVo.getMtlngCdNm());
            } else {
                cdNm = codeVo.getCdNm();
            }

            checkbox.append(cdNm);
            checkbox.append("</label>");
            checkbox.append(CR);
        }
        return checkbox.toString();
    }

    /**
     * radio html 생성
     * 
     * @return
     */
    protected String createRadio() {
        StringBuilder radio = new StringBuilder();
        List<CodeCacheVO> codeList = getCodeListCache(this.cdId);

        if(Validate.isEmpty(codeList)) {
            return NOT_FOUND;
        }

        String cdNm;
        for(CodeCacheVO codeVo : codeList) {
            radio.append("<label for=\"");
            radio.append(this.id);
            radio.append("_");
            radio.append(codeVo.getCdId());
            radio.append("\" ");
            if(Validate.isNotEmpty(this.labelClass)) {
                radio.append("class=\"");
                radio.append(this.labelClass);
                radio.append("\" ");
            }
            radio.append(">");
            radio.append(CR);

            radio.append("<input type=\"radio\" name=\"");
            radio.append(this.id);
            radio.append("\" ");

            radio.append("id=\"");
            radio.append(this.id);
            radio.append("_");
            radio.append(codeVo.getCdId());
            radio.append("\" ");

            radio.append("value=\"");
            radio.append(codeVo.getCdId());
            radio.append("\" ");

            if(Validate.isNotEmpty(this.click)) {
                radio.append("onclick=\"");
                radio.append(this.click);
                radio.append("\" ");
            }

            if(Validate.isNotEmpty(this.tagClass)) {
                radio.append("class=\"");
                radio.append(this.tagClass);
                radio.append("\" ");
            }

            if(Validate.isNotEmpty(this.etc)) {
                radio.append(this.etc);
            }
            radio.append(createStatus(codeVo.getCdId()));

            radio.append("/>");
            radio.append(CR);

            // 다국어 사용시
            if(Validate.isNotEmpty(codeVo.getMtlngCdNm())) {
                cdNm = MessageUtil.getMessage(codeVo.getMtlngCdNm());
            } else {
                cdNm = codeVo.getCdNm();
            }

            radio.append(cdNm);
            radio.append("</label>");
            radio.append(CR);
        }
        return radio.toString();
    }

    /**
     * 현재 값을 설정
     * 
     * @param values 을(를) String values로 설정
     */
    public void setValues(Object values) {
        this.values = values;
    }

    /**
     * code을 설정
     * 
     * @param cdId 을(를) String code로 설정
     */
    public void setCdId(String cdId) {
        this.cdId = cdId;
    }

    /**
     * hghrkCdId을 설정
     * 
     * @param hghrkCdId 을(를) String hghrkCdId로 설정
     */
    public void setHghrkCdId(String hghrkCdId) {
        this.hghrkCdId = hghrkCdId;
    }

    /**
     * choice을 설정
     * 
     * @param choice 을(를) String choice로 설정
     */
    public void setChoice(String choice) {
        this.choice = choice;
    }

    /**
     * id/name을 설정
     * 단! radio, checkbox의 경우는 id 문자열에 코드값을 결합하여 사용된다.
     * 
     * @param id 을(를) String id로 설정
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * type을 설정
     * 
     * @param type 을(를) TAG_TYPE type로 설정
     */
    public void setType(String type) {
        this.type = TAG_TYPE.valueOf(type);
    }

    /**
     * label tag의 css Class을 설정
     * 
     * @param labelClass 을(를) String labelClass로 설정
     */
    public void setLabelClass(String labelClass) {
        this.labelClass = labelClass;
    }

    /**
     * html tag(select/input)의 css Class을 설정
     * 
     * @param tagClass 을(를) String tagClass로 설정
     */
    public void setTagClass(String tagClass) {
        this.tagClass = tagClass;
    }

    /**
     * 별도의 문자열 설정 (특별히 사용하여야 할 문장이 있는 경우 활용)
     * 
     * @param etc 을(를) String etc로 설정
     */
    public void setEtc(String etc) {
        this.etc = etc;
    }

    /**
     * click 이벤트 함수 설정
     * 
     * @param click 을(를) String click로 설정
     */
    public void setClick(String click) {
        this.click = click;
    }

    /**
     * change 이벤트 함수 설정
     * 
     * @param change 을(를) String change로 설정
     */
    public void setChange(String change) {
        this.change = change;
    }

    /**
     * select tag 중에 블럭형으로 사용하는 경우 size을 설정
     * 
     * @param size 을(를) String size로 설정
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * select tag의 기본 option 문구 설정
     * 
     * @param defaultOption 을(를) String defaultOption로 설정
     */
    public void setDefaultOption(String defaultOption) {
        this.defaultOption = defaultOption;
    }

    /**
     * 코드에 현재값이 포함되어 있는지 여부
     * 
     * @param cdId
     * @return
     */
    private String createStatus(String cdId) {
        List<String> valueList = getValues();
        if(Validate.isNotEmpty(valueList)) {
            if(valueList.contains(cdId)) {
                if(type.equals(TAG_TYPE.select)) {
                    return "selected=\"true\"";
                }
                return "checked=\"true\"";
            }
        }
        return "";
    }

    /**
     * 리스트 유형으로 값을 변형한다.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<String> getValues() {
        List<String> valueList = new ArrayList<String>();
        if(Validate.isNotEmpty(this.values)) {
            // 리스트
            if(this.values instanceof List<?>) {
                return (List<String>) this.values;
            }
            // 문자 배열
            if(this.values instanceof String[]) {
                return Arrays.asList((String[]) this.values);
            }
            // 콤마 구분자
            if(this.values instanceof String) {
                String val = (String) this.values;
                val = StringUtil.deleteWhitespace(val);
                String[] vals = val.split(",");
                return Arrays.asList(vals);
            }
        }

        return valueList;
    }

    /**
     * 캐시에서 코드목록을 반환
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<CodeCacheVO> getCodeListCache(String cdId) {
        Map<String, Map<String, List<CodeCacheVO>>> codeCacheMap =
            (Map<String, Map<String, List<CodeCacheVO>>>) CacheService.get(BaseConfig.CODE_LIST_CACHE_KEY);

        String key = this.hghrkCdId + "_" + this.cdId;
        Map<String, List<CodeCacheVO>> codeListMap = codeCacheMap.get(key);

        List<CodeCacheVO> codeList = Collections.EMPTY_LIST;

        if(Validate.isNotEmpty(codeListMap)) {
            if(Validate.isNotEmpty(this.choice)) {
                codeList = codeListMap.get(this.choice);
            } else {
                codeList = codeListMap.get(BaseConfig.DEFAULT_LWPRT_KEY);
            }
        }

        return codeList;
    }

    /**
     * 캐시에서 코드 Map을 반환
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    private CodeCacheVO getCodeMapCache() {
        Map<String, CodeCacheVO> codeMap =
            (Map<String, CodeCacheVO>) CacheService.get(BaseConfig.CODE_MAP_CACHE_KEY);

        String key = this.hghrkCdId + "_" + this.cdId;

        return codeMap.get(key);
    }

    /**
     * html tag 유형
     * 
     * <pre>
     * << 개정이력(Modification Information) >>
     *    
     *     수정일       수정자   수정내용
     * --------------  --------  -------------------------------
     *  2015. 4. 9.    방기배   최초작성
     * </pre>
     * 
     * @author (주)제스아이엔씨 기술연구소
     * @see
     */
    enum TAG_TYPE {
        text, select, radio, checkbox, list
    }
}
