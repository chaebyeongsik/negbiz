/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.validate.annotation.marker;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import zesinc.web.validate.annotation.ValidatorInfo;
import zesinc.web.validate.validator.impl.RangeLengthValidator;

/**
 * 문자열 값의 길이를 확인. 최소길이의 기본 값은 0
 * <p/>
 * min=3, max=20 인 경우 "1234" : true, "12" : false
 * 
 * <pre>
 * 사용 예)
 * &#64;RangeLength(min=3, max=20) 
 * &#64;RangeLength(max=20)
 * 기본값
 * min() 최소길이 0
 * max() 최대길이 2000,
 * </pre>
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
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.FIELD })
@Documented
@ValidatorInfo(validatorName = "rangelength", validator = RangeLengthValidator.class)
public @interface RangeLength {

    /**
     * 오류 메시지 설정
     * 
     * @return
     */
    String message() default "rangelength";

    /**
     * 최소길이
     * 
     * @return
     */
    int min() default 0;

    /**
     * 최대길이
     * 
     * @return
     */
    int max();
}
