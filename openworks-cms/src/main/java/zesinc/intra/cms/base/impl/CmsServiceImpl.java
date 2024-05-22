/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.base.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zesinc.component.file.FileService;
import zesinc.core.lang.Validate;
import zesinc.intra.cms.base.CmsMapper;
import zesinc.intra.cms.base.CmsService;
import zesinc.intra.cms.base.domain.CmsLwprtUrlVO;
import zesinc.intra.cms.base.domain.CmsOrgVO;
import zesinc.intra.cms.base.domain.CmsUserGradVO;
import zesinc.intra.cms.base.domain.CmsVO;
import zesinc.intra.cms.cntnts.CmsCntntsService;
import zesinc.intra.cms.cntnts.domain.CmsCntntsVO;
import zesinc.intra.cms.support.CmsSupport;
import zesinc.intra.cms.support.PublishUtil;
import zesinc.web.vo.TreeVO;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사용자메뉴 정보 서비스 구현 클레스
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

@Service("opCmsService")
public class CmsServiceImpl extends EgovAbstractServiceImpl implements CmsService {

    @Resource(name = "opCmsDao")
    private CmsMapper opCmsDao;
    // 첨부파일
    @Resource(name = "opFileService")
    private FileService opFileService;
    // 컨텐츠관리
    @Resource(name = "opCmsCntntsService")
    private CmsCntntsService opCmsCntntsService;

    @Override
    public List<CmsOrgVO> selectDeptList(CmsOrgVO cmsOrgVo) {

        return opCmsDao.selectDeptList(cmsOrgVo);
    }

    @Override
    public List<CmsOrgVO> selectMngrList(CmsOrgVO cmsOrgVo) {

        return opCmsDao.selectMngrList(cmsOrgVo);
    }

    @Override
    public Integer selectDplctChckCode(CmsVO cmsVo) {

        return opCmsDao.selectDplctChckCode(cmsVo);
    }

    @Override
    public CmsVO selectCms(CmsVO cmsVo) {

        CmsVO dataVo = opCmsDao.selectCms(cmsVo);
        // 첨부파일
        if(Validate.isNotEmpty(dataVo.getFileSn())) {
            dataVo.setFileList(opFileService.selectFileList(dataVo.getFileSn()));
        }
        // 메뉴권한담당자목록
        if(Validate.isNotEmpty(dataVo.getAuthrtGroupNm()) && dataVo.getAuthrtGroupNm().equals("CHARGER")) {
            dataVo.setAuthorChargerList(opCmsDao.selectAuthorChargerList(cmsVo));
        }
        // 사용자권한등급목록
        dataVo.setUserGradList(opCmsDao.selectUserGradList(cmsVo));
        // 화면 컨트롤을 위해 제일 마지막 / 문자를 삭제
        String sitePathNm = dataVo.getSitePathNm();
        if(sitePathNm.charAt(sitePathNm.length() - 1) == '/') {
            sitePathNm = sitePathNm.substring(0, sitePathNm.length() - 1);
            dataVo.setSitePathNm(sitePathNm);
        }
        // 하위메뉴URL
        dataVo.setLwprtMenuUrlAddr(opCmsDao.selectUserMenuLwprtUrl(dataVo));

        return dataVo;
    }

    @Override
    public CmsVO selectSimpleCms(CmsVO cmsVo) {

        return opCmsDao.selectCms(cmsVo);
    }

    @Override
    public List<TreeVO> selectCmsTreeList(CmsVO cmsVo) {

        List<TreeVO> dataList = opCmsDao.selectCmsTreeList(cmsVo);

        return dataList;
    }

    @Override
    public List<CmsVO> selectCmsList(CmsVO cmsVo) {

        List<CmsVO> dataList = opCmsDao.selectCmsList(cmsVo);

        return dataList;
    }

