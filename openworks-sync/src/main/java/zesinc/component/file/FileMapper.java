/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.file;

import java.util.List;

import zesinc.component.file.domain.FileLogVO;
import zesinc.component.file.domain.FileVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 파일 관리 쿼리 메퍼 클레스
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
@Mapper("opFileDao")
public interface FileMapper {

    /**
     * 파일 일련번호를 생성한다.
     * 
     * @return
     */
    Integer selectFileSn();

    /**
     * 대상 파일 반환. 하나의 파일만 반환
     * 
     * @param fileVo q_fileId 값이 포함된 파일 객체
     * @return
     */
    FileVO selectFile(FileVO fileVo);

    /**
     * 일련번호에 해당하는 모든 파일 목록을 반환
     * 
     * @param fileVo q_fileSn 값이 포함된 파일 객체
     * @return
     */
    List<FileVO> selectFileList(FileVO fileVo);

    /**
     * 일련번호에 해당하는 모든 파일 목록의 갯수를 반환
     * 
     * @param fileVo q_fileSn 값이 포함된 파일 객체
     * @return
     */
    int selectListCount(FileVO fileVo);

    /**
     * 파일 저장
     * 
     * @param fileVo 저장할 파일값이 포함된 객체
     * @return
     */
    int insertFile(FileVO fileVo);

    /**
     * 파일 수정 (수정은 파일에 대한 보조 정보만 수정한다.(예:파일설명)
     * 
     * @param fileVo 수정할 파일값이 포함된 객체
     * @return
     */
    int updateFile(FileVO fileVo);

    /**
     * 파일 일련번호에 해당하는 모든 파일을 삭제한다.<br/>
     * q_fileSn 값만 존재하는 경우 일련번호에 해당하는 모든 파일을 삭제한다.
     * q_fileId 값이 존재하는 경우 해당 키에 해당하는 파일 하나만 삭제한다.
     * 
     * @param fileVo 삭제할 파일키값이 포함된 객체
     * @return
     */
    int deleteFile(FileVO fileVo);

    /**
     * 파일조회수를 증가시킨다.
     * 
     * @param fileVo 웹 요청 객체
     * @param fileId 첨부파일 고유 ID
     * @return
     */
    int increaseFile(FileVO fileVo);

    /**
     * 파일 다운로드 이력 등록
     * 
     * @param fileLogVo
     * @return
     */
    int insertFileLog(FileLogVO fileLogVo);

    /**
     * 파일별 파일 다운로드 이력 목록
     * 
     * @param fileLogVo 이력을 확인 할 파일 정보
     * @return
     */
    List<FileLogVO> selectFileLogList(FileLogVO fileLogVo);

    /**
     * 파일별 파일 다운로드 이력 목록 갯수
     * 
     * @param fileLogVo 이력을 확인 할 파일 정보
     * @return
     */
    int selectFileLogListCount(FileLogVO fileLogVo);

    /**
     * 파일 다운로드 이력 삭제
     * 
     * @param fileVo
     * @return
     */
    int deleteFileLog(FileVO fileVo);
}
