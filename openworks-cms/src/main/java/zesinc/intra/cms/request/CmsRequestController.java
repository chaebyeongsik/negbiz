/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.request;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zesinc.core.cache.CacheService;
import zesinc.intra.cms.cntnts.domain.CmsCntntsCompareVO;
import zesinc.intra.cms.request.domain.CmsRequestVO;
import zesinc.web.auth.AuthType;
import zesinc.web.auth.anotation.OpenworksAuth;
import zesinc.web.spring.controller.IntraController;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.MessageUtil;
import zesinc.web.vo.ISessVO;
import zesinc.web.vo.cache.DomnCacheVO;

/**
 * 컨텐츠발행요청 컨트롤러 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-06-10.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller("컨텐츠발행요청 관리")
@RequestMapping(value = { "/intra/cms/request" })
public class CmsRequestController extends IntraController {

    @Resource(name = "opCmsRequestService")
    private CmsRequestService opCmsRequestService;

    /**
     * 사용자메뉴승인요청 소스상세
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "PD_selectCmsRequestSource.do")
    public void selectCmsManagSource(HttpServletRequest request, Model model, CmsRequestVO cmsRequestVo, CmsCntntsCompareVO compareVo) {

        model.addAttribute(BaseConfig.KEY_DATA_LIST, opCmsRequestService.compareSourceCntnts(compareVo));
        model.addAttribute(BaseConfig.KEY_BASE_VO, compareVo);
    }

    /**
     * 사용자메뉴승인요청 화면상세
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "PD_selectCmsRequestScreen.do")
    public void selectCmsRequestScreen(HttpServletRequest request, Model model, CmsRequestVO cmsRequestVo, CmsCntntsCompareVO compareVo) {

        model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsRequestService.compareScreenCntnts(compareVo));
        model.addAttribute(BaseConfig.KEY_BASE_VO, compareVo);
    }

    /**
     * 사용자메뉴승인요청 페이지 목록
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "BD_selectCmsRequestList.do")
    public void selectCmsRequestList(HttpServletRequest request, Model model, CmsRequestVO cmsRequestVo) {

        @SuppressWarnings("unchecked")
        List<DomnCacheVO> domnList = (List<DomnCacheVO>) CacheService.get(BaseConfig.DOMN_LIST_CACHE_KEY);
        model.addAttribute(BaseConfig.KEY_DOMAIN_LIST, domnList);

        ISessVO loginVo = (ISessVO) getMgrSession();
        cmsRequestVo.setMdfrId(loginVo.getUsername());

        model.addAttribute(BaseConfig.KEY_PAGER, opCmsRequestService.selectCmsRequestPageList(cmsRequestVo));
    }

    /**
     * 사용자메뉴승인요청 회수 수정
     */
    @OpenworksAuth(authType = AuthType.BASIC)
    @RequestMapping(value = "ND_updateCmsRequest.do", method = RequestMethod.POST)
    public String updateCmsRequest(HttpServletRequest request, Model model, CmsRequestVO cmsRequestVo) {

        ISessVO loginVo = (ISessVO) getMgrSession();
        cmsRequestVo.setAutzrId(loginVo.getUsername());

        // 수정 실행
        Integer updateCnt = opCmsRequestService.updateCmsRequest(cmsRequestVo);
        if(updateCnt != 1) {
            return alertAndBack(model, MessageUtil.getMessage("common.processFail"));
        }

        String scriptCode = "alert('" + MessageUtil.getMessage("common.processOk")
            + "'); if(opener) { opener.location.reload(); self.close(); } else { self.close(); }";

        return responseScript(model, scriptCode);
    }

}