    @Override
    public Integer insertCms(CmsVO cmsVo) {

        CmsVO parentVo = opCmsDao.selectCms(cmsVo);
        // 만약 초기데이터가 없다면 생성하고난 후 현재 요청정보를 저장한다.
        if(Validate.isEmpty(parentVo)) {
            parentVo = createDummyRoot(cmsVo);
            opCmsDao.insertCms(parentVo);
        }

        // 기본 경로
        String bscPathNm;
        if(Validate.isNotEmpty(parentVo.getBscPathNm())) {
            bscPathNm = parentVo.getBscPathNm() + parentVo.getUserMenuEngNm() + "/";
        } else {
            bscPathNm = "/";
        }
        // 컨텐츠인경우 파일생성 경로
        String userMenuUrlAddr = "";
        if(cmsVo.getMenuType().equals("CONTENTS")) {
            userMenuUrlAddr = bscPathNm + cmsVo.getUserMenuEngNm() + ".jsp";
        }
        // 메뉴 경로
        String menuPathNm;
        if(Validate.isNotEmpty(parentVo.getMenuPathNm())) {
            menuPathNm = parentVo.getMenuPathNm() + " &gt; " + cmsVo.getMenuNm();
        } else {
            menuPathNm = cmsVo.getMenuNm();
        }
        // 메뉴 깊이
        int menuLvlSn = parentVo.getMenuLvlSn() + 1;

        cmsVo.setBscPathNm(bscPathNm);
        cmsVo.setuserMenuUrlAddr(userMenuUrlAddr);
        cmsVo.setMenuPathNm(menuPathNm);
        cmsVo.setMenuLvlSn(menuLvlSn);
        cmsVo.setUpMenuEngNm(parentVo.getUserMenuEngNm());

        // 최상위 메뉴여부에 따른 설정 분기
        if(menuLvlSn == 1) {
            // 각메뉴의 최상위
            cmsVo.setHghrkMenuEngNm(cmsVo.getUserMenuEngNm());
            cmsVo.setUpLytUseYn("N");
        } else if(menuLvlSn > 1) {
            // 서브 메뉴 추가시
            cmsVo.setHghrkMenuEngNm(parentVo.getHghrkMenuEngNm());
            // 레이아웃 상속여부에 따른 자동설정
            if(Validate.isNotEmpty(cmsVo.getUpLytUseYn()) && cmsVo.getUpLytUseYn().equals("Y")) {
                cmsVo.setLytCdNo(parentVo.getLytCdNo());
            }
        } else {
            // 최초 메뉴 생성시
            cmsVo.setHghrkMenuEngNm(parentVo.getUserMenuEngNm());
        }
        // 사용여부
        cmsVo.setUseYn(CmsSupport.USE_YN);
        // 화면표시여부
        cmsVo.setIndctYn(CmsSupport.INDCT_YN);
        // 컨텐츠 수정시 승인필요 여부
        cmsVo.setAprvYn(CmsSupport.APRV_YN);
        // 권한유형
        cmsVo.setAuthrtGroupNm(CmsSupport.AUTHRT_GROUP_NM);
        // 승인필요여부
        cmsVo.setAprvYn(CmsSupport.APRV_YN);
        // 만족도표시여부
        cmsVo.setDgstfnIndctYn(CmsSupport.DGSTFN_INDCT_YN);
        // 담당자표시여부
        cmsVo.setPicIndctYn(CmsSupport.PIC_INDCT_YN);
        // SNS 사용여부
        cmsVo.setSnsUseYn(CmsSupport.SNS_USE_YN);

        return opCmsDao.insertCms(cmsVo);
    }

    /**
     * 사이트 더미루트 메뉴 생성
     * 
     * @return
     */
    private CmsVO createDummyRoot(CmsVO cmsVo) {

        CmsVO dataVo = new CmsVO();

        dataVo.setSiteSn(cmsVo.getSiteSn());
        dataVo.setHghrkMenuEngNm(CmsSupport.DUMMY_TOP_MENU);
        dataVo.setUpMenuEngNm(CmsSupport.DUMMY_RARNT_MENU);
        dataVo.setUserMenuEngNm(CmsSupport.HIGH_CMS_CD);
        dataVo.setMenuNm(CmsSupport.DUMMY_MENU_NM);
        dataVo.setMenuPathNm("");
        dataVo.setBscPathNm("");
        dataVo.setMenuLvlSn(CmsSupport.DUMMY_MENU_DP);
        dataVo.setSortSn(CmsSupport.DUMMY_SORT_RODR);
        dataVo.setUseYn("N");
        dataVo.setIndctYn(CmsSupport.INDCT_YN);
        dataVo.setAprvYn(CmsSupport.APRV_YN);

        return dataVo;
    }

