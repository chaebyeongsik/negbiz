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
import zesinc.web.validate.validator.impl.DateValidator;

/**
 * 입력된 날짜 포멧 패턴과 일치 하는지 확인
 * 
 * <pre>
 * 사용 예)
 * &#64;Date(pattern="YYYY-MM-DD")
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
@ValidatorInfo(validatorName = "date", validator = DateValidator.class)
public @interface Date {

    /**
     * 오류 메시지 설정
     * 
     * @return
     */
    String message() default "date";

    /**
     * 날짜 포멧 패턴
     * <p />
     * 예) YYYY-MM-DD, YYYYMMDD
     * 
     * @return
     */
    String pattern();
}
