/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.file;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zesinc.component.file.domain.FileLogVO;
import zesinc.component.file.domain.FileVO;
import zesinc.component.file.support.UploadHelper;
import zesinc.web.spring.controller.BaseController;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.CompressUtil;
import zesinc.web.utils.MessageUtil;

/**
 * 파일 다운로드와 파일 정렬순서 및 파일 설명 수정 기능 제공
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 29.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller
@RequestMapping(value = "/**/component/file")
public class FileController extends BaseController {

    @Resource(name = "opFileService")
    private FileService service;

    /**
     * 에디터 파일 업로드
     */
    @RequestMapping(value = "ND_fileUpload.do")
    public String fileUpload(HttpServletResponse response, HttpServletRequest request, Model model) {

        List<FileVO> fileList = UploadHelper.upload(request, "ckeditor", Boolean.TRUE);

        String fileUrl = "";
        String funcNum = request.getParameter("CKEditorFuncNum");

        FileVO fileVo = null;
        if(fileList.size() > 0) {
            // service.insertFileList(-10, fileList);
            fileVo = fileList.get(0);
            fileUrl = fileVo.getFileUrlAddr();
        }

        String script = "parent.CKEDITOR.tools.callFunction('" + funcNum + "','" + fileUrl + "','업로드 되었습니다.');";

        return responseScript(model, script);
    }
    
    /**
     * froala 에디터 파일 업로드
     */
    @RequestMapping(value = "ND_fileFroalaUpload.do")
    public String fileFroalaUpload(HttpServletResponse response, HttpServletRequest request, Model model) {

        List<FileVO> fileList = UploadHelper.upload(request, "froala", Boolean.FALSE);

        String fileUrl = "";

        FileVO fileVo = null;
        if(fileList.size() > 0) {
            fileVo = fileList.get(0);
            fileUrl = fileVo.getFileUrlAddr();
        }
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("link", fileUrl);
        return responseJson(model, returnMap);
    }

    /**
     * 파일정보 수정 폼
     */
    @RequestMapping(value = "PD_fileUpdateForm.do")
    public String updateFileForm(HttpServletRequest request, Model model, FileVO fileVo) {

        List<FileVO> fileList = service.selectFileList(fileVo);

        model.addAttribute(BaseConfig.KEY_DATA_LIST, fileList);

        return "component/file/PD_fileUpdateForm";
    }

    /**
     * 파일 정보 수정
     */
    @RequestMapping(value = "ND_fileUpdate.do")
    public String updateFile(HttpServletRequest request, Model model, FileVO fileVo) {

        int updateCnt = service.updateFile(fileVo);

        if(updateCnt <= 0) {
            return responseJson(model, MessageUtil.FALSE, MessageUtil.getMessage("common.processFail"));
        }

        return responseJson(model, MessageUtil.TRUE, MessageUtil.getMessage("common.updateOk"));
    }

    /**
     * 파일 다운로드
     */
    @RequestMapping(value = "ND_fileDownload.do")
    public String fileDownload(HttpServletRequest request, Model model, FileVO fileVo) {

        FileVO dataVo = service.selectFile(fileVo);
        if(dataVo != null) {
            service.increaseFile(dataVo);
        }

        return responseDownload(model, dataVo);
    }

    /**
     * 파일 다운로드 (파일 URL 직접 호출)
     */
    @RequestMapping(value = "ND_directDownload.do")
    public String directDownload(HttpServletRequest request, Model model, FileVO fileVo) {

        String filePath = (String) fileVo.getParamMap().get("q_filePath");
        String fileName = (String) fileVo.getParamMap().get("q_fileName");

        // 보안문제를 발생시킬수 있는 파라이터가 있는 경우 강제 오류 발생
        if(filePath.indexOf("..") > -1 || filePath.indexOf("%") > -1 || filePath.indexOf("&") > -1
            || fileName.indexOf("..") > -1 || fileName.indexOf("%") > -1 || fileName.indexOf("&") > -1) {

            return responseDownload(model, new String());
        }

        File file = new File(BaseConfig.UPLOAD_ROOT + filePath);
        if(file.exists()) {
            fileVo.setOrgnlFileNm(fileName);
            fileVo.setFileUrlAddr(filePath);
        } else {
            fileVo.setOrgnlFileNm(StringUtils.EMPTY);
            fileVo.setFileUrlAddr(StringUtils.EMPTY);
        }

        return responseDownload(model, fileVo);
    }

    /**
     * 첨부파일들 압축 다운로드
     * 
     * @throws Exception
     */
    @RequestMapping("zipdownload.do")
    public Object zipdownload(HttpServletRequest request, Model model, FileVO fileVo) throws Exception {

        String fileSn = (String) fileVo.getParamMap().get("q_fileSn");
        List<FileVO> fileList = service.selectFileList(fileVo);

        String zipPath = BaseConfig.WEBAPP_ROOT + "zipdown";
        String zipFileName = "CompressDown_" + fileSn + ".zip";

        File zipFile = null;

        try {
            zipFile = CompressUtil.compressVo(fileList, zipPath, zipFileName);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

        for(FileVO dataVo : fileList) {
            service.increaseFile(dataVo);
        }

        return responseDownload(model, zipFile);
    }

    /**
     * 파일 다운로드 로그 목록 조회
     */
    @RequestMapping(value = "PD_fileLogList.do")
    public String fileLogList(HttpServletRequest request, Model model, FileLogVO fileLogVo) {

        model.addAttribute(BaseConfig.KEY_PAGER, service.selectFileLogList(fileLogVo));

        return "component/file/PD_fileLogList";
    }
}
