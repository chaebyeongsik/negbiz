/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo;

/**
 * 파일 관리 컴포넌트 VO 객체 인터페이스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 2. 2.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 */
public interface IFileVO {

    /**
     * String orgnlFileNm을 반환
     * 
     * @return String orgnlFileNm
     */
    String getOrgnlFileNm();

    /**
     * String srvrFileNm을 반환
     * 
     * @return String srvrFileNm
     */
    String getSrvrFileNm();

    /**
     * String fileExpln을 반환
     * 
     * @return String fileExpln
     */
    String getFileExpln();

    /**
     * String fileUrlAddr을 반환
     * 
     * @return String fileUrlAddr
     */
    String getFileUrlAddr();

    /**
     * String thmbPathNm을 반환
     * 
     * @return String thmbPathNm
     */
    public String getThmbPathNm();

    /**
     * String fileSzNm을 반환
     * 
     * @return String fileSzNm
     */
    String getFileSzNm();

    /**
     * byteFileSz 을 반환
     * 
     * @return
     */
    long getByteFileSz();

    /**
     * String fileTypeNm을 반환
     * 
     * @return String fileTypeNm
     */
    String getFileTypeNm();

    /**
     * 파일등록 폼 name 값
     * 
     * @return
     */
    String getInptDataNm();
}
