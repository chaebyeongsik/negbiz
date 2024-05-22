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
import zesinc.web.validate.annotation.marker.NotNull;
import zesinc.web.validate.validator.AbstractValidator;
import zesinc.web.validate.validator.Validator;

/**
 * <code>not null</code> 확인. <code>null</code> 인 경우 <code>false</code>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 4.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class NotNullValidator extends AbstractValidator {

    @Override
    public Validator setAnnotation(Annotation annotation) {

        NotNull anno = (NotNull) annotation;
        this.message = anno.message();

        return this;
    }

    /**
     * <code>not null</code> 확인
     */
    @Override
    protected boolean doValidate(Object propertyValue) {

        if(null == propertyValue) {
            return false;
        }

        return Validate.isNotNull(propertyValue);
    }

}
