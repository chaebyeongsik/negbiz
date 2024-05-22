/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.validate.validator;

import java.lang.annotation.Annotation;

import zesinc.web.validate.annotation.ValidatorInfo;

/**
 * Annotation Type에 따른 검증기를 반환한다.
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
public class ValidatorFactory {

    /**
     * <code>Annotation</code> 타입에 따른 <code>Validator</code> 반환
     * 
     * @param annotation
     * @return
     */
    public static Validator getInstance(Annotation annotation, Object obj) {

        ValidatorInfo type = annotation.annotationType().getAnnotation(ValidatorInfo.class);
        Validator validator = null;

        try {

            validator = type.validator().newInstance();
            validator.setValidatorName(type.validatorName()).setAnnotation(annotation).setObject(obj);

        } catch (Exception e) {
            // do nothing
        }

        return validator;
    }
}
