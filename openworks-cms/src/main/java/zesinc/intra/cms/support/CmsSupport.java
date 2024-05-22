/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zesinc.core.config.Config;

/**
 * CMS 기능 관련 상수 값을 모은 지원 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 5. 27.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsSupport {

    /**
     * 인스턴스 생성불가
     */
    private CmsSupport() {
    }

    // -------------------------------------------------------------------------------------------------------------
    // 컨텐츠 승인/반려 프로세스 코드
    // -------------------------------------------------------------------------------------------------------------

    /** 코드목록 */
    private static List<Map<String, String>> CONTMM_STTUS_LIST;
    private static Map<String, ConfmSttus> CONTMM_STTUS_MAP;
    static {
        CONTMM_STTUS_LIST = new ArrayList<Map<String, String>>();
        CONTMM_STTUS_MAP = new HashMap<String, ConfmSttus>();

        ConfmSttus[] values = ConfmSttus.values();
        Map<String, String> sttusMap;
        for(ConfmSttus aprvSttsNo : values) {
            sttusMap = new HashMap<String, String>();
            sttusMap.put(aprvSttsNo.name(), aprvSttsNo.getName());

            CONTMM_STTUS_LIST.add(sttusMap);
            CONTMM_STTUS_MAP.put(aprvSttsNo.name(), aprvSttsNo);
        }
    }

    /**
     * 문자열에 해당하는 승인/반려 상태 ConfmSttus를 반환
     * 
     * @param aprvSttsNo
     * @return
     */
    public static ConfmSttus getAprvSttsNo(String aprvSttsNo) {

        return CONTMM_STTUS_MAP.get(aprvSttsNo);
    }

    /**
     * 문자열에 해당하는 승인/반려 상태명를 반환
     * 
     * @param aprvSttsNo
     * @return
     */
    public static String getConfmSttusNm(String aprvSttsNo) {

        return CONTMM_STTUS_MAP.get(aprvSttsNo).getName();
    }

    /**
     * 상태코드 목록을 반환
     * 
     * @return
     */
    public static List<Map<String, String>> getConfmSttusList() {

        return CONTMM_STTUS_LIST;
    }

    // -------------------------------------------------------------------------------------------------------------
    // 최상위 DUMMY 메뉴 생성 정보
    // -------------------------------------------------------------------------------------------------------------

    /** 최상위 기본 메뉴코드 */
    public static String HIGH_CMS_CD = Config.getString("webapp-config.defaultCode.highCmsCd", "web");
    /** dummy 메뉴명 */
    public static String DUMMY_MENU_NM = Config.getString("cms-config.dummyRoot.menuNm", "dummy");
    /** dummy 탑 메뉴 코드 */
    public static String DUMMY_TOP_MENU = Config.getString("cms-config.dummyRoot.hghrkMenuEngNm", "ROOT");
    /** dummy 부모 메뉴 코드 */
    public static String DUMMY_RARNT_MENU = Config.getString("cms-config.dummyRoot.parentMenuCode", "DOMAIN");
    /** dummy 메뉴 뎁스 */
    public static Integer DUMMY_MENU_DP = Config.getInt("cms-config.dummyRoot.menuDepth", -1);
    /** dummy 정렬순번 */
    public static Integer DUMMY_SORT_RODR = Config.getInt("cms-config.dummyRoot.sortOrder", -1);

    // -------------------------------------------------------------------------------------------------------------
    // 메뉴 생성시 기본 값 정보
    // -------------------------------------------------------------------------------------------------------------

    /** 권한유형 */
    public static String AUTHRT_GROUP_NM = Config.getString("cms-config.default-values.authrtGroupNm", "NO_ONE");
    /** 메뉴유형 */
    public static String MENU_TYPE = Config.getString("cms-config.default-values.menuType", "CONTENTS");
    /** 링크유형 */
    public static String LINK_TYPE_NM = Config.getString("cms-config.default-values.linkTypeNm", "DEFAULT");
    /** 승인여부 */
    public static String APRV_YN = Config.getString("cms-config.default-values.aprvYn", "N");
    /** 만족도표시여부 */
    public static String DGSTFN_INDCT_YN = Config.getString("cms-config.default-values.dgstfnIndctYn", "N");
    /** 담당자표시여부 */
    public static String PIC_INDCT_YN = Config.getString("cms-config.default-values.picIndctYn", "N");
    /** 메뉴표시여부 */
    public static String INDCT_YN = Config.getString("cms-config.default-values.indctYn", "N");
    /** 사용여부 */
    public static String USE_YN = Config.getString("cms-config.default-values.useYn", "Y");
    /** SNS사용여부 */
    public static String SNS_USE_YN = Config.getString("cms-config.default-values.snsUseYn", "N");
    /** 부모레이아웃 사용여부 */
    public static String PARNTS_LAYOUT_USE_YN = Config.getString("cms-config.default-values.upLytUseYn", "Y");

    // -------------------------------------------------------------------------------------------------------------
    // 파일 생성 기본 경로
    // -------------------------------------------------------------------------------------------------------------

    public static String DECORATOR_FILE_ROOT = Config.getString("cms-config.basePath.decoratorFileRoot", "WEB-INF/jsp/common/decorator");
    public static String MENU_FILE_ROOT = Config.getString("cms-config.basePath.menuDataRoot", "/webcontent/menu/data");
    public static String RESOURCE_FILE_ROOT = Config.getString("cms-config.basePath.resourceFileRoot", "/webcontent/menu/resources");

    // -------------------------------------------------------------------------------------------------------------
    // 기타 정보
    // -------------------------------------------------------------------------------------------------------------

    public static String ADMIN_URL = Config.getString("cms-config.urls.adminUrl", "/intra/cms/cntnts/PD_updateCmsCntntsForm.do");
    public static String COPY_URL_PREFIX = Config.getString("webapp-config.ccl.url-prefix");
    public static String COPY_URL_POSTFIX = Config.getString("webapp-config.ccl.url-postfix");

}
