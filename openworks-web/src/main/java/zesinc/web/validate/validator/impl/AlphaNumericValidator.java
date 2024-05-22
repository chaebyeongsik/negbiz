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
import zesinc.web.validate.annotation.marker.AlphaNumeric;
import zesinc.web.validate.validator.AbstractValidator;
import zesinc.web.validate.validator.Validator;

/**
 * 영문(대소문자 포함)와 숫자만으로 이루어진 값인지 확인
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
public class AlphaNumericValidator extends AbstractValidator {

    @Override
    public Validator setAnnotation(Annotation annotation) {

        AlphaNumeric anno = (AlphaNumeric) annotation;
        this.message = anno.message();

        return this;
    }

    /**
     * 영문과 숫자만으로 구성되었는지 확인
     */
    @Override
    protected boolean doValidate(Object propertyValue) {

        if(null == propertyValue || propertyValue.toString().length() == 0) {
            return true;
        }

        String value = propertyValue.toString();

        return Validate.isAlphaNumeric(value);
    }

}
