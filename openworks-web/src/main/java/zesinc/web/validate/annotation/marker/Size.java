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
import zesinc.web.validate.validator.impl.SizeValidator;

/**
 * Collection 또는 배열 값의 길이(length 또는 size)를 확인
 * <p />
 * 
 * <pre>
 * 사용 예)
 * &#64;Size(min=3, max=20) 3~20 사이 크기
 * &#64;Size(min=3) 3 이상의 크기
 * &#64;Size(max=20) 20 이하의 크기
 * 
 * 기본값
 * min() 최소길이 기본값 0
 * max() 최대길이 필수
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
@ValidatorInfo(validatorName = "size", validator = SizeValidator.class)
public @interface Size {

    /**
     * 오류 메시지 설정
     * 
     * @return
     */
    String message() default "size";

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
