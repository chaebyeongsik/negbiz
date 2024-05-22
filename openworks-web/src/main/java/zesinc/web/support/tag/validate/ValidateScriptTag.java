/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.validate;

import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.tag.OpTagSupport;
import zesinc.web.validate.ValidateResultHolder;

/**
 * <code>Validating Javascript</code> 생성
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 19.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see zes.core.lang.validate.ValidateResultHolder
 * @see zes.core.lang.validate.validator.ValidateResult
 * @see zes.core.lang.validate.validator.ValidTypeMsg
 */
public class ValidateScriptTag extends OpTagSupport {

    /** <code>Javascript</code> 유형 */
    private static enum SCRIPT_TYPE {
        alert("validate/validateAlert.jsp"),
        msgbox("validate/validateMsgBox.jsp"),
        jquery("validate/validateJquery.jsp");

        /** 항목에 따른 jsp 페이지 */
        private String jsp;

        /**
         * 항목에 해당 jsp 설정
         * 
         * @param jsp
         */
        SCRIPT_TYPE(String jsp) {
            this.jsp = jsp;
        }

        /**
         * 항목별 jsp 반환
         * 
         * @return
         */
        public String getJsp() {
            return jsp;
        }
    }

    /** Validate 이후 설정 기본 값 속성명 */
    private static String KEY_VALIDATE = BaseConfig.KEY_VALIDATE;
    /** 필드별 결과 Validate 속성명 */
    private static String KEY_VALIDATE_SCRIPT_KEY = BaseConfig.KEY_VALIDATE_SCRIPT_KEY;
    /** 기본 스크립트 유형 */
    private SCRIPT_TYPE type = SCRIPT_TYPE.alert;
    /** 복수의 벨리데이터 사용시 이름을 구분한다. */
    private String validateName;

    /**
     * Javascript 유형을 설정
     * 
     * <pre>
     * alert : alert("메시지"); 
     * msgbox : jsErrorBox("메시지"); 레이어 유형
     * jquery : jquery.validate.js 파일의 검증 스크립트를 사용
     * </pre>
     * 
     * @param type 을(를) String type로 설정
     */
    public void setType(String type) {

        if(SCRIPT_TYPE.valueOf(type) != null) {
            this.type = SCRIPT_TYPE.valueOf(type);
        }
        this.page = this.type.getJsp();
    }

    /**
     * validateName을 설정
     * 
     * @param validateName 을(를) String validateName로 설정
     */
    public void setValidateName(String validateName) {
        this.validateName = validateName;
    }

    @Override
    protected String getPage() {
        if(this.page == null) {
            return this.type.getJsp();
        }

        return this.page;
    }

    /**
     * 입력폼 name 명에 해당하는 검증 정보 및 검증 결과를 <code>HttpServletRequest</code> 객체에 추가
     */
    @Override
    protected void beforeTag() {

        String keyValidate;
        if(Validate.isNotEmpty(validateName)) {
            keyValidate = validateName;
        } else {
            keyValidate = KEY_VALIDATE;
        }
        addAttribute("validateFuncName", keyValidate);

        ValidateResultHolder holder = (ValidateResultHolder) getAttribute(keyValidate);
        if(holder != null) {
            addAttribute(KEY_VALIDATE_SCRIPT_KEY, holder);
        }
    }

}
