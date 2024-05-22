/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.cntnts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zesinc.core.lang.Validate;
import zesinc.intra.cms.base.CmsService;
import zesinc.intra.cms.base.domain.CmsVO;
import zesinc.intra.cms.cntnts.domain.CmsCntntsCompareVO;
import zesinc.intra.cms.cntnts.domain.CmsCntntsVO;
import zesinc.web.spring.controller.IntraController;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.utils.DomnCacheUtil;
import zesinc.web.utils.MessageUtil;
import zesinc.web.validate.ValidateResultHolder;
import zesinc.web.validate.ValidateUtil;
import zesinc.web.vo.ISessVO;
import zesinc.web.vo.cache.DomnCacheVO;

/**
 * 사용자메뉴컨텐츠 정보 컨트롤러 클레스
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
@Controller("사용자메뉴컨텐츠 관리")
@RequestMapping(value = { "/intra/cms/cntnts" })
public class CmsCntntsController extends IntraController {

    /** 미리보기 URI */
    private static String USER_PREVIEW_URI = "/user/cms/preview/PV_LayoutContentPreView.do";

    @Resource(name = "opCmsCntntsService")
    private CmsCntntsService opCmsCntntsService;

    @Resource(name = "opCmsService")
    private CmsService opCmsService;

    /**
     * 사용자메뉴컨텐츠 상세
     */
    //@OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "BD_selectCmsCntnts.do")
    public void selectCmsCntnts(HttpServletRequest request, Model model, CmsCntntsVO cmsCntntsVo) {

        model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsCntntsService.selectCmsCntnts(cmsCntntsVo));
    }

    /**
     * 사용자메뉴컨텐츠 페이지 목록
     */
    //@OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "INC_selectCmsCntntsList.do")
    public void selectCmsCntntsList(HttpServletRequest request, Model model, CmsCntntsVO cmsCntntsVo) {

        model.addAttribute(BaseConfig.KEY_PAGER, opCmsCntntsService.selectCmsCntntsPageList(cmsCntntsVo));
    }

    /**
     * 사용자메뉴컨텐츠 등록
     */
    //@OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_insertCmsCntnts.do", method = RequestMethod.POST)
    public String insertCmsCntnts(HttpServletRequest request, Model model, CmsCntntsVO cmsCntntsVo) {

        Integer key;

        // 검증
        ValidateResultHolder holder = ValidateUtil.validate(cmsCntntsVo);
        if(holder.isValid()) {
            ISessVO loginVo = (ISessVO) getMgrSession();
            cmsCntntsVo.setRgtrId(loginVo.getUsername());
            cmsCntntsVo.setRgtrNm(loginVo.getFullname());
            cmsCntntsVo.setIpAddr(getIpAddr());

            Integer insertCnt = opCmsCntntsService.insertCmsCntnts(cmsCntntsVo);
            if(insertCnt != 1) {
                return alertAndBack(model, MessageUtil.getMessage("common.processFail"));
            }
            key = cmsCntntsVo.getContsSn();
        } else {
            return alertAndBack(model, MessageUtil.getMessage("common.validateFail"));
        }

        String queryString = OpHelper.getSearchQueryString(request);
        queryString = OpHelper.getReplaceParamValue(queryString, "q_contsSn", key);

        String url = "PD_updateCmsCntntsForm.do?" + queryString;
        return alertAndRedirect(model, MessageUtil.getMessage("common.insertOk"), url);
    }

    /**
     * 사용자메뉴컨텐츠 수정 폼
     */
    //@OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = { "PD_updateCmsCntntsForm.do" })
    public void updateCmsCntntsForm(HttpServletRequest request, Model model, CmsCntntsVO cmsCntntsVo) {

        ValidateResultHolder holder = ValidateUtil.validate(cmsCntntsVo);
        model.addAttribute(BaseConfig.KEY_VALIDATE, holder);

        // 사용자메뉴 기본정보
        CmsVO cmsVo = new CmsVO();
        cmsVo.setParamMap(cmsCntntsVo.getParamMap());
        model.addAttribute(BaseConfig.KEY_BASE_VO, opCmsService.selectCms(cmsVo));

        // 컨텐츠 일련번호가 있는 경우 해당 컨텐츠를 반환
        if(Validate.isNotEmpty(cmsCntntsVo.getParam("q_contsSn"))) {
            model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsCntntsService.selectCmsCntnts(cmsCntntsVo));
        }
    }

    /**
     * 사용자메뉴컨텐츠 수정
     */
    //@OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_updateCmsCntnts.do", method = RequestMethod.POST)
    public String updateCmsCntnts(HttpServletRequest request, Model model, CmsCntntsVO cmsCntntsVo) {
        Integer updateCnt;
        // 검증
        ValidateResultHolder holder = ValidateUtil.validate(cmsCntntsVo);
        if(holder.isValid()) {
            ISessVO loginVo = (ISessVO) getMgrSession();
            cmsCntntsVo.setMdfrId(loginVo.getUsername());
            cmsCntntsVo.setUpdusrNm(loginVo.getFullname());
            cmsCntntsVo.setIpAddr(getIpAddr());

            // 수정 실행
            updateCnt = opCmsCntntsService.updateCmsCntnts(cmsCntntsVo);
            if(updateCnt != 1) {
                return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.processFail"));
            }
        } else {
            return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.validateFail"));
        }
        // 자동 승인경우 컨텐츠 배포
        String aprvSttsNo = cmsCntntsVo.getAprvSttsNo();
        if(aprvSttsNo.equals("C1050")) {
            boolean isPublished = opCmsCntntsService.publishCmsCntnts(cmsCntntsVo);
            if(isPublished) {
                return responseJson(model, Boolean.TRUE, MessageUtil.getMessage("common.processOk"));
            }
            return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.processFail"));
        }

        return responseJson(model, Boolean.TRUE, MessageUtil.getMessage("common.updateOk"));
    }

    /**
     * 사용자메뉴컨텐츠 화면비교
     *
     * @param request
     * @param model
     */
    //@OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "PD_compareScreenCntnts.do")
    public void compareScreenCntnts(HttpServletRequest request, Model model, CmsCntntsCompareVO compareVo) {

        model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsCntntsService.compareScreenCntnts(compareVo));
        model.addAttribute(BaseConfig.KEY_BASE_VO, compareVo);
    }

    /**
     * 사용자메뉴컨텐츠 소스비교
     *
     * @param request
     * @param model
     */
    //@OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "PD_compareSourceCntnts.do")
    public void compareSourceCntnts(HttpServletRequest request, Model model, CmsCntntsCompareVO compareVo) {

        model.addAttribute(BaseConfig.KEY_DATA_LIST, opCmsCntntsService.compareSourceCntnts(compareVo));
        model.addAttribute(BaseConfig.KEY_BASE_VO, compareVo);
    }

    /**
     * 사용자메뉴컨텐츠 삭제
     *
     * @param request
     * @param model
     */
    //@OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_deleteCmsCntnts.do", method = RequestMethod.POST)
    public String deleteCmsCntnts(HttpServletRequest request, Model model, CmsCntntsVO cmsCntntsVo) {

        Integer cnt = opCmsCntntsService.deleteCmsCntnts(cmsCntntsVo);
        if(cnt != 1) {
            return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.processFail"));
        }

        return responseJson(model, Boolean.TRUE, MessageUtil.getMessage("common.deleteOk"));
    }

    /**
     * 사용자단 레이아웃/컨텐츠 미리보기
     */
    //@OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "PV_LayoutContentPreView.do")
    public String previewLayoutCntnts(HttpServletRequest request, Model model, CmsCntntsVO cmsCntntsVo) {

        // Redirect 시킬 URL 생성
        Integer siteSn = cmsCntntsVo.getInteger("q_siteSn");
        DomnCacheVO domnVo = DomnCacheUtil.getDomainCache(siteSn);

        // protocol
        String protocol = "http://";
        // 도메인명
        String siteNm = domnVo.getSiteNm();
        // Port 번호
        int port = domnVo.getPortSn();
        // https 사용 여부
        String httpsYn = domnVo.getHttpsYn();
        // https 사용여부에 따른 수정
        if(httpsYn.equals("Y") && Validate.isNotEmpty(domnVo.getScrtyPortSn())) {
            protocol = "https://";
            port = domnVo.getScrtyPortSn();
        }

        String uri = "";
        // 실제 컨텍스트인 경우만 컨텍스트 설정을 추가해줌
        if(domnVo.getActlStngYn().equals("Y")) {
            uri = domnVo.getSitePathNm();
        }
        if(uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }
        uri += USER_PREVIEW_URI;

        StringBuilder sb = new StringBuilder();
        sb.append(protocol).append(siteNm).append(":").append(port).append(uri);

        List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
        Map<String, Object> paramMap;

        // 도메인 코드
        paramMap = new HashMap<String, Object>();
        paramMap.put("name", "q_siteSn");
        paramMap.put("value", siteSn);
        paramList.add(paramMap);
        // 사용자메뉴코드
        paramMap = new HashMap<String, Object>();
        paramMap.put("name", "q_userMenuEngNm");
        paramMap.put("value", cmsCntntsVo.getString("q_userMenuEngNm"));
        paramList.add(paramMap);
        // 레이아웃코드
        paramMap = new HashMap<String, Object>();
        paramMap.put("name", "q_lytCdNo");
        paramMap.put("value", cmsCntntsVo.getString("q_lytCdNo"));
        paramList.add(paramMap);
        // 컨텐츠일련번호
        paramMap = new HashMap<String, Object>();
        paramMap.put("name", "q_contsSn");
        paramMap.put("value", cmsCntntsVo.getString("q_contsSn"));
        paramList.add(paramMap);

        return postMethodRedirect(model, sb.toString(), paramList);
    }

}
