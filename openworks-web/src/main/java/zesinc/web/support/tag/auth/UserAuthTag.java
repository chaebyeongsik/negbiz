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

import zesinc.core.utils.StringUtil;
import zesinc.web.auth.UserAuthSupport;

/**
 * <pre>
 * 보유한 회원등급과 확인 요청된 회원등급에 따라서 허용여부를 판단하고,
 * 추가로 인자로 사용자 ID가 전달된 경우에는 사용자 ID를 함께 비교하여
 * 최종으로 태그내의 코드를 화면에 표시하도록 한다.
 * 
 * 물론 권한이 없는 경우에는 태그 내 내용을 표시하지 않는다.
 * </pre>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2016. 1. 7.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class UserAuthTag extends TagSupport {

    /** serialVersionUID */
    private static final long serialVersionUID = -4218830831671693948L;

    /** 필요회원등급 */
    private String userGrads;
    /** 사용자일치여부를 확인하기 위한 ID */
    private String userId;

    @Override
    public int doStartTag() throws JspException {

        Boolean isAuth = UserAuthSupport.isAuth(this.userId, this.userGrads);

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
     * 확인대상 회원등급(쉼표로 복수개의 등급을 지정)
     * 
     * @param userGrads 을(를) String userGrads로 설정
     */
    public void setUserGrads(String userGrads) {
        this.userGrads = StringUtil.deleteWhitespace(userGrads);
    }

    /**
     * 확인대상 사용자ID
     * 
     * @param userId 을(를) String userId로 설정
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