    @Override
    public Integer updateCmsBase(CmsVO cmsVo) {
        // 등록되어 있는 권한 담당자 삭제 후 처리
        opCmsDao.deleteAuthorCharger(cmsVo);
        // 관리 권한유형이 담당자인경우 처리
        if(Validate.isNotEmpty(cmsVo.getAuthrtGroupNm()) && cmsVo.getAuthrtGroupNm().equals("CHARGER")) {
            if(Validate.isNotEmpty(cmsVo.getAuthorPicIds())) {
                CmsOrgVO cmsOrgVo = new CmsOrgVO();
                cmsOrgVo.setSiteSn(cmsVo.getSiteSn());
                cmsOrgVo.setUserMenuEngNm(cmsVo.getUserMenuEngNm());
                cmsOrgVo.setRgtrId(cmsVo.getMdfrId());
                for(String authorPicId : cmsVo.getAuthorPicIds()) {
                    cmsOrgVo.setAuthorPicId(authorPicId);
                    opCmsDao.insertAuthorCharger(cmsOrgVo);
                }
            }
        }

        // 등록되어 있는 사용자권한등급목록 삭제 후 처리
        opCmsDao.deleteUserGrad(cmsVo);
        if(Validate.isNotEmpty(cmsVo.getUserGrdCdIds())) {
            CmsUserGradVO userGradVo = new CmsUserGradVO();
            userGradVo.setSiteSn(cmsVo.getSiteSn());
            userGradVo.setUserMenuEngNm(cmsVo.getUserMenuEngNm());
            userGradVo.setRgtrId(cmsVo.getMdfrId());
            for(String userGrdCdId : cmsVo.getUserGrdCdIds()) {
                userGradVo.setUserGrdCdId(userGrdCdId);
                opCmsDao.insertUserGrad(userGradVo);
            }
        }

        // 하위메뉴URL 삭제 후 등록
        CmsLwprtUrlVO cmsLwprtUrlVo = new CmsLwprtUrlVO();
        cmsLwprtUrlVo.setSiteSn(cmsVo.getSiteSn());
        cmsLwprtUrlVo.setUserMenuEngNm(cmsVo.getUserMenuEngNm());
        opCmsDao.deleteUserMenuLwprtUrl(cmsLwprtUrlVo);

        List<String> lwprtMenuUrlAddrList = cmsVo.getLwprtMenuUrlAddr();
        if(Validate.isNotEmpty(lwprtMenuUrlAddrList)) {
            for(String lwprtMenuUrlAddr : lwprtMenuUrlAddrList) {
                if(Validate.isNotEmpty(lwprtMenuUrlAddr)) {
                    cmsLwprtUrlVo.setLwprtMenuUrlAddr(lwprtMenuUrlAddr);
                    opCmsDao.insertUserMenuLwprtUrl(cmsLwprtUrlVo);
                }
            }
        }

        CmsVO dataVo = opCmsDao.selectCms(cmsVo);
        CmsVO parentVo = opCmsDao.selectParentCms(cmsVo);

        // 레이아웃 변경여부
        Boolean isLayoutChange = Boolean.FALSE;
        // 메뉴명 변경여부
        Boolean isMenuChange = Boolean.FALSE;

        // 메뉴명 변경시 하위 메뉴들의 메뉴경로명을 모두 갱신
        if(!dataVo.getMenuNm().equals(cmsVo.getMenuNm())) {
            String menuPathNm;
            if(Validate.isNotEmpty(parentVo.getMenuPathNm())) {
                menuPathNm = parentVo.getMenuPathNm() + " &gt; " + cmsVo.getMenuNm();
            } else {
                menuPathNm = cmsVo.getMenuNm();
            }
            cmsVo.setMenuPathNm(menuPathNm);
            isMenuChange = Boolean.TRUE;
        } else {
            cmsVo.setMenuPathNm(dataVo.getMenuPathNm());
        }

        // 상위 메뉴가 더미 메뉴가 아닌 경우에만 상위메뉴의 설정을 가져옴
        if(!parentVo.getUserMenuEngNm().equals(CmsSupport.HIGH_CMS_CD)) {
            String pLytCdNo = (parentVo.getLytCdNo() == null) ? "" : parentVo.getLytCdNo();
            String cLytCdNo = (dataVo.getLytCdNo() == null) ? "" : dataVo.getLytCdNo();
            /*
             * 레이아웃 설정 변경에 따라서 부모메뉴레이아웃 사용설정인 하위 메뉴까지 모두 변경한다.
             * - 부모레이아웃 사용여부가 Y로 변경된 경우 (부모 레이아웃 코드를 가져와서 설정)
             * - 부모레이아웃 사용이면서 부모와 코드가 다른 경우
             * - 기존 레이아웃과 달라진 경우
             */
            if(dataVo.getUpLytUseYn().equals("N") && cmsVo.getUpLytUseYn().equals("Y")) {
                cmsVo.setLytCdNo(parentVo.getLytCdNo());
                isLayoutChange = Boolean.TRUE;
            } else if(cmsVo.getUpLytUseYn().equals("Y") && !pLytCdNo.equals(cmsVo.getLytCdNo())) {
                cmsVo.setLytCdNo(pLytCdNo);
                isLayoutChange = Boolean.TRUE;
            } else if(!cLytCdNo.equals(cmsVo.getLytCdNo())) {
                isLayoutChange = Boolean.TRUE;
            }
        }

        Integer updateCnt = opCmsDao.updateCmsBase(cmsVo);

        if(isLayoutChange || isMenuChange) {
            cmsVo.setUpMenuEngNm(cmsVo.getUserMenuEngNm());

            updateChildLayout(cmsVo, isLayoutChange, isMenuChange);
        }
        return updateCnt;
    }

