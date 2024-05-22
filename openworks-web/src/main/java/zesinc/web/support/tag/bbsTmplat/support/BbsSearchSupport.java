/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.bbsTmplat.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 게시판에서 사용되는 공통 상수 관리 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 11. 24.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class BbsSearchSupport {

    /** 검색시 해당 코드에 대응한 DB 컬럼 */
    private static Map<String, String> SEARCH_CODE_MAP = new HashMap<String, String>();
    static {
        SEARCH_CODE_MAP.put("bbsSn", "BBS_SN");
        SEARCH_CODE_MAP.put("bbsDocNo", "BBS_DOC_NO");
        SEARCH_CODE_MAP.put("rfrncDocNo", "RFRNC_DOC_NO");
        SEARCH_CODE_MAP.put("ntcPstYn", "NTC_PST_YN");
        SEARCH_CODE_MAP.put("ntcBgngDt", "NTC_BGNG_DT");
        SEARCH_CODE_MAP.put("ntcEndDt", "NTC_END_DT");
        SEARCH_CODE_MAP.put("rlsYn", "RLS_YN");
        SEARCH_CODE_MAP.put("ttl", "TTL");
        SEARCH_CODE_MAP.put("docContsCn", "DOC_CONTS_CN");
        SEARCH_CODE_MAP.put("clsfNo", "CLSF_NO");
        SEARCH_CODE_MAP.put("lwrkClsfSn", "LWRK_CLSF_SN");
        SEARCH_CODE_MAP.put("mainCn", "MAIN_CN");
        SEARCH_CODE_MAP.put("ansDtlCn", "ANS_DTL_CN");
        SEARCH_CODE_MAP.put("ansDt", "ANS_DT");
        SEARCH_CODE_MAP.put("file", "FILE_SN");
        SEARCH_CODE_MAP.put("deptCdId", "DEPT_CD_ID");
        SEARCH_CODE_MAP.put("deptNm", "DEPT_NM");
        SEARCH_CODE_MAP.put("picId", "PIC_ID");
        SEARCH_CODE_MAP.put("picNm", "PIC_NM");
        SEARCH_CODE_MAP.put("cpyrht", "CPYRHT_USE_YN");
        SEARCH_CODE_MAP.put("tagNms", "TAG_NM");
        SEARCH_CODE_MAP.put("inqCnt", "INQ_CNT");
        SEARCH_CODE_MAP.put("dclrCnt", "DCLR_CNT");
        SEARCH_CODE_MAP.put("rcmdtnCnt", "RCMDTN_CNT");
        SEARCH_CODE_MAP.put("stsfdgEvl", "DGSTFN_SUM_SCR");
        SEARCH_CODE_MAP.put("cntnBrwsrNm", "CNTN_BRWSR_NM");
        SEARCH_CODE_MAP.put("ipAddr", "IP_ADRES");
        SEARCH_CODE_MAP.put("userIdntfNm", "USER_IDNTF_NM");
        SEARCH_CODE_MAP.put("rgtrId", "RGTR_ID");
        SEARCH_CODE_MAP.put("rgtrNm", "RGTR_NM");
        SEARCH_CODE_MAP.put("regDt", "REG_DT");
        SEARCH_CODE_MAP.put("pswd", "PSWD");
        SEARCH_CODE_MAP.put("mdfrId", "MDFR_ID");
        SEARCH_CODE_MAP.put("updtDt", "UPDT_DT");
        SEARCH_CODE_MAP.put("flctnColCn1", "FLCTN_COL_CN1");
        SEARCH_CODE_MAP.put("flctnColCn2", "FLCTN_COL_CN2");
        SEARCH_CODE_MAP.put("flctnColCn3", "FLCTN_COL_CN3");
        SEARCH_CODE_MAP.put("flctnColCn4", "FLCTN_COL_CN4");
        SEARCH_CODE_MAP.put("flctnColCn5", "FLCTN_COL_CN5");
        SEARCH_CODE_MAP.put("flctnColCn6", "FLCTN_COL_CN6");
        SEARCH_CODE_MAP.put("flctnColCn7", "FLCTN_COL_CN7");
        SEARCH_CODE_MAP.put("flctnColCn8", "FLCTN_COL_CN8");
        SEARCH_CODE_MAP.put("flctnColCn9", "FLCTN_COL_CN9");
        SEARCH_CODE_MAP.put("flctnColCn10", "FLCTN_COL_CN10");
    }

    /**S
     * 설정에 따른 검색에 사용되는 키에 대응한 DB 컬럼명을 반환한다.
     * 
     * @param colId
     * @return
     */
    public static String getDBColumnNm(String colId) {

        return SEARCH_CODE_MAP.get(colId);
    }

}
