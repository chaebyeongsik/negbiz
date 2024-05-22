/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.passwd;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zesinc.component.passwd.domain.PasswdVO;
import zesinc.web.auth.AuthType;
import zesinc.web.auth.anotation.OpenworksAuth;
import zesinc.web.spring.controller.BaseController;
import zesinc.web.utils.PasswdUtil;

/**
 * 비밀번호 관련 컨트롤러
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 5. 6.    ZES-INC   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller
@RequestMapping(value = { "/**/component/passwd" })
public class PasswdController extends BaseController {

    /**
     * 비밀번호 유효성 체크
     * 
     * @param request
     * @param model
     * @param deptVo
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "ND_allowPasswd.do")
    public String allowPasswd(HttpServletRequest request, Model model, PasswdVO passwdVo) {

        return responseJson(model, PasswdUtil.isAllowPasswd(passwdVo.getPswd()));
    }
}
