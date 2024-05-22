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
import zesinc.web.validate.validator.impl.MaxValidator;

/**
 * 숫자 값의 최대 크기를 확인
 * <p/>
 * max=10 인 경우 12 : false, 9 : true
 * 
 * <pre>
 * 사용 예)
 * &#64;Max(max=20)
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
@ValidatorInfo(validatorName = "max", validator = MaxValidator.class)
public @interface Max {

    /**
     * 오류 메시지 설정
     * 
     * @return
     */
    String message() default "max";

    /**
     * 최대크기
     * 
     * @return
     */
    int max() default -1;
}
