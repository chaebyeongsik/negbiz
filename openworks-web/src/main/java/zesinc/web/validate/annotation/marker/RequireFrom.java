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
import zesinc.web.validate.validator.impl.RequireFromValidator;

/**
 * 대상 필드가 <code>null</code>이 아닌 경우 fieldValue 값을 비교하여 동일한 경우 필수체크
 * <p />
 * 
 * <pre>
 * 사용 예)
 * &#64;RequireFrom(fieldName="test", fieldValue="Y", fieldDesc="test")
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
@ValidatorInfo(validatorName = "requirefrom", validator = RequireFromValidator.class)
public @interface RequireFrom {

    /**
     * 오류 메시지 설정
     * 
     * @return
     */
    String message() default "requirefrom";

    /**
     * 대상 필드명
     * 
     * @return
     */
    String fieldName();

    /**
     * 대상 필드 값
     * 
     * @return
     */
    String fieldValue();

    /**
     * 대상 필드의 한글 명칭(메시지 포멧 용)
     * 
     * @return
     */
    String fieldDesc();
}
