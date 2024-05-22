/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.base;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zesinc.component.file.domain.FileVO;
import zesinc.core.cache.CacheService;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.intra.cms.base.domain.CmsOrgVO;
import zesinc.intra.cms.base.domain.CmsVO;
import zesinc.intra.cms.layout.CmsLayoutService;
import zesinc.intra.cms.layout.domain.CmsLayoutVO;
import zesinc.intra.cms.support.CmsSupport;
import zesinc.intra.cms.support.CmsUploadHelper;
import zesinc.intra.cms.support.CmsUtil;
import zesinc.intra.cms.support.domain.CmsReferenceVO;
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
 * 사용자메뉴 정보 컨트롤러 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-05-18.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller("사용자메뉴 관리")
@RequestMapping(value = { "/intra/cms/base" })
public class CmsController extends IntraController {

    @Resource(name = "opCmsService")
    private CmsService opCmsService;

    @Resource(name = "opCmsLayoutService")
    private CmsLayoutService opCmsLayoutService;

    /**
     * 부서목록 조회(Auto Complete 용)
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "ND_selectDeptList.do")
    public String selectDeptList(HttpServletRequest request, Model model, CmsOrgVO cmsOrgVo) {

        List<CmsOrgVO> orgList = opCmsService.selectDeptList(cmsOrgVo);

        return responseJson(model, orgList);
    }

    /**
     * 담당자목록 조회(Auto Complete 용)
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "ND_selectMngrList.do")
    public String selectMngrList(HttpServletRequest request, Model model, CmsOrgVO cmsOrgVo) {

        List<CmsOrgVO> orgList = opCmsService.selectMngrList(cmsOrgVo);

        return responseJson(model, orgList);
    }

    /**
     * ajax 방식의 코드 중복 체크
     */
    @OpenworksAuth(authType = AuthType.BASIC)
    @RequestMapping(value = "ND_selectDplctChckCode.do")
    public String selectDplctChckCode(HttpServletRequest request, Model model, CmsVO cmsVo) {

        Integer cnt = opCmsService.selectDplctChckCode(cmsVo);
        cmsVo.setUserMenuEngNm(cmsVo.getString("q_userMenuEngNm"));
        ValidateResultHolder holder = ValidateUtil.doFiledValidate(cmsVo, "userMenuEngNm");

        if(cnt == 0 && holder.isValid()) {
            return responseJson(model, Boolean.TRUE, MessageUtil.getMessage("common.dplctChckTrue"));
        }
        if(!holder.isValid()) {
            return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.validateFail"));
        }

        return responseJson(model, Boolean.FALSE, cnt, MessageUtil.getMessage("common.dplctChckFalse"));
    }

