/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.validate.validator.impl;

import java.lang.annotation.Annotation;

import zesinc.core.lang.Validate;
import zesinc.web.validate.annotation.marker.JuminNo;
import zesinc.web.validate.validator.AbstractValidator;
import zesinc.web.validate.validator.Validator;

/**
 * 주민번호 검증 수식에 따라서 입력된 값을 계산 하여 확인
 * <p />
 * 주민번호는 '-'가 포함되어 있는 지는 상관없음
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
public class JuminNoValidator extends AbstractValidator {

    @Override
    public Validator setAnnotation(Annotation annotation) {

        JuminNo anno = (JuminNo) annotation;
        this.message = anno.message();

        return this;
    }

    /**
     * 주민번호가 맞는지 확인
     */
    @Override
    protected boolean doValidate(Object propertyValue) {

        if(null == propertyValue || propertyValue.toString().length() == 0) {
            return true;
        }

        String value = propertyValue.toString();

        return Validate.isJuminNo(value);
    }

}