    /**
     * 메뉴정보 인자를 부모로하여 상위 메뉴의 레이아웃을 사용하도록 설정된 자식 메뉴 모두를
     * 부모의 레이아웃 코드로 갱신한다. 재귀 호출을 통하여 처리
     * 
     * @param cmsVo
     */
    private void updateChildLayout(CmsVO cmsVo, Boolean isLayoutChange, Boolean isMenuChange) {

        // 업데이트용 목록 조회 조건 추가
        List<CmsVO> childList = opCmsDao.selectChildCmsList(cmsVo);
        for(CmsVO child : childList) {

            // 부모메뉴 레이아웃 사용 설정시 함께 수정
            if(isLayoutChange && child.getUpLytUseYn().equals("Y")) {
                child.addParam("q_useUpdateLayout", "Y");
                child.setLytCdNo(cmsVo.getLytCdNo());
            }

            if(isMenuChange) {
                String menuPathNm;
                if(Validate.isNotEmpty(cmsVo.getMenuPathNm())) {
                    menuPathNm = cmsVo.getMenuPathNm() + " &gt; " + child.getMenuNm();
                } else {
                    menuPathNm = child.getMenuNm();
                }
                child.setMenuPathNm(menuPathNm);
            }

            opCmsDao.updateLytCdNo(child);
            // 재귀 호출
            child.setUpMenuEngNm(child.getUserMenuEngNm());
            updateChildLayout(child, isLayoutChange, isMenuChange);
        }
    }

    @Override
    public Integer updateCmsAdd(CmsVO cmsVo) {
        // 삭제요청된 이미지 삭제
        deleteImages(cmsVo, null);

        return opCmsDao.updateCmsAdd(cmsVo);
    }

