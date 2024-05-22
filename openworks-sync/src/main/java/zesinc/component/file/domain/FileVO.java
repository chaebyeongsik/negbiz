/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.file.domain;

import java.io.File;

import zesinc.web.vo.BaseVO;
import zesinc.web.vo.IFileVO;

/**
 * 파일 관리 컴포넌트 VO 객체
 * 
 * @author (주)제스아이엔씨 기술연구소
 * 
 *         <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 2. 2.    방기배   최초작성
 * </pre>
 * @see
 */
public class FileVO extends BaseVO implements IFileVO {

    /** serialVersionUID */
    private static final long serialVersionUID = 7540099562233221558L;
    /** 파일순번 */
    private Integer fileSn;
    /** 파일ID */
    private String fileId;
    /** 정렬순서 */
    private Integer sortSn;
    /** 원본파일명 */
    private String orgnlFileNm;
    /** 서버파일명 */
    private String srvrFileNm;
    /** 파일경로웹 */
    private String fileUrlAddr;
    /** 썸네일경로 */
    private String thmbPathNm;
    /** 파일사이즈 */
    private String fileSzNm;
    /** 파일유형 */
    private String fileTypeNm;
    /** 파일확장자 */
    private String fileExtnNm;
    /** 파일설명 */
    private String fileExpln;
    /** 입력변수이름 */
    private String inptDataNm;
    /** 다운로드수 */
    private Integer dwnldCnt;
    /** 파일바이트사이즈 */
    private long byteFileSz;
    /** 리소스파일여부 */
    private String dataYn;
    /** 등록자ID */
    private String rgtrId;
    /** 등록일시 */
    private String regDt;

    /** 파일순번들 */
    private Integer[] fileSns;
    /** 파일ID들 */
    private String[] fileIds;
    /** 정렬순서들 */
    private Integer[] sortSns;
    /** 원본파일명들 */
    private String[] orgnlFileNms;

    /** 업로드된 파일객체 */
    private File file;

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
     * String orgnlFileNm을 반환
     * 
     * @return String orgnlFileNm
     */
    @Override
    public String getOrgnlFileNm() {
        return orgnlFileNm;
    }

    /**
     * orgnlFileNm을 설정
     * 
     * @param orgnlFileNm 을(를) String orgnlFileNm로 설정
     */
    public void setOrgnlFileNm(String orgnlFileNm) {
        this.orgnlFileNm = orgnlFileNm;
    }

    /**
     * String srvrFileNm을 반환
     * 
     * @return String srvrFileNm
     */
    @Override
    public String getSrvrFileNm() {
        return srvrFileNm;
    }

    /**
     * srvrFileNm을 설정
     * 
     * @param srvrFileNm 을(를) String srvrFileNm로 설정
     */
    public void setSrvrFileNm(String srvrFileNm) {
        this.srvrFileNm = srvrFileNm;
    }

    /**
     * String fileUrlAddr을 반환
     * 
     * @return String fileUrlAddr
     */
    @Override
    public String getFileUrlAddr() {
        return fileUrlAddr;
    }

    /**
     * fileUrlAddr을 설정
     * 
     * @param fileUrlAddr 을(를) String fileUrlAddr로 설정
     */
    public void setFileUrlAddr(String fileUrlAddr) {
        this.fileUrlAddr = fileUrlAddr;
    }

    /**
     * String thmbPathNm을 반환
     * 
     * @return String thmbPathNm
     */
    public String getThmbPathNm() {
        return thmbPathNm;
    }

    /**
     * thmbPathNm을 설정
     * 
     * @param thmbPathNm 을(를) String thmbPathNm로 설정
     */
    public void setThmbPathNm(String thmbPathNm) {
        this.thmbPathNm = thmbPathNm;
    }

    /**
     * String fileSzNm을 반환
     * 
     * @return String fileSzNm
     */
    @Override
    public String getFileSzNm() {
        return fileSzNm;
    }

    /**
     * fileSzNm을 설정
     * 
     * @param fileSzNm 을(를) String fileSzNm로 설정
     */
    public void setFileSzNm(String fileSzNm) {
        this.fileSzNm = fileSzNm;
    }

    /**
     * String fileTypeNm을 반환
     * 
     * @return String fileTypeNm
     */
    @Override
    public String getFileTypeNm() {
        return fileTypeNm;
    }

    /**
     * fileTypeNm을 설정
     * 
     * @param fileTypeNm 을(를) String fileTypeNm로 설정
     */
    public void setFileTypeNm(String fileTypeNm) {
        this.fileTypeNm = fileTypeNm;
    }

