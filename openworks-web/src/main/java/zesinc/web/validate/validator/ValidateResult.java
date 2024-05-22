/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.validate.validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 검증 결과 정보를 저장
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 2.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ValidateResult implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -8824416109332640025L;

    /** 검증 대상 필드명 */
    private String fieldName;

    /** 검증 대상 필드의 값 */
    private Object filedValue;

    /** 검증 결과 */
    private Boolean valid = true;

    /** 검증 타입과 결과 메시지 */
    private List<ValidTypeMsg> typeMsgs = new ArrayList<ValidTypeMsg>();

    /**
     * 검증결과 추가
     * 
     * @param msg <code>ValidTypeMsg</code> 추가
     */
    public void addValidMsg(ValidTypeMsg typeMsg) {
        typeMsgs.add(typeMsg);
    }

    /**
     * String fieldName 반환
     * 
     * @return String fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * fieldName 설정
     * 
     * @param fieldName 을(를) String fieldName 설정
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Object filedValue을 반환
     * 
     * @return Object filedValue
     */
    public Object getFiledValue() {
        return filedValue;
    }

    /**
     * filedValue을 설정
     * 
     * @param filedValue 을(를) Object filedValue로 설정
     */
    public void setFiledValue(Object filedValue) {
        this.filedValue = filedValue;
    }

    /**
     * Boolean valid을 반환
     * 
     * @return Boolean valid
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * valid을 설정
     * 
     * @param valid 을(를) Boolean valid로 설정
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * List<ValidTypeMsg> typeMsgs을 반환
     * 
     * @return List<ValidTypeMsg> typeMsgs
     */
    public List<ValidTypeMsg> getTypeMsgs() {
        return typeMsgs;
    }

    /**
     * typeMsgs을 설정
     * 
     * @param typeMsgs 을(를) List<ValidTypeMsg> typeMsgs로 설정
     */
    public void setTypeMsgs(List<ValidTypeMsg> typeMsgs) {
        this.typeMsgs = typeMsgs;
    }

    @Override
    public String toString() {
        return " [fieldName=" + fieldName + ", filedValue=" + filedValue + ", valid=" + valid + ", typeMsgs=" + typeMsgs + "]\n";
    }

}
