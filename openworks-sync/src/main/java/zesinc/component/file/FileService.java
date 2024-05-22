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
import zesinc.web.support.pager.Pager;

/**
 * 파일/파일로그 관리 서비스 정의 인터페이스
 * 
 * @author (주)제스아이엔씨 기술연구소
 * 
 *         <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 2. 5.    방기배   최초작성
 * </pre>
 * @see
 */
public interface FileService {

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
     * 일련번호에 해당하는 모든 파일 목록을 반환
     * 
     * @param fileSn 파일 일련번호
     * @return
     */
    List<FileVO> selectFileList(Integer fileSn);

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
     * 파일목록 저장. 파일일련번호는 자동으로 생성된다.
     * 
     * @param fileList 저장할 파일목록
     * @return 파일 일련번호
     */
    int insertFileList(List<FileVO> fileList);

    /**
     * 파일목록 저장. 파라미터의 파일 일련번호를 사용하여 파일을 저장한다.
     * 단! 파일 일련번호가 없다면 자동으로 생성한다.
     * 
     * @param fileSn 파일 일련번호
     * @param fileList 저장할 파일목록
     * @return 파일 일련번호
     */
    int insertFileList(Integer fileSn, List<FileVO> fileList);

    /**
     * 파일 수정 (수정은 파일에 대한 보조 정보만 수정한다.(예:파일설명)
     * 
     * @param fileVo 수정할 파일값이 포함된 객체
     * @return
     */
    int updateFile(FileVO fileVo);

    /**
     * 파일 일련번호에 해당하는 모든 파일을 삭제한다.
     * ParamMap에 q_fileSn 값이 자동설정된다.
     * 
     * @param fileVo 삭제할 파일키값이 포함된 객체
     * @return
     */
    int deleteFile(Integer fileSn);

    /**
     * 파일 일련번호와 파일ID에 해당하는 하나의 파일을 삭제한다.
     * ParamMap에 q_fileSn과 q_fileId 값이 자동설정된다.
     * 
     * @param fileVo 삭제할 파일키값이 포함된 객체
     * @return
     */
    int deleteFile(Integer fileSn, String fileId);

    /**
     * 파일 일련번호와 파일ID에 해당하는 파일 목록을 삭제한다.
     * ParamMap에 q_fileSn과 q_fileId 값이 자동설정된다.
     * 
     * @param fileVo 삭제할 파일키값이 포함된 객체
     * @return
     */
    int deleteFiles(Integer fileSn, String[] fileId);

    /**
     * 파일 정보에 해당하는 모든 파일을 삭제한다.<br/>
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
     * @return
     */
    int increaseFile(FileVO fileVo);

    /**
     * 파일별 파일 다운로드 이력 목록
     * 
     * @param fileLogVo 이력을 확인 할 파일 정보
     * @return
     */
    Pager<FileLogVO> selectFileLogList(FileLogVO fileLogVo);

}
