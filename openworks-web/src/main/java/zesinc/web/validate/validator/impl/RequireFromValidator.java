/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.validate.validator.impl;

import java.lang.annotation.Annotation;

import zesinc.core.lang.FieldUtil;
import zesinc.core.lang.Validate;
import zesinc.web.validate.annotation.marker.RequireFrom;
import zesinc.web.validate.validator.AbstractValidator;
import zesinc.web.validate.validator.Validator;

/**
 * 대상 필드의 값이 존재하고, 값이 동일한 경우 필수 여부 확인
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
public class RequireFromValidator extends AbstractValidator {

    /** 대상 필드명 */
    private String fieldName;
    /** 대상 필드의 값(비교 값) */
    private String fieldValue;
    /** 대상 필드의 한글 명(메시지 포멧 용) */
    private String fieldDesc;

    @Override
    public Validator setAnnotation(Annotation annotation) {

        RequireFrom anno = (RequireFrom) annotation;
        this.message = anno.message();
        this.fieldName = anno.fieldName();
        this.fieldValue = anno.fieldValue();
        this.fieldDesc = anno.fieldDesc();

        String msg1 = String.valueOf(this.fieldName);
        String msg2 = String.valueOf(this.fieldValue);
        String msg3 = String.valueOf(this.fieldDesc);

        // 메시지 포멧용 데이터 설정
        this.validData = new String[] { "\'" + msg1 + "\'", "\'" + msg2 + "\'", "\'" + msg3 + "\'" };

        return this;
    }

    @Override
    protected boolean doValidate(Object propertyValue) {

        Object value = FieldUtil.getValue(this.currentVo, fieldName);

        if(Validate.isEmpty(value)) {
            return true;
        }

        String compareValue = String.valueOf(value);
        if(!compareValue.equals("null") && this.fieldValue.equals(compareValue)) {
            return Validate.isNotEmpty(propertyValue);
        }

        return true;
    }

}