    /**
     * 사용자메뉴 메인화면
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "BD_selectCmsList.do")
    public void selectCmsBDList(HttpServletRequest request, Model model, CmsVO cmsVo) {

        @SuppressWarnings("unchecked")
        List<DomnCacheVO> domnList = (List<DomnCacheVO>) CacheService.get(BaseConfig.DOMN_LIST_CACHE_KEY);
        model.addAttribute(BaseConfig.KEY_DOMAIN_LIST, domnList);
    }

    /**
     * 사용자메뉴 관리화면
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "PD_selectCmsList.do")
    public void selectCmsPDList(HttpServletRequest request, Model model, CmsVO cmsVo) {

        @SuppressWarnings("unchecked")
        List<DomnCacheVO> domnList = (List<DomnCacheVO>) CacheService.get(BaseConfig.DOMN_LIST_CACHE_KEY);
        model.addAttribute(BaseConfig.KEY_DOMAIN_LIST, domnList);

        Integer cnt = opCmsService.selectDplctChckCode(cmsVo);
        model.addAttribute("menuCnt", cnt);
    }

    /**
     * 사용자메뉴 트리 목록
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "ND_selectCmsTreeList.do")
    public String selectCmsTreeList(HttpServletRequest request, Model model, CmsVO cmsVo) {

        return responseJson(model, opCmsService.selectCmsTreeList(cmsVo));
    }

    /**
     * 사용자메뉴 상세 Tab 메뉴
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "ND_selectCmsTab.do")
    public void selectCms(HttpServletRequest request, Model model, CmsVO cmsVo) {

        model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsService.selectSimpleCms(cmsVo));
    }

    /**
     * 사용자메뉴 입력 폼
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = { "INC_insertCmsForm.do" })
    public void insertCmsForm(HttpServletRequest request, Model model, CmsVO cmsVo, CmsLayoutVO cmsLayoutVo) {

        ValidateResultHolder holder = ValidateUtil.validate(cmsVo);
        model.addAttribute(BaseConfig.KEY_VALIDATE, holder);

        cmsVo.setMenuType(CmsSupport.MENU_TYPE);
        cmsVo.setLinkTypeNm(CmsSupport.LINK_TYPE_NM);

        // 현재 선택한 부모 메뉴의 뎁스가 1뎁스 이상인 경우에만 상위 부모 메뉴의 설정을 사용한다.
        CmsVO dataVo = opCmsService.selectSimpleCms(cmsVo);
        if(Validate.isNotEmpty(dataVo)) {
            cmsVo.setMenuLvlSn(dataVo.getMenuLvlSn());

            // 최상위 메뉴가 아닌 경우에는 상위 메뉴 레이아웃 정보를 기본으로 사용한다.
            if(dataVo.getMenuLvlSn() > 0) {
                cmsVo.setUpLytUseYn(CmsSupport.PARNTS_LAYOUT_USE_YN);
                cmsVo.setLytCdNo(dataVo.getLytCdNo());
            } else {
                cmsVo.setUpLytUseYn("N");
            }
        } else {
            cmsVo.setUpLytUseYn("N");
        }
        model.addAttribute(BaseConfig.KEY_DATA_VO, cmsVo);
        model.addAttribute(BaseConfig.KEY_DATA_LIST, opCmsLayoutService.selectCmsLayoutList(cmsLayoutVo));
    }

    /**
     * 사용자메뉴 등록
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_insertCms.do", method = RequestMethod.POST)
    public String insertCms(HttpServletRequest request, Model model, CmsVO cmsVo) {
        Integer key = 0;
        // 검증
        ValidateResultHolder holder = ValidateUtil.validate(cmsVo);
        if(holder.isValid()) {
            ISessVO loginVo = (ISessVO) getMgrSession();
            cmsVo.setRgtrId(loginVo.getUsername());
            cmsVo.setRgtrNm(loginVo.getFullname());
            cmsVo.setIpAddr(getIpAddr());

            // 등록 실행
            key = opCmsService.insertCms(cmsVo);
            if(key != 1) {
                return responseJson(model, Boolean.FALSE, key, MessageUtil.getMessage("common.processFail"));
            }
        } else {
            return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.validateFail"));
        }

        return responseJson(model, Boolean.TRUE, key, MessageUtil.getMessage("common.insertOk"));
    }

    /**
     * 사용자메뉴기본정보 수정 폼
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = { "INC_updateCmsBaseForm.do" })
    public void updateCmsBaseForm(HttpServletRequest request, Model model, CmsVO cmsVo, CmsLayoutVO cmsLayoutVo) {

        ValidateResultHolder holder = ValidateUtil.validate(cmsVo);
        model.addAttribute(BaseConfig.KEY_VALIDATE, holder);

        // Validate URL
        String css = Config.getString("cms-config.urls.cssValidate");
        String html = Config.getString("cms-config.urls.htmlValidate");
        model.addAttribute("CSS_VALIDATE", css);
        model.addAttribute("HTML_VALIDATE", html);

        model.addAttribute("CONTROL_LIST", CmsUtil.getCmsReferenceList(request.getSession().getServletContext()));
        model.addAttribute(BaseConfig.KEY_DATA_LIST, opCmsLayoutService.selectCmsLayoutList(cmsLayoutVo));
        model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsService.selectCms(cmsVo));
    }

    /**
     * 사용자메뉴기본정보 수정
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_updateCmsBase.do", method = RequestMethod.POST)
    public String updateCmsBase(HttpServletRequest request, Model model, CmsVO cmsVo) {
        Integer updateCnt = 0;
        // 검증
        ValidateResultHolder holder = ValidateUtil.validate(cmsVo);
        if(holder.isValid()) {
            ISessVO loginVo = (ISessVO) getMgrSession();
            cmsVo.setMdfrId(loginVo.getUsername());
            cmsVo.setUpdusrNm(loginVo.getFullname());
            cmsVo.setIpAddr(getIpAddr());

            updateCnt = opCmsService.updateCmsBase(cmsVo);
            if(updateCnt != 1) {
                return responseJson(model, Boolean.FALSE, updateCnt, MessageUtil.getMessage("common.processFail"));
            }
        } else {
            return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.validateFail"));
        }

        return responseJson(model, Boolean.TRUE, updateCnt, MessageUtil.getMessage("common.updateOk"));
    }

    /**
     * 사용자메뉴추가정보 수정 폼
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = { "INC_updateCmsAddForm.do" })
    public void updateCmsAddForm(HttpServletRequest request, Model model, CmsVO cmsVo, CmsLayoutVO cmsLayoutVo) {

        ValidateResultHolder holder = ValidateUtil.validate(cmsVo);
        model.addAttribute(BaseConfig.KEY_VALIDATE, holder);

        model.addAttribute(BaseConfig.KEY_DATA_VO, opCmsService.selectSimpleCms(cmsVo));
    }

    /**
     * 사용자메뉴추가정보 수정
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_updateCmsAdd.do", method = RequestMethod.POST)
    public String updateCmsAdd(HttpServletRequest request, Model model, CmsVO cmsVo) {
        Integer updateCnt = 0;
        // 검증
        ValidateResultHolder holder = ValidateUtil.validate(cmsVo);
        if(holder.isValid()) {
            ISessVO loginVo = (ISessVO) getMgrSession();
            cmsVo.setMdfrId(loginVo.getUsername());
            cmsVo.setUpdusrNm(loginVo.getFullname());
            cmsVo.setIpAddr(getIpAddr());

            List<FileVO> fileList = CmsUploadHelper.upload(request, cmsVo);

            if(Validate.isNotEmpty(fileList) && fileList.size() > 0) {
                FileVO fileVO = null;
                for(int i = 0 ; i < fileList.size() ; i++) {
                    fileVO = fileList.get(i);
                    String inputNm = fileVO.getInptDataNm();
                    String fileUrl = fileVO.getFileUrlAddr();
                    if("sjImageFile".equals(inputNm)) {
                        cmsVo.setUserMenuImgNm(fileUrl);
                    } else if("menuTtlImgNmFile".equals(inputNm)) {
                        cmsVo.setMenuTtlImgNm(fileUrl);
                    } else if("menuOnImageFile".equals(inputNm)) {
                        cmsVo.setMenuOnImgNm(fileUrl);
                    } else if("menuOffImageFile".equals(inputNm)) {
                        cmsVo.setMenuOffImgNm(fileUrl);
                    } else if("cssFileTmp".equals(inputNm)) {
                        cmsVo.setCssFileNm(fileUrl);
                    }
                }
            }

            updateCnt = opCmsService.updateCmsAdd(cmsVo);
            if(updateCnt != 1) {
                return responseJson(model, Boolean.FALSE, updateCnt, MessageUtil.getMessage("common.processFail"));
            }
        } else {
            return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.validateFail"));
        }

        return responseJson(model, Boolean.TRUE, updateCnt, MessageUtil.getMessage("common.updateOk"));
    }

    /**
     * 사용자메뉴 정렬순서 수정
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_updateCmsSortOrder.do")
    public String updateCmsSortOrder(HttpServletRequest request, Model model, CmsVO cmsVo) {

        ISessVO loginVo = (ISessVO) getMgrSession();
        cmsVo.setMdfrId(loginVo.getUsername());
        cmsVo.setUpdusrNm(loginVo.getFullname());
        cmsVo.setIpAddr(getIpAddr());

        Boolean success = opCmsService.updateCmsSortOrder(cmsVo);

        if(!success) {
            return responseJson(model, Boolean.FALSE, MessageUtil.getMessage("common.processFail"));
        }

        return responseJson(model, Boolean.TRUE, MessageUtil.getMessage("common.updateOk"));
    }

    /**
     * 사용자메뉴 삭제
     */
    @OpenworksAuth(authType = AuthType.MANAGER)
    @RequestMapping(value = "ND_deleteCms.do", method = RequestMethod.POST)
    public String deleteCms(HttpServletRequest request, Model model, CmsVO cmsVo) {

        Integer cnt = opCmsService.deleteCms(cmsVo);

        if(cnt != 1) {
            return responseJson(model, Boolean.FALSE, cmsVo.getUserMenuEngNm(), MessageUtil.getMessage("common.processFail"));
        }

        return responseJson(model, Boolean.TRUE, cmsVo.getUserMenuEngNm(), MessageUtil.getMessage("common.deleteOk"));
    }

    /**
     * 선택한 컨트롤(BEAN Name)과 함수명을 받아서 대상 목록을 반환한다.
     */
    @RequestMapping(value = "selectCmsRefernce.do", method = RequestMethod.POST)
    public String selectCmsReference(HttpServletRequest request, Model model, CmsReferenceVO cmsRVO) {
        List<CmsReferenceVO> umrList = null;
        try {
            umrList = CmsUtil.getCmsReference(request.getSession().getServletContext(), cmsRVO);
        } catch (Exception e) {
            return responseJson(model, new ArrayList<CmsReferenceVO>());
        }
        return responseJson(model, umrList);
    }

}
