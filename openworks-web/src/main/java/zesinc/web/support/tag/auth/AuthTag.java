/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.auth;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import zesinc.web.auth.AuthSupport;
import zesinc.web.auth.AuthType;

/**
 * <pre>
 * 보유한 권한과 요청된 권한의 등급에 따라서 허용여부를 판단하고,
 * 추가로 인자로 사용자 ID가 전달된 경우에는 사용자 ID를 함께 비교하여
 * 최종으로 태그내의 코드를 화면에 표시하도록 한다.
 * 
 * 물론 권한이 없는 경우에는 태그내 내용을 표시하지 않는다.
 * </pre>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 8.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class AuthTag extends TagSupport {

    /** serialVersionUID */
    private static final long serialVersionUID = 7215730281727208892L;
    /** 필요권한 코드 */
    private AuthType authType;
    /** 사용자일치여부를 확인하기 위한 ID */
    private String picId;

    @Override
    public int doStartTag() throws JspException {

        Boolean isAuth = AuthSupport.isAuth(authType, picId);
        /*
         * 최종적으로 권한이 있다면 태그내의 내용을 출력하고, 권한이 없다면, 출력하지 않는다.
         */
        if(isAuth) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    /**
     * needAuthType을 인자로 AuthType을 생성하여 설정 설정
     * 1001/2001/3001 코드 또는 READ/BASIC/MANAGER 유형명을 사용
     * 
     * @param needAuthType 을(를) String needAuthType로 설정
     * @see AuthType
     */
    public void setNeedAuthType(String needAuthType) {
        this.authType = AuthSupport.getAuthType(needAuthType);
    }

    /**
     * picId을 설정
     * 
     * @param picId 을(를) String picId로 설정
     */
    public void setPicId(String picId) {
        this.picId = picId;
    }

}
