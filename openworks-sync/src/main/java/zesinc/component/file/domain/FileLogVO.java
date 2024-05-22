/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.file.domain;

import zesinc.web.vo.PageVO;

/**
 * @author (주)제스아이엔씨 기술연구소
 * 
 *         <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 2. 3.    방기배   최초작성
 * </pre>
 * @see
 */
public class FileLogVO extends PageVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -1702392001735844277L;
    /** 파일순번 */
    private Integer fileSn;
    /** 파일ID */
    private String fileId;
    /** 정렬순서 */
    private Integer sortSn;
    /** 작업자이름 */
    private String oprtrNm;
    /** 등록일시 */
    private String regDt;

    /**
     * Integer fileSn을 반환
     * 
     * @return Integer fileSn
     */
    public Integer getFileSn() {
        return fileSn;
    }

    /**
     * fileSn을 설정
     * 
     * @param fileSn 을(를) Integer fileSn로 설정
     */
    public void setFileSn(Integer fileSn) {
        this.fileSn = fileSn;
    }

    /**
     * String fileId을 반환
     * 
     * @return String fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * fileId을 설정
     * 
     * @param fileId 을(를) String fileId로 설정
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * Integer sortSn을 반환
     * 
     * @return Integer sortSn
     */
    public Integer getSortSn() {
        return sortSn;
    }

    /**
     * sortSn을 설정
     * 
     * @param sortSn 을(를) Integer sortSn로 설정
     */
    public void setSortSn(Integer sortSn) {
        this.sortSn = sortSn;
    }

    /**
     * String oprtrNm을 반환
     * 
     * @return String oprtrNm
     */
    public String getOprtrNm() {
        return oprtrNm;
    }

    /**
     * oprtrNm을 설정
     * 
     * @param oprtrNm 을(를) String oprtrNm로 설정
     */
    public void setOprtrNm(String oprtrNm) {
        this.oprtrNm = oprtrNm;
    }

    /**
     * String regDt을 반환
     * 
     * @return String regDt
     */
    public String getRegDt() {
        return regDt;
    }

    /**
     * regDt을 설정
     * 
     * @param regDt 을(를) String regDt로 설정
     */
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

}
