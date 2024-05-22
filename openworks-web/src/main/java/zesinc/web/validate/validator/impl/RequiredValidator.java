/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.validate.validator.impl;

import java.lang.annotation.Annotation;
import java.util.Collection;

import zesinc.core.lang.Validate;
import zesinc.web.validate.annotation.marker.Required;
import zesinc.web.validate.validator.AbstractValidator;
import zesinc.web.validate.validator.Validator;

/**
 * 값이 존재하는지 또는 length/size를 갖는 경우 0보다 큰지 여부를 체크
 * <p />
 * <code>not null</code>, object.length() > 0, object.length > 0, Collection.size() > 0
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
public class RequiredValidator extends AbstractValidator {

    @Override
    public Validator setAnnotation(Annotation annotation) {

        Required anno = (Required) annotation;
        this.message = anno.message();

        return this;
    }

    /**
     * <code>not null</code>, object.length() > 0, object.length > 0,
     * collection.size() > 0 를 만족하는지 체크
     */
    @Override
    protected boolean doValidate(Object propertyValue) {

        if(null == propertyValue) {
            return true;
        }

        // 문자열의 경우
        if(propertyValue instanceof CharSequence) {
            String value = propertyValue.toString();
            return Validate.isNotEmpty(value);
        }
        // 숫자형의 경우(0 포함)
        else if(propertyValue instanceof Number) {
            Number value = ((Number) propertyValue).intValue();
            return Validate.isNotEmpty(value);
        }
        // Collection 타입의 경우
        else if(propertyValue instanceof Collection) {
            Collection<?> value = (Collection<?>) propertyValue;
            return Validate.isNotEmpty(value);
        }
        // 기타 이외의 타입
        return Validate.isNotEmpty(propertyValue);
    }

}
