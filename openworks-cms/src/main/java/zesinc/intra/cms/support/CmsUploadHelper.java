/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import zesinc.component.file.domain.FileVO;
import zesinc.intra.cms.base.domain.CmsVO;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.FileUtil;

/**
 * CMS 전용 파일 업로드 도움 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 2. 7.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsUploadHelper {

    /** Contrroller 로깅 */
    private static Logger logger = LoggerFactory.getLogger(CmsUploadHelper.class);
    /** 첨부 파일 저장 경로 */
    private static final String MENU_FILE_ROOT = CmsSupport.MENU_FILE_ROOT;
    /** WAS 서버의 Root 경로 */
    public static String WAS_ROOT = BaseConfig.WEBAPP_ROOT;

    /**
     * CMS 메뉴 정보를 통하여 파일을 생성한 후 파일 정보를 반환한다.
     * 이때 개발 모드 설정 상태에서는 설정된 경로에 추가로 파일을 생성한다.(예 : 소스파일 경로)
     * 
     * @param request Http 요청 객체
     * @param cmsVo CMS 메뉴 정보
     */
    public static List<FileVO> upload(HttpServletRequest request, CmsVO cmsVo) {

        if(!MultipartHttpServletRequest.class.isAssignableFrom(request.getClass())) {
            return new ArrayList<FileVO>();
        }

        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

        // 운영경로
        String realModePath = WAS_ROOT + MENU_FILE_ROOT + cmsVo.getSiteSn() + "/" + cmsVo.getUserMenuEngNm();
        // 로컬개발경로
        String webPath = MENU_FILE_ROOT + cmsVo.getSiteSn() + "/" + cmsVo.getUserMenuEngNm();

        File parent = new File(realModePath);
        if(!parent.isDirectory()) {
            FileUtil.mkdirs(parent);
            parent.setWritable(true);
        }

        FileVO fileVo;
        List<FileVO> fileList = new ArrayList<FileVO>();
        List<String> extList = Arrays.asList(BaseConfig.IMAGE_FILE_EXTS);

        List<MultipartFile> multiList = new ArrayList<MultipartFile>();
        Iterator<String> fileNames = multiRequest.getFileNames();
        while(fileNames.hasNext()) {
            multiList.addAll(multiRequest.getFiles(fileNames.next()));
        }

        try {

            int fileCnt = multiList.size();
            for(int i = 0 ; i < fileCnt ; i++) {

                MultipartFile multiFile = multiList.get(i);
                if(!multiFile.isEmpty()) {

                    String uuid = UUID.randomUUID().toString();
                    String ext = FileUtil.extension(multiFile.getOriginalFilename());

                    // 허용불가 첨부파일 유형인 경우 업로드 된 파일을 무시하며, 결과에도 포함하지 않는다.
                    if(!extList.contains(ext)) {
                        multiFile.getInputStream().close();
                        continue;
                    }

                    String newFileName = renameTo(multiFile, cmsVo);
                    File newFileObject = new File(realModePath, newFileName);

                    multiFile.transferTo(newFileObject);

                    fileVo = new FileVO();
                    fileVo.setFile(newFileObject);
                    fileVo.setFileUrlAddr(FileUtil.getContextPath(WAS_ROOT, newFileObject.getAbsolutePath()));
                    fileVo.setInptDataNm(multiFile.getName());
                    fileVo.setSrvrFileNm(newFileObject.getName());
                    fileVo.setOrgnlFileNm(multiFile.getOriginalFilename());
                    fileVo.setFileTypeNm(multiFile.getContentType());
                    fileVo.setFileSzNm(FileUtil.toDisplaySize(multiFile.getSize()));
                    fileVo.setByteFileSz(multiFile.getSize());
                    fileVo.setFileId(uuid);
                    fileVo.setFileExtnNm(ext);

                    fileList.add(fileVo);

                    // 사용자단 서버 경로에 파일을 같이 올린다.
                    PublishUtil.publishImage(newFileObject, webPath, newFileName);
                }
            }
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }

        return fileList;
    }

    /**
     * 저장 파일명. CMS는 메뉴 코드가 기본이므로 업로드되는 파일을 메뉴 코드명으로 설정하여 생성
     * 
     * @param multi MultipartFile
     * @param cmsVo CmsVO
     * @return
     */
    private static String renameTo(MultipartFile multi, CmsVO cmsVo) {
        String cmsFileName = "";
        String fileName = multi.getOriginalFilename();
        String fileExtension = FileUtil.extension(fileName);
        String menuSn = cmsVo.getUserMenuEngNm();

        if("sjImageFile".equals(multi.getName())) {
            cmsFileName = "title_" + menuSn + "." + fileExtension;
        } else if("menuTtlImgNmFile".equals(multi.getName())) {
            cmsFileName = "menu_title_" + menuSn + "." + fileExtension;
        } else if("menuOnImageFile".equals(multi.getName())) {
            cmsFileName = "menu_on_" + menuSn + "." + fileExtension;
        } else if("menuOffImageFile".equals(multi.getName())) {
            cmsFileName = "menu_off_" + menuSn + "." + fileExtension;
        } else if("cssFileTmp".equals(multi.getName())) {
            cmsFileName = "css_" + menuSn + "." + fileExtension;
        } else {
            cmsFileName = UUID.randomUUID() + "." + fileExtension;
        }

        return cmsFileName;
    }

}
