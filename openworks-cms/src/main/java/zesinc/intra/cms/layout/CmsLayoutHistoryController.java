/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.layout;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zesinc.intra.cms.layout.domain.CmsLayoutVO;
import zesinc.web.auth.AuthType;
import zesinc.web.auth.anotation.OpenworksAuth;
import zesinc.web.spring.controller.IntraController;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.utils.MessageUtil;
import zesinc.web.validate.ValidateResultHolder;
import zesinc.web.validate.ValidateUtil;

/**
 * 사용자메뉴레이아웃이력 정보 컨트롤러 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-07-03.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller("사용자메뉴레이아웃이력 관리")
@RequestMapping(value = { "/intra/cms/layout" })
public class CmsLayoutHistoryController extends IntraController {

    @Resource(name = "opCmsLayoutHistoryService")
    private CmsLayoutHistoryService opCmsLayoutHistoryService;

    /**
     * 사용자메뉴레이아웃이력 상세
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "PD_selectCmsLayoutHistory.do")
    public void selectCmsLayoutHistory(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {

        ValidateResultHolder holder = ValidateUtil.validate(cmsLayoutVo);
        model.addAttribute(BaseConfig.KEY_VALIDATE, holder);

        model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsLayoutHistoryService.selectCmsLayoutHistory(cmsLayoutVo));
    }

    /**
     * 사용자메뉴레이아웃이력 페이지 목록
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "PD_selectCmsLayoutHistoryList.do")
    public void selectCmsLayoutHistoryList(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {

        model.addAttribute(BaseConfig.KEY_PAGER, opCmsLayoutHistoryService.selectCmsLayoutHistoryPageList(cmsLayoutVo));
    }

    /**
     * 사용자메뉴레이아웃이력 복원처리
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_processCmsLayoutHistory.do", method = RequestMethod.POST)
    public String processCmsLayoutHistory(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {

        // 검증
        ValidateResultHolder holder = ValidateUtil.validate(cmsLayoutVo);
        if(holder.isValid()) {

            Integer key = opCmsLayoutHistoryService.insertCmsLayoutHistory(cmsLayoutVo);
            if(key != 1) {
                return alertAndBack(model, MessageUtil.getMessage("common.processFail"));
            }
        } else {
            return alertAndBack(model, MessageUtil.getMessage("common.validateFail"));
        }

        String url = "PD_selectCmsLayoutHistoryList.do?" + OpHelper.getSearchQueryString(request);
        return alertAndRedirect(model, MessageUtil.getMessage("common.insertOk"), url);
    }

    /**
     * 사용자메뉴레이아웃이력 삭제
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_deleteCmsLayoutHistory.do", method = RequestMethod.POST)
    public String deleteCmsLayoutHistory(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {

        Integer cnt = opCmsLayoutHistoryService.deleteCmsLayoutHistory(cmsLayoutVo);
        if(cnt != 1) {
            return alertAndBack(model, MessageUtil.getMessage("common.processFail"));
        }

        String url = "BD_selectCmsLayoutHistoryList.do?" + OpHelper.getSearchQueryString(request);
        return alertAndRedirect(model, MessageUtil.getMessage("common.deleteOk"), url);
    }

}