    @Override
    public Boolean updateCmsSortOrder(CmsVO cmsVo) {

        if(Validate.isEmpty(cmsVo.getParamMap())) {
            return Boolean.FALSE;
        }

        Integer siteSn = cmsVo.getInteger("q_siteSn");
        String upMenuEngNm = cmsVo.getString("q_upMenuEngNm");
        Integer sortSn = cmsVo.getInteger("q_sortSn");
        String userMenuEngNm = cmsVo.getString("q_userMenuEngNm");

        if(Validate.isEmpty(upMenuEngNm) || Validate.isEmpty(siteSn)
            || Validate.isEmpty(sortSn) || Validate.isEmpty(userMenuEngNm)) {

            return Boolean.FALSE;
        }

        // 부모 메뉴 정보
        CmsVO parentVo = opCmsDao.selectParentCms(cmsVo);
        // 현재 메뉴 정보
        CmsVO currVo = opCmsDao.selectCms(cmsVo);

        // 기본 경로
        String bscPathNm;
        if(Validate.isNotEmpty(parentVo.getBscPathNm())) {
            bscPathNm = parentVo.getBscPathNm() + parentVo.getUserMenuEngNm() + "/";
        } else {
            bscPathNm = "/";
        }

        // 메뉴 깊이
        int menuLvlSn = parentVo.getMenuLvlSn() + 1;

        CmsVO paramVo = new CmsVO();
        // 기본 데이터 설정
        paramVo.setSiteSn(siteSn);
        paramVo.setUpMenuEngNm(parentVo.getUserMenuEngNm());
        paramVo.setUserMenuEngNm(currVo.getUserMenuEngNm());
        paramVo.setBscPathNm(bscPathNm);
        paramVo.setMenuLvlSn(menuLvlSn);
        paramVo.setMenuNm(currVo.getMenuNm());

        // 이동대상의 부모를 기준으로 동레벨의 메뉴목록을 가져온다. 없으면 리스트 생성
        List<CmsVO> childList = opCmsDao.selectChildCmsList(paramVo);
        if(Validate.isEmpty(childList)) {
            childList = new ArrayList<CmsVO>();
            childList.add(paramVo);
        } else {
            childList.add(sortSn, paramVo);
        }

        // 메뉴 경로
        String menuPathNm;
        // 탑메뉴코드
        String hghrkMenuEngNm;

        // 동레벨의 메뉴목록의 정렬순서를 새로 업데이트한다.
        int childCnt = childList.size();
        CmsVO childVo;
        for(int i = 0 ; i < childCnt ; i++) {
            childVo = childList.get(i);

            childVo.setSortSn(i + 1);

            if(childVo.getMenuLvlSn() > 1) {
                hghrkMenuEngNm = parentVo.getHghrkMenuEngNm();
            } else {
                hghrkMenuEngNm = childVo.getUserMenuEngNm();
            }
            childVo.setHghrkMenuEngNm(hghrkMenuEngNm);

            if(Validate.isNotEmpty(parentVo.getMenuPathNm())) {
                menuPathNm = parentVo.getMenuPathNm() + " &gt; " + childVo.getMenuNm();
            } else {
                menuPathNm = childVo.getMenuNm();
            }
            childVo.setMenuPathNm(menuPathNm);

            opCmsDao.updateCmsSortOrder(childVo);
        }

        // 부모가 달라지는 이동의 경우 원래 있던 메뉴의 정렬 번호를 갱신한다.
        if(!upMenuEngNm.equals(currVo.getUpMenuEngNm())) {
            paramVo.setUpMenuEngNm(currVo.getUpMenuEngNm());

            childList = opCmsDao.selectChildCmsList(paramVo);
            if(Validate.isNotEmpty(childList)) {
                childCnt = childList.size();
                for(int i = 0 ; i < childCnt ; i++) {
                    childVo = childList.get(i);

                    childVo.setSortSn(i + 1);
                    opCmsDao.updateCmsSortOrder(childVo);
                }
            }

            // 부모의 레이아웃 코드를 상속받도록 설정시 부모의 레이아웃 코드로 전부 갱신한다.
            if(currVo.getUpLytUseYn().equals("Y")) {
                paramVo.addParam("q_useUpdateLayout", "Y");
                paramVo.setLytCdNo(parentVo.getLytCdNo());
                // 현재 매뉴 우선 수정
                opCmsDao.updateLytCdNo(paramVo);
            } else {
                paramVo.setLytCdNo(currVo.getLytCdNo());
            }

            // 부모가 달라지는 경우 부모로 부터 상속받는 최상위 메뉴 코드, 경로명, 메뉴깊이등을 수정한다.
            paramVo.setUpMenuEngNm(currVo.getUserMenuEngNm());
            updateChildMenu(paramVo);
        }

        return Boolean.TRUE;
    }

    /**
     * 부모가 변경된 경우에 호출하며 모든 하위 메뉴의 상속필요 정보를 업데이트한다.
     * 최상위 메뉴코드, 경로명, 메뉴 깊이 등 재귀 호출을 통하여 처리
     * 
     * @param cmsVo
     */
    private void updateChildMenu(CmsVO cmsVo) {

        int menuLvlSn;
        String menuPathNm;
        String bscPathNm;
        String hghrkMenuEngNm;

        List<CmsVO> childList = opCmsDao.selectChildCmsList(cmsVo);
        for(CmsVO child : childList) {
            // 부모메뉴 레이아웃 사용 설정시 함께 수정
            if(child.getUpLytUseYn().equals("Y")) {
                child.addParam("q_useUpdateLayout", "Y");
                child.setLytCdNo(cmsVo.getLytCdNo());
            }

            // 최상위 메뉴 코드
            if(child.getMenuLvlSn() > 1) {
                hghrkMenuEngNm = cmsVo.getHghrkMenuEngNm();
            } else {
                hghrkMenuEngNm = child.getUserMenuEngNm();
            }
            child.setHghrkMenuEngNm(hghrkMenuEngNm);

            // 메뉴 깊이
            menuLvlSn = cmsVo.getMenuLvlSn() + 1;
            child.setMenuLvlSn(menuLvlSn);

            // 기본 경로
            if(Validate.isNotEmpty(cmsVo.getBscPathNm())) {
                bscPathNm = cmsVo.getBscPathNm() + cmsVo.getUserMenuEngNm() + "/";
            } else {
                bscPathNm = "/";
            }
            child.setBscPathNm(bscPathNm);

            // 메뉴명 경로
            if(Validate.isNotEmpty(cmsVo.getMenuPathNm())) {
                menuPathNm = cmsVo.getMenuPathNm() + " &gt; " + child.getMenuNm();
            } else {
                menuPathNm = child.getMenuNm();
            }
            child.setMenuPathNm(menuPathNm);

            opCmsDao.updateCmsSortOrder(child);

            // 재귀 호출
            child.setUpMenuEngNm(child.getUserMenuEngNm());
            updateChildMenu(child);
        }
    }

