/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.popup;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zesinc.component.popup.domain.JsPopupVO;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.spring.controller.BaseController;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.DomnCacheUtil;
import zesinc.web.utils.JsonUtil;
import zesinc.web.vo.cache.DomnCacheVO;

/**
 * 팝업 표시를 위한 컨트롤러
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 7. 24.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller
@RequestMapping(value = { "/**/component/popup" })
public class JsPopupController extends BaseController {

    /** javascript 변수명 */
    private static final String POPUP_VAR_NM = "var opPopupConfigList = ";
    private static final String POPUP_SCRIPT = Config.getString("popup-config.script");

    @Resource(name = "opJsPopupService")
    private JsPopupService opJsPopupService;

    /**
     * 화면에 팝업을 표시하기 위한 데이터를 반환
     */
    @RequestMapping(value = { "ND_popupConfig.do" })
    public String selectJsPopupList(HttpServletRequest request, HttpServletResponse response, Model model) {

        response.setContentType("text/javascript");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        DomnCacheVO domnVo = DomnCacheUtil.getDomnCache();

        String json = POPUP_SCRIPT;
        if(Validate.isNotEmpty(domnVo)) {
            JsPopupVO jsPopupVo = new JsPopupVO();
            jsPopupVo.setSiteSn(domnVo.getSiteSn());

            List<JsPopupVO> popupList = opJsPopupService.selectPopupList(jsPopupVo);
            if(Validate.isNotEmpty(popupList)) {
                json += ";\n\r";
                json += POPUP_VAR_NM + JsonUtil.toJson(popupList);
            } else {
                json += ";\n\r";
                json += POPUP_VAR_NM + "{}";
            }

        }
        return responseText(model, json);
    }

    /**
     * 팝업내용 표시
     */
    @RequestMapping(value = { "ND_selectOpenPopup.do" })
    public String selectOpenPopup(HttpServletRequest request, Model model, JsPopupVO jsPopupVo) {

        model.addAttribute(BaseConfig.KEY_DATA_VO, opJsPopupService.selectPopup(jsPopupVo));

        return "component/popup/ND_selectOpenPopup";
    }

    /**
     * 레이어내용 표시
     */
    @RequestMapping(value = { "ND_selectOpenLayer.do" })
    public String selectOpenLayer(HttpServletRequest request, Model model, JsPopupVO jsPopupVo) {

        model.addAttribute(BaseConfig.KEY_DATA_VO, opJsPopupService.selectPopup(jsPopupVo));

        return "component/popup/ND_selectOpenLayer";
    }

}
