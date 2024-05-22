/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.auth;

/**
 * 사용자 비로그인 에러 Exception
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2013. 5. 22.    Administrator   신규 생성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class UserNoLoginException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -4302854429272898817L;
    private String customMsg;

    public UserNoLoginException(String customMsg) {
        this.customMsg = customMsg;
    }

    @Override
    public String getMessage() {
        return customMsg;
    }

    public void setCustomMsg(String customMsg) {
        this.customMsg = customMsg;
    }
}
