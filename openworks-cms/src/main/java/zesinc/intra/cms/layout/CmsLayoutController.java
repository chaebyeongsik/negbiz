/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.layout;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zesinc.core.cache.CacheService;
import zesinc.core.lang.Validate;
import zesinc.intra.cms.layout.domain.CmsLayoutVO;
import zesinc.web.auth.AuthType;
import zesinc.web.auth.anotation.OpenworksAuth;
import zesinc.web.spring.controller.IntraController;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.utils.MessageUtil;
import zesinc.web.utils.XssUtil;
import zesinc.web.utils.XssUtil.TYPE;
import zesinc.web.validate.ValidateResultHolder;
import zesinc.web.validate.ValidateUtil;
import zesinc.web.vo.ISessVO;
import zesinc.web.vo.cache.DomnCacheVO;

/**
 * 사용자메뉴레이아웃 정보 컨트롤러 클레스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-05-14.    방기배   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller("사용자메뉴레이아웃관리")
@RequestMapping(value = { "/intra/cms/layout" })
public class CmsLayoutController extends IntraController {

    @Resource(name = "opCmsLayoutService")
    private CmsLayoutService opCmsLayoutService;

    /**
     * ajax 방식의 코드 중복 체크
     */
    @OpenworksAuth(authType = AuthType.BASIC)
    @RequestMapping(value = "ND_selectDplctChckCode.do")
    public String selectDplctChckCode(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {

        Integer cnt = opCmsLayoutService.selectDplctChckCode(cmsLayoutVo);
        ValidateResultHolder holder = ValidateUtil.doFiledValidate(cmsLayoutVo, "lytCdNo");

        if(cnt == 0 && holder.isValid()) {
            return responseJson(model, Boolean.TRUE, MessageUtil.getMessage("common.dplctChckTrue"));
        }
        if(!holder.isValid()) {
            return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.validateFail"));
        }

        return responseJson(model, Boolean.FALSE, cnt, MessageUtil.getMessage("common.dplctChckFalse"));
    }

    /**
     * 사용자메뉴레이아웃 상세
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "BD_selectCmsLayout.do")
    public void selectCmsLayout(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {

        model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsLayoutService.selectCmsLayout(cmsLayoutVo));
    }

    /**
     * 사용자메뉴레이아웃 페이지 목록
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "BD_selectCmsLayoutList.do")
    public void selectCmsLayoutList(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {
        String siteSnParam = cmsLayoutVo.getString("q_siteSnParam");
        if(Validate.isNotEmpty(siteSnParam)) {
            cmsLayoutVo.addParam("q_siteSn", siteSnParam);
        } else {
            cmsLayoutVo.addParam("q_siteSn", "");
        }
/* MY-SQL 버전일 경우 추가해야 할 부분
        Integer q_currPage = cmsLayoutVo.getInteger("q_currPage");
        Integer q_rowPerPage = cmsLayoutVo.getInteger("q_rowPerPage");
        Integer pagingStartNum = (Validate.isNotNull(q_currPage) ? q_currPage - 1 : 0) * q_rowPerPage;
        cmsLayoutVo.addParam("q_rowPerPage", q_rowPerPage);
        cmsLayoutVo.addParam("q_currPage", q_currPage);
        cmsLayoutVo.addParam("q_pagingStartNum", pagingStartNum);
*/
        model.addAttribute(BaseConfig.KEY_PAGER, opCmsLayoutService.selectCmsLayoutPageList(cmsLayoutVo));
        @SuppressWarnings("unchecked")
        List<DomnCacheVO> domnList = (List<DomnCacheVO>) CacheService.get(BaseConfig.DOMN_LIST_CACHE_KEY);
        model.addAttribute(BaseConfig.KEY_DOMAIN_LIST, domnList);
    }

    /**
     * 사용자메뉴레이아웃 입력 폼
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = { "BD_insertCmsLayoutForm.do" })
    public void insertCmsLayoutForm(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {

        ValidateResultHolder holder = ValidateUtil.validate(cmsLayoutVo);
        model.addAttribute(BaseConfig.KEY_VALIDATE, holder);

        @SuppressWarnings("unchecked")
        List<DomnCacheVO> domnList = (List<DomnCacheVO>) CacheService.get(BaseConfig.DOMN_LIST_CACHE_KEY);
        model.addAttribute(BaseConfig.KEY_DOMAIN_LIST, domnList);
    }

    /**
     * 사용자메뉴레이아웃 등록
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_insertCmsLayout.do", method = RequestMethod.POST)
    public String insertCmsLayout(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {

        // 검증
        ValidateResultHolder holder = ValidateUtil.validate(cmsLayoutVo);
        if(holder.isValid()) {
            ISessVO loginVo = (ISessVO) getMgrSession();
            cmsLayoutVo.setRgtrId(loginVo.getUsername());
            cmsLayoutVo.setRgtrNm(loginVo.getFullname());
            cmsLayoutVo.setIpAddr(getIpAddr());

            // 컨텐츠내용이 아닌 컬럼들은 SCRIPT방지
            cmsLayoutVo.setLytCdNo(XssUtil.cleanTag(cmsLayoutVo.getLytCdNo(), TYPE.SCRIPT));
            cmsLayoutVo.setLytNm(XssUtil.cleanTag(cmsLayoutVo.getLytNm(), TYPE.SCRIPT));

            Integer key = opCmsLayoutService.insertCmsLayout(cmsLayoutVo);
            if(key != 1) {
                return alertAndBack(model, MessageUtil.getMessage("common.processFail"));
            }
        } else {
            return alertAndBack(model, MessageUtil.getMessage("common.validateFail"));
        }
        // 레이아웃 출판 배포
        opCmsLayoutService.publishCmsLayout(cmsLayoutVo);

        String url = "BD_selectCmsLayoutList.do?" + OpHelper.getSearchQueryString(request);
        return alertAndRedirect(model, MessageUtil.getMessage("common.insertOk"), url);
    }

    /**
     * 사용자메뉴레이아웃 수정 폼
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = { "BD_updateCmsLayoutForm.do" })
    public void updateCmsLayoutForm(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {

        ValidateResultHolder holder = ValidateUtil.validate(cmsLayoutVo);
        model.addAttribute(BaseConfig.KEY_VALIDATE, holder);

        model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsLayoutService.selectCmsLayout(cmsLayoutVo));
    }

    /**
     * 사용자메뉴레이아웃 수정
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_updateCmsLayout.do", method = RequestMethod.POST)
    public String updateCmsLayout(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {
        Integer key = 0;
        // 검증
        ValidateResultHolder holder = ValidateUtil.validate(cmsLayoutVo);
        if(holder.isValid()) {
            ISessVO loginVo = (ISessVO) getMgrSession();
            cmsLayoutVo.setMdfrId(loginVo.getUsername());
            cmsLayoutVo.setUpdusrNm(loginVo.getFullname());
            cmsLayoutVo.setIpAddr(getIpAddr());

            // 컨텐츠내용이 아닌 컬럼들은 SCRIPT방지
            cmsLayoutVo.setLytCdNo(XssUtil.cleanTag(cmsLayoutVo.getLytCdNo(), TYPE.SCRIPT));
            cmsLayoutVo.setLytNm(XssUtil.cleanTag(cmsLayoutVo.getLytNm(), TYPE.SCRIPT));

            // 수정 실행
            key = opCmsLayoutService.updateCmsLayout(cmsLayoutVo);
            if(key != 1) {
                return responseJson(model, Boolean.FALSE, key, MessageUtil.getMessage("common.processFail"));
            }
        } else {
            return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.validateFail"));
        }
        // 레이아웃 출판 배포
        opCmsLayoutService.publishCmsLayout(cmsLayoutVo);

        return responseJson(model, Boolean.TRUE, key, MessageUtil.getMessage("common.updateOk"));
    }

    /**
     * 사용자메뉴레이아웃 삭제
     *
     * @param request
     * @param model
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_deleteCmsLayout.do", method = RequestMethod.POST)
    public String deleteCmsLayout(HttpServletRequest request, Model model, CmsLayoutVO cmsLayoutVo) {

        ISessVO loginVo = (ISessVO) getMgrSession();
        cmsLayoutVo.setMdfrId(loginVo.getUsername());
        cmsLayoutVo.setUpdusrNm(loginVo.getFullname());

        Integer cnt = opCmsLayoutService.deleteCmsLayout(cmsLayoutVo);
        if(cnt != 1) {
            return alertAndBack(model, MessageUtil.getMessage("common.processFail"));
        }

        String url = "BD_selectCmsLayoutList.do?" + OpHelper.getSearchQueryString(request);
        return alertAndRedirect(model, MessageUtil.getMessage("common.deleteOk"), url);
    }

}
