/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.manage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zesinc.core.cache.CacheService;
import zesinc.intra.cms.cntnts.domain.CmsCntntsCompareVO;
import zesinc.intra.cms.manage.domain.CmsManageVO;
import zesinc.web.auth.AuthType;
import zesinc.web.auth.anotation.OpenworksAuth;
import zesinc.web.spring.controller.IntraController;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.MessageUtil;
import zesinc.web.validate.ValidateResultHolder;
import zesinc.web.validate.ValidateUtil;
import zesinc.web.vo.ISessVO;
import zesinc.web.vo.cache.DomnCacheVO;

/**
 * 사용자메뉴승인/반려 컨트롤러 클레스
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
@Controller("컨텐츠발행승인 관리")
@RequestMapping(value = { "/intra/cms/manage" })
public class CmsManageController extends IntraController {

    @Resource(name = "opCmsManageService")
    private CmsManageService opCmsManageService;

    /**
     * 사용자메뉴발생 승인/반려 소스상세
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "PD_selectCmsManageSource.do")
    public void selectCmsManagSource(HttpServletRequest request, Model model, CmsManageVO cmsManageVo, CmsCntntsCompareVO compareVo) {

        model.addAttribute(BaseConfig.KEY_DATA_LIST, opCmsManageService.compareSourceCntnts(compareVo));
        model.addAttribute(BaseConfig.KEY_BASE_VO, compareVo);
    }

    /**
     * 사용자메뉴발생 승인/반려 화면상세
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "PD_selectCmsManageScreen.do")
    public void selectCmsManageScreen(HttpServletRequest request, Model model, CmsManageVO cmsManageVo, CmsCntntsCompareVO compareVo) {

        model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsManageService.compareScreenCntnts(compareVo));
        model.addAttribute(BaseConfig.KEY_BASE_VO, compareVo);
    }

    /**
     * 사용자메뉴발생 승인/반려 페이지 목록
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "BD_selectCmsManageList.do")
    public void selectCmsManageList(HttpServletRequest request, Model model, CmsManageVO cmsManageVo) {

        @SuppressWarnings("unchecked")
        List<DomnCacheVO> domnList = (List<DomnCacheVO>) CacheService.get(BaseConfig.DOMN_LIST_CACHE_KEY);
        model.addAttribute(BaseConfig.KEY_DOMAIN_LIST, domnList);

        model.addAttribute(BaseConfig.KEY_PAGER, opCmsManageService.selectCmsManagePageList(cmsManageVo));
    }

    /**
     * 사용자메뉴발생 승인/반려 수정
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_updateCmsManage.do", method = RequestMethod.POST)
    public String updateCmsManage(HttpServletRequest request, Model model, CmsManageVO cmsManageVo) {
        Integer updateCnt;
        // 검증
        ValidateResultHolder holder = ValidateUtil.validate(cmsManageVo);
        if(holder.isValid()) {
            ISessVO loginVo = (ISessVO) getMgrSession();
            cmsManageVo.setAutzrId(loginVo.getUsername());

            // 수정 실행
            updateCnt = opCmsManageService.updateCmsManage(cmsManageVo);
            if(updateCnt != 1) {
                return alertAndBack(model, MessageUtil.getMessage("common.processFail"));
            }
        } else {
            return alertAndBack(model, MessageUtil.getMessage("common.validateFail"));
        }

        // 승인경우 컨텐츠 배포
        String aprvSttsNo = cmsManageVo.getAprvSttsNo();
        if(aprvSttsNo.equals("C1040") || aprvSttsNo.equals("C1050")) {
            opCmsManageService.publishCmsCntnts(cmsManageVo);
        }

        String scriptCode = "alert('" + MessageUtil.getMessage("common.processOk")
            + "'); if(opener) { opener.location.reload(); self.close(); } else { self.close(); }";

        return responseScript(model, scriptCode);
    }

}
