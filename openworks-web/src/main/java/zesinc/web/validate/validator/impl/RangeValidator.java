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
import zesinc.web.validate.annotation.marker.Range;
import zesinc.web.validate.validator.AbstractValidator;
import zesinc.web.validate.validator.Validator;

/**
 * 입력된 숫자 값이 최소, 최대값 사이인지 확인
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 3.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class RangeValidator extends AbstractValidator {

    /** 최소값 */
    private Integer min;

    /** 최대값 */
    private Integer max;

    @Override
    public Validator setAnnotation(Annotation annotation) {

        Range anno = (Range) annotation;
        this.message = anno.message();
        this.min = anno.min();
        this.max = anno.max();

        String msg1 = String.valueOf(min);
        String msg2 = this.max < 0 ? "무제한" : String.valueOf(max);
        // 메시지 포멧용 데이터 설정
        this.validData = new String[] { msg1, msg2 };

        return this;
    }

    /**
     * 입력된 숫자 값이 최소, 최대 값 사이인지 확인
     */
    @Override
    protected boolean doValidate(Object propertyValue) {

        if(null == propertyValue || propertyValue.toString().length() == 0) {
            return true;
        }

        String value = propertyValue.toString();

        return Validate.isRangeMatch(value, min, max);
    }

}
