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
import zesinc.web.validate.validator.impl.RangeValidator;

/**
 * 숫자 값의 크기를 확인. 최소 기본값은 0
 * 
 * <pre>
 * 사용 예)
 * &#64;Range(min=3, max=20) 3~20 사이의 숫자 값
 * &#64;Range(max=20) 20 이하의 숫자 값
 * 기본값
 * max() 최대 필수
 * min() 최소 기본값 0
 * </pre>
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
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.FIELD })
@Documented
@ValidatorInfo(validatorName = "range", validator = RangeValidator.class)
public @interface Range {

    /**
     * 오류 메시지 설정
     * 
     * @return
     */
    String message() default "range";

    /**
     * 최소크기
     * 
     * @return
     */
    int min() default 0;

    /**
     * 최대크기
     * 
     * @return
     */
    int max();
}
