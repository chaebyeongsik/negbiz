/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.adres;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zesinc.component.file.domain.FileVO;
import zesinc.core.config.Config;
import zesinc.web.spring.controller.BaseController;

/**
 * 도로명주소찾기팝업 관리 Controller
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 10. 20.    ZES-INC   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller
@RequestMapping(value = "/**/component/adres")
public class AdresController extends BaseController {

    /**
     * 도로명주소찾기팝업
     */
    @RequestMapping(value = "PD_selectAdres.do")
    public String selectAdres(HttpServletRequest request, Model model, FileVO fileVo) {

        String confmKey = Config.getString("adres-config.confmKeys." + request.getServerName().replace(".", "-"));
        model.addAttribute("confmKey", confmKey);

        return "component/adres/PD_selectAdres";
    }
}
