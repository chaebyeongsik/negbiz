/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.web.support.helper.OpHelper;
import zesinc.web.vo.IUserSessVO;

/**
 * 요청응답 메소드등 컨트롤러 편의 기능 지원
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 10. 17.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class UserController extends BaseController {

    /** Contrroller 로깅 */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 세션객체 얻기
     * 
     * @param request
     * @return
     */
    protected IUserSessVO getUserSession() {

        return (IUserSessVO) OpHelper.getUserSession();
    }
}
