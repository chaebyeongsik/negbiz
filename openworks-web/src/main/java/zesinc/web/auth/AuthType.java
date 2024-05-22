/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.auth;

/**
 * <pre>
 * 권한 설정 목록 enum 타입이므로 실제 사용되는 값은 괄호 안의 숫자가 사용된다.
 * 예를 들면 HTML 소스나 DB에 저장되어 있는 값은 1001, 2001, 3001 이 된다.
 * 
 * READ("1001") : 조회
 * BASIC("2001") : 기본(mine 조회+등록+수정+삭제)
 * MANAGER("3001") : 총괄(mine + other 조회+등록+수정+삭제)
 * </pre>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 2. 29.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public enum AuthType {
    // 순서대로 조회, 본인컨텐츠, 관리자. 클레스 설명 참조
    READ("1001"), BASIC("2001"), MANAGER("3001");

    AuthType(String cdId) {
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
        if(this.equals(MANAGER)) {
            return "총괄권한";
        } else if(this.equals(BASIC)) {
            return "기본권한";
        }
        return "조회권한";
    }

    private String cdId;
}