    @Override
    public Integer deleteCms(CmsVO cmsVo) {

        // 원본 삭제
        CmsVO dataVo = selectCms(cmsVo);
        dataVo.setParamMap(cmsVo.getParamMap());

        // 하위 메뉴URL 삭제
        opCmsDao.deleteMenuLwprtUrl(dataVo);
        // 등록되어 있는 권한 담당자 삭제
        opCmsDao.deleteAuthorCharger(cmsVo);
        // 등록되어 있는 사용자권한등급목록 삭제
        opCmsDao.deleteUserGrad(cmsVo);
        // 등록되어 있는 메타데이터 삭제
        opCmsDao.deleteUserMeta(cmsVo);
        // 등록되어 있는 평가상세 삭제
        opCmsDao.deleteUserEvlDetail(cmsVo);
        // 등록되어 있는 평가 삭제
        opCmsDao.deleteUserEvl(cmsVo);

        // 컨텐츠 생성 이력 목록도 함께 모두 삭제
        CmsCntntsVO cmsCntntsVo = new CmsCntntsVO();
        cmsCntntsVo.setParamMap(cmsVo.getParamMap());
        opCmsCntntsService.deleteCmsAllCntnts(cmsCntntsVo);

        Integer delCnt = opCmsDao.deleteCms(dataVo);
        // 첨부파일 삭제
        if(Validate.isNotEmpty(dataVo.getFileSn())) {
            opFileService.deleteFile(dataVo.getFileSn());
        }
        // 이미지 파일 삭제
        deleteImages(cmsVo, dataVo);
        // 컨텐츠 파일 삭제
        String contentsFilePath = dataVo.getBscPathNm() + dataVo.getUserMenuEngNm() + ".jsp";
        PublishUtil.deleteFile(contentsFilePath);

        // 최종 메뉴 삭제라면 dummy 메뉴도 삭제
        if(dataVo.getMenuLvlSn() <= 0) {
            dataVo.addParam("q_siteSn", dataVo.getSiteSn());
            dataVo.addParam("q_userMenuEngNm", "web");
            opCmsDao.deleteCms(dataVo);
        }
        return delCnt;
    }

    /**
     * 이전엔 있었는데 값이 없어진 경우 실 이미지 삭제
     * 
     * @param cmsVo
     */
    private void deleteImages(CmsVO cmsVo, CmsVO dataVo) {

        if(Validate.isEmpty(dataVo)) {
            dataVo = selectCms(cmsVo);
        }
        // 제목이미지
        if(Validate.isNotEmpty(dataVo.getUserMenuImgNm()) && Validate.isEmpty(cmsVo.getUserMenuImgNm())) {
            PublishUtil.deleteFile(dataVo.getUserMenuImgNm());
        }
        // 메뉴제목이미지
        if(Validate.isNotEmpty(dataVo.getMenuTtlImgNm()) && Validate.isEmpty(cmsVo.getMenuTtlImgNm())) {
            PublishUtil.deleteFile(dataVo.getMenuTtlImgNm());
        }
        // 메뉴온이미지
        if(Validate.isNotEmpty(dataVo.getMenuOnImgNm()) && Validate.isEmpty(cmsVo.getMenuOnImgNm())) {
            PublishUtil.deleteFile(dataVo.getMenuOnImgNm());
        }
        // 메뉴오프이미지
        if(Validate.isNotEmpty(dataVo.getMenuOffImgNm()) && Validate.isEmpty(cmsVo.getMenuOffImgNm())) {
            PublishUtil.deleteFile(dataVo.getMenuOffImgNm());
        }
        // CSS파일
        if(Validate.isNotEmpty(dataVo.getCssFileNm()) && Validate.isEmpty(cmsVo.getCssFileNm())) {
            PublishUtil.deleteFile(dataVo.getCssFileNm());
        }
    }
}
