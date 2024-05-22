/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.auth;

import java.util.Arrays;
import java.util.List;

import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.vo.IUserSessVO;

/**
 * 회원등급과 회원ID로 권한 여부를 판단하는 유티 클레스
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
public class UserAuthSupport {

    /**
     * 회원ID가 로그인한 회원과 일치하는지 여부를 판단한다.
     * 
     * @param userId
     * @return
     */
    public static Boolean isAuth(final String userId) {

        return isAuth(userId, "");
    }

    /**
     * 회원등급 또는 회원ID가 로그인 한 회원과 일치하는지 여부를 판단한다.
     * 
     * @param userId
     * @return
     */
    public static Boolean isAuth(final String userId, final String userGrads) {
        String[] arrUserGrad = null;
        if(Validate.isNotEmpty(userGrads)) {
            arrUserGrad = StringUtil.deleteWhitespace(userGrads).split(",");
        }

        return isAuth(userId, arrUserGrad);
    }

    /**
     * 회원ID가 로그인한 회원과 일치하는지 여부를 판단한다.
     * 
     * @param userId
     * @return
     */
    public static Boolean isAuthGrad(final String userGrads) {
        String[] arrUserGrad = null;
        if(Validate.isNotEmpty(userGrads)) {
            arrUserGrad = StringUtil.deleteWhitespace(userGrads).split(",");
        }

        return isAuth(null, arrUserGrad);
    }

    /**
     * 회원등급 또는 회원ID가 로그인 한 회원과 일치하는지 여부를 판단한다.
     * 
     * @param userId
     * @param userGrads
     * @return
     */
    public static Boolean isAuth(final String userId, final String[] userGrads) {
        Boolean isAuth = Boolean.FALSE;

        // 사용자 등급에 따른 권한
        IUserSessVO loginVo = (IUserSessVO) OpHelper.getUserSession();
        if(Validate.isNotEmpty(loginVo)) {
            // ID 확인
            if(Validate.isNotEmpty(userId) && userId.equals(loginVo.getUserId())) {
                isAuth = Boolean.TRUE;
            }

            String loginGrads = loginVo.getUserGrdCdDsctn();
            // 회원등급 확인
            if(Validate.isNotEmpty(userGrads) && Validate.isNotEmpty(loginGrads)) {

                String[] arrLoginGrad = StringUtil.deleteWhitespace(loginGrads).split(",");
                List<String> loginGradList = Arrays.asList(arrLoginGrad);

                for(String userGrad : userGrads) {
                    if(loginGradList.contains(userGrad)) {
                        isAuth = Boolean.TRUE;
                        break;
                    }
                }
            }
        }
        return isAuth;
    }

}
