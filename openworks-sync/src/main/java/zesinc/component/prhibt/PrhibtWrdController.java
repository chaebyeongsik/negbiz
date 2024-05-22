/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.prhibt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zesinc.web.spring.controller.BaseController;
import zesinc.web.utils.PrhibtUtil;
import zesinc.web.vo.BaseVO;

/**
 * 금지단어 관리와 연동되어 금지단어 목록 제공등의 기능을 수행한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 29.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller
@RequestMapping(value = "/**/component/prhibt")
public class PrhibtWrdController extends BaseController {

    /**
     * 금지단어 목록을 json 유형으로 제공
     */
    @RequestMapping(value = { "selectPrhibtWrdList.do" })
    public String selectPrhibtWrdList(HttpServletRequest request, Model model, BaseVO paramVo) {
        String[] prhibtCodes = paramVo.getStrings("q_prhibtCode");

        return responseJson(model, PrhibtUtil.getPrhibtList(prhibtCodes));
    }

}
