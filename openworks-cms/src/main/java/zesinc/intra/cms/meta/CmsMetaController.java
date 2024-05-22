/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.meta;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zesinc.intra.cms.meta.domain.CmsMetaVO;
import zesinc.web.auth.AuthType;
import zesinc.web.auth.anotation.OpenworksAuth;
import zesinc.web.spring.controller.IntraController;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.MessageUtil;
import zesinc.web.validate.ValidateResultHolder;
import zesinc.web.validate.ValidateUtil;
import zesinc.web.vo.ISessVO;

/**
 * 사용자메뉴메타 정보 컨트롤러 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-06-01.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller("사용자메뉴메타 관리")
@RequestMapping(value = { "/intra/cms/meta" })
public class CmsMetaController extends IntraController {

    @Resource(name = "opCmsMetaService")
    private CmsMetaService opCmsMetaService;

    /**
     * 사용자메뉴메타 수정 폼
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = { "INC_updateCmsMetaForm.do" })
    public void updateCmsMetaForm(HttpServletRequest request, Model model, CmsMetaVO cmsMetaVo) {

        ValidateResultHolder holder = ValidateUtil.validate(cmsMetaVo);
        model.addAttribute(BaseConfig.KEY_VALIDATE, holder);

        model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsMetaService.selectCmsMeta(cmsMetaVo));
    }

    /**
     * 사용자메뉴메타 수정
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_updateCmsMeta.do", method = RequestMethod.POST)
    public String updateCmsMeta(HttpServletRequest request, Model model, CmsMetaVO cmsMetaVo) {
        Integer updateCnt;
        // 검증
        ValidateResultHolder holder = ValidateUtil.validate(cmsMetaVo);
        if(holder.isValid()) {
            ISessVO loginVo = (ISessVO) getMgrSession();
            cmsMetaVo.setRgtrId(loginVo.getUsername());
            cmsMetaVo.setRgtrNm(loginVo.getFullname());
            cmsMetaVo.setMdfrId(loginVo.getUsername());
            cmsMetaVo.setUpdusrNm(loginVo.getFullname());
            cmsMetaVo.setIpAddr(getIpAddr());

            // 수정 실행
            updateCnt = opCmsMetaService.updateCmsMeta(cmsMetaVo);
            if(updateCnt != 1) {
                return responseJson(model, Boolean.FALSE, updateCnt, MessageUtil.getMessage("common.processFail"));
            }

            // 메타 정보가 수정되면 수정된 메타정보를 추가하여 재 배포한다.
            opCmsMetaService.publishCmsCntnts(cmsMetaVo);

        } else {
            return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.validateFail"));
        }

        return responseJson(model, Boolean.TRUE, updateCnt, MessageUtil.getMessage("common.updateOk"));
    }

}
