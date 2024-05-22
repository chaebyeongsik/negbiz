/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.file.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zesinc.component.file.FileMapper;
import zesinc.component.file.FileService;
import zesinc.component.file.domain.FileLogVO;
import zesinc.component.file.domain.FileVO;
import zesinc.component.file.support.DevFileUtil;
import zesinc.component.file.support.UploadHelper;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.support.pager.Pager;
import zesinc.web.vo.ISessVO;
import zesinc.web.vo.IUserSessVO;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 파일관리 서비스 구현 객체
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 2. 5.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Service("opFileService")
public class FileServiceImpl extends EgovAbstractServiceImpl implements FileService {

    @Resource(name = "opFileDao")
    private FileMapper opFileDao;

    @Override
    public FileVO selectFile(FileVO fileVo) {

        return opFileDao.selectFile(fileVo);
    }

    @Override
    public List<FileVO> selectFileList(Integer fileSn) {
        FileVO fileVo = new FileVO();
        fileVo.getParamMap().put("q_fileSn", fileSn);

        return selectFileList(fileVo);
    }

    @Override
    public List<FileVO> selectFileList(FileVO fileVo) {

        return opFileDao.selectFileList(fileVo);
    }

    @Override
    public int selectListCount(FileVO fileVo) {

        return opFileDao.selectListCount(fileVo);
    }

    @Override
    public int insertFile(FileVO fileVo) {
        Integer fileSn = fileVo.getFileSn();

        if(Validate.isEmpty(fileSn)) {
            fileSn = opFileDao.selectFileSn();
            fileVo.setFileSn(fileSn);
        }
        opFileDao.insertFile(fileVo);

        return fileSn;
    }

    @Override
    public int insertFileList(List<FileVO> fileList) {

        return insertFileList(null, fileList);
    }

    @Override
    public int insertFileList(Integer fileSn, List<FileVO> fileList) {

        if(Validate.isNotEmpty(fileList)) {
            if(Validate.isEmpty(fileSn) || fileSn < 0) {
                fileSn = opFileDao.selectFileSn();
            }

            for(FileVO fileVo : fileList) {
                fileVo.setFileSn(fileSn);
                opFileDao.insertFile(fileVo);
            }
        }

        return fileSn;
    }

    @Override
    public int updateFile(FileVO fileVo) {
        int updateCnt = 0;

        Integer[] fileSns = fileVo.getIntegers("q_fileSn");
        String[] fileIds = fileVo.getStrings("q_fileId");
        String[] orgnlFileNms = fileVo.getStrings("q_orgnlFileNm");
        String[] fileExplns = fileVo.getStrings("q_fileExpln");
        Integer[] sortSns = fileVo.getIntegers("q_sortSn");

        FileVO dataVo;
        int fileCnt = fileIds.length;
        for(int i = 0 ; i < fileCnt ; i++) {
            dataVo = new FileVO();
            dataVo.setFileSn(fileSns[i]);
            dataVo.setFileId(fileIds[i]);
            dataVo.setOrgnlFileNm(orgnlFileNms[i]);
            if(Validate.isNotEmpty(fileExplns)) {
                dataVo.setFileExpln(fileExplns[i]);
            } else {
                dataVo.setFileExpln("");
            }
            dataVo.setSortSn(sortSns[i]);

            updateCnt += opFileDao.updateFile(dataVo);
        }

        return updateCnt;
    }

    @Override
    public int deleteFile(Integer fileSn) {
        FileVO fileVo = new FileVO();

        fileVo.getParamMap().put("q_fileSn", fileSn);

        return deleteFile(fileVo);
    }

    @Override
    public int deleteFile(Integer fileSn, String fileId) {
        FileVO fileVo = new FileVO();

        fileVo.getParamMap().put("q_fileSn", fileSn);
        fileVo.getParamMap().put("q_fileId", fileId);

        return deleteFile(fileVo);
    }

    @Override
    public int deleteFiles(Integer fileSn, String[] fileIds) {

        int delectCnt = 0;
        for(int i = 0 ; i < fileIds.length ; i++) {
            delectCnt += deleteFile(fileSn, fileIds[i]);
        }

        return delectCnt;
    }

    @Override
    public int deleteFile(FileVO fileVo) {
        int deleteCnt = 0;
        List<FileVO> fileList = opFileDao.selectFileList(fileVo);

        String uploadRoot = UploadHelper.getRootPath(false);
        String resourceRoot = UploadHelper.getRootPath(true);

        for(FileVO dataVo : fileList) {

            String rootPath;
            if(dataVo.getDataYn().equals("N")) {
                rootPath = uploadRoot;
            } else {
                rootPath = resourceRoot;
            }

            File file = new File(rootPath + dataVo.getFileUrlAddr());
            if(file.exists() && file.isFile()) {
                file.delete();
                // 개발 설정 삭제
                DevFileUtil.deleteDevPathFile(dataVo.getFileUrlAddr());
            }

            if(Validate.isNotEmpty(dataVo.getThmbPathNm())) {
                File thumb = new File(resourceRoot + dataVo.getThmbPathNm());
                if(thumb.exists() && thumb.isFile()) {
                    thumb.delete();
                    // 개발 설정 삭제
                    DevFileUtil.deleteDevPathFile(dataVo.getThmbPathNm());
                }
            }

            opFileDao.deleteFileLog(dataVo);
            deleteCnt += opFileDao.deleteFile(dataVo);
        }

        return deleteCnt;
    }

    @Override
    public int increaseFile(FileVO fileVo) {

        FileLogVO fileLogVo = new FileLogVO();
        fileLogVo.setFileSn(fileVo.getFileSn());
        fileLogVo.setFileId(fileVo.getFileId());

        if(BaseConfig.SYSTEM_KIND.equals("intra")) {
            ISessVO loginVo = (ISessVO) OpHelper.getMgrSession();
            if(Validate.isNotEmpty(loginVo)) {
                fileLogVo.setOprtrNm(loginVo.getFullname() + " (" + loginVo.getUsername() + ")");
            } else {
                fileLogVo.setOprtrNm("관리자:알수없음");
            }
        } else {
            IUserSessVO loginVo = (IUserSessVO) OpHelper.getUserSession();
            if(Validate.isNotEmpty(loginVo)) {
                fileLogVo.setOprtrNm(loginVo.getUserNm() + " (" + loginVo.getUserId() + ")");
            } else {
                fileLogVo.setOprtrNm("사용자:알수없음");
            }
        }
        opFileDao.insertFileLog(fileLogVo);

        return opFileDao.increaseFile(fileVo);
    }

    @Override
    public Pager<FileLogVO> selectFileLogList(FileLogVO fileLogVo) {
        List<FileLogVO> dataList = opFileDao.selectFileLogList(fileLogVo);
        Integer totalNum = opFileDao.selectFileLogListCount(fileLogVo);

        return new Pager<FileLogVO>(dataList, fileLogVo, totalNum);
    }

}
