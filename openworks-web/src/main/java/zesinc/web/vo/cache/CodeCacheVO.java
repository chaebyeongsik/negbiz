/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo.cache;

import java.util.List;
import java.util.Map;

import zesinc.web.vo.BaseVO;

/**
 * 코드 정보 VO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-03-27.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CodeCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = 1401997881527420493L;

    /** 코드 */
    private String cdId;

    /** 코드명 */
    private String cdNm;

    /** 최상위코드 */
    private String hghrkCdId;

    /** 상위코드 */
    private String upCdId;

    /** 상위코드명 */
    private String upCdIdNm;

    /** 코드선택 */
    private String cdChcId;

    /** 하위코드 */
    private String lwrkCdId;

    /** 하위코드명 */
    private String lwrkCdIdNm;

    /** 다국어코드 */
    private String mtlngCdNm;

    /** 코드설명 */
    private String cdExpln;

    /** 정렬순서 */
    private Integer sortSn;

    /** 행정표준코드 */
    private String pbadmsStdCdId;

    /** 행정표준코드여부 */
    private String pbadmsStdCdYn;

    /** 자식 존재여부 */
    private Boolean hasChild;

    /** 코드선택자명으로 각각의 목록을 저장 */
    private Map<String, List<CodeCacheVO>> cdChcListId;

    /**
     * 코드을 설정
     * 
     * @param code을(를) String code로 설정
     */
    public void setCdId(String cdId) {
        this.cdId = cdId;
    }

    /**
     * 코드을 반환
     * 
     * @return String cdId
     */
    public String getCdId() {
        return cdId;
    }

    /**
     * 코드명을 설정
     * 
     * @param cdNm을(를) String cdNm로 설정
     */
    public void setCdNm(String cdNm) {
        this.cdNm = cdNm;
    }

    /**
     * 코드명을 반환
     * 
     * @return String cdNm
     */
    public String getCdNm() {
        return cdNm;
    }

    /**
     * String hghrkCdId을 반환
     * 
     * @return String hghrkCdId
     */
    public String getHghrkCdId() {
        return hghrkCdId;
    }

    /**
     * hghrkCdId을 설정
     * 
     * @param hghrkCdId 을(를) String hghrkCdId로 설정
     */
    public void setHghrkCdId(String hghrkCdId) {
        this.hghrkCdId = hghrkCdId;
    }

    /**
     * 상위코드을 설정
     * 
     * @param upCdId을(를) String upCdId로 설정
     */
    public void setUpCdId(String upCdId) {
        this.upCdId = upCdId;
    }

    /**
     * 상위코드을 반환
     * 
     * @return String upCdId
     */
    public String getUpCdId() {
        return upCdId;
    }

    /**
     * 상위코드명을 설정
     * 
     * @param upCdIdNm을(를) String upCdIdNm로 설정
     */
    public void setUpCdIdNm(String upCdIdNm) {
        this.upCdIdNm = upCdIdNm;
    }

    /**
     * 상위코드명을 반환
     * 
     * @return String upCdIdNm
     */
    public String getUpCdIdNm() {
        return upCdIdNm;
    }

    /**
     * String codeChoice을 반환
     * 
     * @return String cdChcId
     */
    public String getCdChcId() {
        return cdChcId;
    }

    /**
     * codeChoice을 설정
     * 
     * @param cdChcId 을(를) String codeChoice로 설정
     */
    public void setCdChcId(String cdChcId) {
        this.cdChcId = cdChcId;
    }

    /**
     * String lwrkCdId을 반환
     * 
     * @return String lwrkCdId
     */
    public String getLwrkCdId() {
        return lwrkCdId;
    }

    /**
     * lwrkCdId을 설정
     * 
     * @param lwrkCdId 을(를) String lwrkCdId로 설정
     */
    public void setLwrkCdId(String lwrkCdId) {
        this.lwrkCdId = lwrkCdId;
    }

    /**
     * String lwrkCdIdNm을 반환
     * 
     * @return String lwrkCdIdNm
     */
    public String getLwrkCdIdNm() {
        return lwrkCdIdNm;
    }

    /**
     * lwrkCdIdNm을 설정
     * 
     * @param lwrkCdIdNm 을(를) String lwrkCdIdNm로 설정
     */
    public void setLwrkCdIdNm(String lwrkCdIdNm) {
        this.lwrkCdIdNm = lwrkCdIdNm;
    }

    /**
     * 다국어코드을 설정
     * 
     * @param mtlngCdNm을(를) String mtlngCdNm로 설정
     */
    public void setMtlngCdNm(String mtlngCdNm) {
        this.mtlngCdNm = mtlngCdNm;
    }

    /**
     * 다국어코드을 반환
     * 
     * @return String mtlngCdNm
     */
    public String getMtlngCdNm() {
        return mtlngCdNm;
    }

    /**
     * 코드설명을 설정
     * 
     * @param cdExpln을(를) String cdExpln로 설정
     */
    public void setCdExpln(String cdExpln) {
        this.cdExpln = cdExpln;
    }

    /**
     * 코드설명을 반환
     * 
     * @return String cdExpln
     */
    public String getCdExpln() {
        return cdExpln;
    }

    /**
     * 정렬순서을 설정
     * 
     * @param sortSn을(를) Integer sortSn로 설정
     */
    public void setSortSn(Integer sortSn) {
        this.sortSn = sortSn;
    }

    /**
     * 정렬순서을 반환
     * 
     * @return Integer sortSn
     */
    public Integer getSortSn() {
        return sortSn;
    }

    /**
     * 행정표준코드 반환
     * 
     * @return String pbadmsStdCdId
     */
    public String getPbadmsStdCdId() {
        return pbadmsStdCdId;
    }

    /**
     * 행정표준코드 설정
     * 
     * @param pbadmsStdCdId 을(를) String pbadmsStdCdId로 설정
     */
    public void setPbadmsStdCdId(String pbadmsStdCdId) {
        this.pbadmsStdCdId = pbadmsStdCdId;
    }

    /**
     * 행정표준코드여부 반환
     * 
     * @return String pbadmsStdCdYn
     */
    public String getPbadmsStdCdYn() {
        return pbadmsStdCdYn;
    }

    /**
     * 행정표준코드여부 설정
     * 
     * @param pbadmsStdCdYn 을(를) String pbadmsStdCdYn로 설정
     */
    public void setPbadmsStdCdYn(String pbadmsStdCdYn) {
        this.pbadmsStdCdYn = pbadmsStdCdYn;
    }

    /**
     * Boolean hasChild을 반환
     * 
     * @return Boolean hasChild
     */
    public Boolean getHasChild() {
        return hasChild;
    }

    /**
     * hasChild을 설정
     * 
     * @param hasChild 을(를) Boolean hasChild로 설정
     */
    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    /**
     * Map<String,List<CodeCacheVO>> cdChcListId을 반환
     * 
     * @return Map<String,List<CodeCacheVO>> cdChcListId
     */
    public Map<String, List<CodeCacheVO>> getCdChcListId() {
        return cdChcListId;
    }

    /**
     * cdChcListId을 설정
     * 
     * @param cdChcListId 을(를) Map<String,List<CodeCacheVO>> cdChcListId로
     *        설정
     */
    public void setCdChcListId(Map<String, List<CodeCacheVO>> cdChcListId) {
        this.cdChcListId = cdChcListId;
    }

}
