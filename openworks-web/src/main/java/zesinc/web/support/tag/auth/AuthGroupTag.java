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

/**
 * <pre>
 * 소유한 권한 그룹 코드에 해당하는 권한 확인 지원 태그로
 * 해당 권한 그룹 코드를 소유한 사용자에겐 내용을 표시하고,
 * 권한 그룹 코드가 없는 경우에는 태그내 내용을 표시하지 않는다.
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
public class AuthGroupTag extends TagSupport {

    /** serialVersionUID */
    private static final long serialVersionUID = -2602849226720355131L;
    /** 필요권한 그룹 코드 */
    private String groupCode;
    /** 사용자일치여부를 확인하기 위한 ID */
    private String picId;

    @Override
    public int doStartTag() throws JspException {
        Boolean isAuth = AuthSupport.isAuthGroup(groupCode, picId);
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
     * 보유를 확인할 권한 그룹 코드를 설정
     * 권한관리 기능에서 설정된 그룹코드
     * 
     * @param groupCode 을(를) String groupCode로 설정
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
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
