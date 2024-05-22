/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.auth.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import zesinc.web.auth.AuthType;

/**
 * <pre>
 * AuthType과 파라미터를 설정하여 메소드별 권한정보를 설정한다.
 * 이미 만들어져 있는 Controller들의 소스에서 확인하기 바란다.
 * 
 * 사용예 : @OpenworksAuth(authType = AuthType.BASIC)
 * </pre>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 2. 29.    방기배   신규 추가
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see AuthType
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface OpenworksAuth {

    /**
     * 대상 메소드에 적용되는 권한 유형
     * 
     * @return READ, BASIC, MANAGER for AuthTypeEnum
     * @see AuthType
     */
    AuthType authType();
}
