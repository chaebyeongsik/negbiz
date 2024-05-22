/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.validate;

import java.util.LinkedHashSet;
import java.util.Set;

import zesinc.web.validate.validator.ValidTypeMsg;
import zesinc.web.validate.validator.ValidateResult;
import zesinc.web.validate.validator.Validator;

/**
 * <code>Validator</code> 목록을 저장한다.
 * <p />
 * 하나의 대상에 하나 이상의 검증이 필요하므로 대상에 적용되야 하는 목록을 만들어 저장하고, 사용한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 2.    방기배   신규 추가
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ValidateGroup {

    /** Validator 목록 */
    private Set<Validator> validatorList = new LinkedHashSet<Validator>();

    /** 검증 결과 상세 */
    private ValidateResult validateResult = new ValidateResult();

    /** 검증 결과 */
    private Boolean valid;

    /**
     * Validator 목록 추가
     * 
     * @param validateor
     * @return
     */
    public ValidateGroup add(Validator validateor) {
        validatorList.add(validateor);
        return this;
    }

    /**
     * Validator 목록 반환
     * 
     * @return
     */
    public Set<Validator> get() {
        return this.validatorList;
    }

    /**
     * 적용된 모든 검증을 수행하고, 검증정보(유형, 오류 메시지, 유효여부)를 리턴한다.
     * 
     * @param value
     * @return
     */
    public ValidateGroup doValidte(String fieldName, Object value) {

        validateResult.setFieldName(fieldName);
        validateResult.setFiledValue(value);

        ValidTypeMsg typeMsg;

        for(Validator validator : validatorList) {
            typeMsg = new ValidTypeMsg();

            typeMsg.setValidType(validator.getValidatorName());
            typeMsg.setValidMsg(validator.getMessgse());
            typeMsg.setValidData(validator.getValidData());

            if(!validator.isValid(value)) {
                this.valid = false;
                validateResult.setValid(false);
            }

            validateResult.addValidMsg(typeMsg);
        }
        if(this.valid == null) {
            this.valid = true;
            validateResult.setValid(true);
        }

        return this;
    }

    /**
     * 검증 결과 반환
     * 
     * @return
     */
    public boolean isValid() {

        if(valid == null) {
            throw new IllegalArgumentException("doValidate() 메소드를 먼저 실행해야 합니다.");
        }

        return valid;
    }

    /**
     * 검증 결과 상세 내용 반환
     * 
     * @return
     */
    public ValidateResult getResult() {
        return this.validateResult;
    }
}
