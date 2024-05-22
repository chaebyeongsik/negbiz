/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.auth;

/**
 * MEMBER("1001") : 로그인한 사용자
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 7. 20.    ZES-INC   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public enum AuthWebType {
    MEMBER("1001");

    AuthWebType(String cdId) {
        this.cdId = cdId;
    }

    /**
     * 문자열 권한코드 값을 반환
     * 
     * @return
     */
    public String getCdId() {
        return cdId;
    }

    /**
     * 해당 코드에 대한 한글명칭
     * 
     * @return
     */
    public String getName() {
        if(this.equals(MEMBER)) {
            return "사용자";
        }
        return "";
    }

    private String cdId;

}
