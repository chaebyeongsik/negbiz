/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.core.privacy;

/**
 * <pre>
 * 개인정보 필터링 인터페이스
 * 
 * << 개정이력(Modification Information) >>
 * 
 *     수정일               수정자                  수정내용
 * --------------  --------  -------------------------------
 *  
 * 
 *  2013. 06. 04.       (주)제스아이앤씨         신규등록
 * </pre>
 */
public interface PrivacyFilter {

    /**
     * 개인정보가 존재하는지 필터링 한다.
     * 실제 문자열 또는 파일명 등 개인정보 필터링 대상을 설정한다.
     * 
     * @return 결과정보를 담고 있는 객체를 반환한다.
     * @see PrivacyResultVO
     */
    public PrivacyResultVO doFilter();

}