    /**
     * String fileExtnNm을 반환
     * 
     * @return String fileExtnNm
     */
    public String getFileExtnNm() {
        return fileExtnNm;
    }

    /**
     * fileExtnNm을 설정
     * 
     * @param fileExtnNm 을(를) String fileExtnNm로 설정
     */
    public void setFileExtnNm(String fileExtnNm) {
        this.fileExtnNm = fileExtnNm;
    }

    /**
     * String fileExpln을 반환
     * 
     * @return String fileExpln
     */
    public String getFileExpln() {
        return fileExpln;
    }

    /**
     * fileExpln을 설정
     * 
     * @param fileExpln 을(를) String fileExpln로 설정
     */
    public void setFileExpln(String fileExpln) {
        this.fileExpln = fileExpln;
    }

    /**
     * String inptDataNm을 반환
     * 
     * @return String inptDataNm
     */
    public String getInptDataNm() {
        return inptDataNm;
    }

    /**
     * inptDataNm을 설정
     * 
     * @param inptDataNm 을(를) String inptDataNm로 설정
     */
    public void setInptDataNm(String inptDataNm) {
        this.inptDataNm = inptDataNm;
    }

    /**
     * Integer dwnldCnt을 반환
     * 
     * @return Integer dwnldCnt
     */
    public Integer getDwnldCnt() {
        return dwnldCnt;
    }

    /**
     * dwnldCnt을 설정
     * 
     * @param dwnldCnt 을(를) Integer dwnldCnt로 설정
     */
    public void setDwnldCnt(Integer dwnldCnt) {
        this.dwnldCnt = dwnldCnt;
    }

    /**
     * long byteFileSz을 반환
     * 
     * @return long byteFileSz
     */
    public long getByteFileSz() {
        return byteFileSz;
    }

    /**
     * byteFileSz을 설정
     * 
     * @param byteFileSz 을(를) long byteFileSz로 설정
     */
    public void setByteFileSz(long byteFileSz) {
        this.byteFileSz = byteFileSz;
    }

    /**
     * String dataYn을 반환
     * 
     * @return String dataYn
     */
    public String getDataYn() {
        return dataYn;
    }

    /**
     * dataYn을 설정
     * 
     * @param dataYn 을(를) String dataYn로 설정
     */
    public void setDataYn(String dataYn) {
        this.dataYn = dataYn;
    }

    /**
     * String rgtrId을 반환
     * 
     * @return String rgtrId
     */
    public String getRgtrId() {
        return rgtrId;
    }

    /**
     * rgtrId을 설정
     * 
     * @param rgtrId 을(를) String rgtrId로 설정
     */
    public void setRgtrId(String rgtrId) {
        this.rgtrId = rgtrId;
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

    /**
     * Integer[] fileSns을 반환
     * 
     * @return Integer[] fileSns
     */
    public Integer[] getFileSns() {
        return fileSns;
    }

    /**
     * fileSns을 설정
     * 
     * @param fileSns 을(를) Integer[] fileSns로 설정
     */
    public void setFileSns(Integer[] fileSns) {
        this.fileSns = fileSns;
    }

    /**
     * String[] fileIds을 반환
     * 
     * @return String[] fileIds
     */
    public String[] getFileIds() {
        return fileIds;
    }

    /**
     * fileIds을 설정
     * 
     * @param fileIds 을(를) String[] fileIds로 설정
     */
    public void setFileIds(String[] fileIds) {
        this.fileIds = fileIds;
    }

    /**
     * Integer[] sortSns을 반환
     * 
     * @return Integer[] sortSns
     */
    public Integer[] getSortSns() {
        return sortSns;
    }

    /**
     * sortSns을 설정
     * 
     * @param sortSns 을(를) Integer[] sortSns로 설정
     */
    public void setSortSns(Integer[] sortSns) {
        this.sortSns = sortSns;
    }

    /**
     * String[] orgnlFileNms을 반환
     * 
     * @return String[] orgnlFileNms
     */
    public String[] getOrgnlFileNms() {
        return orgnlFileNms;
    }

    /**
     * orgnlFileNms을 설정
     * 
     * @param orgnlFileNms 을(를) String[] orgnlFileNms로 설정
     */
    public void setOrgnlFileNms(String[] orgnlFileNms) {
        this.orgnlFileNms = orgnlFileNms;
    }

    /**
     * File file을 반환
     * 
     * @return File file
     */
    public File getFile() {
        return file;
    }

    /**
     * file을 설정
     * 
     * @param file 을(를) File file로 설정
     */
    public void setFile(File file) {
        this.file = file;
    }

}
