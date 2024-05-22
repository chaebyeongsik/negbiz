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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.component.file.support.DevFileUtil;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.intra.cms.cntnts.domain.CmsCntntsVO;
import zesinc.intra.cms.layout.domain.CmsLayoutVO;
import zesinc.intra.cms.meta.domain.CmsMetaVO;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.FileUtil;

/**
 * CMS 관련 컨텐츠 출판 유틸 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 5. 14.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class PublishUtil {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(PublishUtil.class);

    /** WAS 서버의 Root 경로 */
    public static String WAS_ROOT = BaseConfig.WEBAPP_ROOT;
    /** 개발환경 소스파일 Root 경로 */
    public static List<String> PROGRAM_PATH = BaseConfig.PROGRAM_PATH_LIST;
    /** 개발환경 소스파일 Root 경로 */
    public static List<String> DEV_PATH = BaseConfig.DEV_PATH_LIST;
    /** jsp 파일 생성시 첫 라인에 정의되어야 할 코드 */
    private static final String HEADER = "<%@ page contentType=\"text/html;charset=utf-8\" %>\n";
    /** 파일인코딩 케릭터셋 */
    private static final String FILE_ENCODING = Config.getString("system-config.defaultCharset");

    /**
     * 이미지 및 CSS 파일을 사용자 서버 경로에 동시에 생성
     * 
     * @param sourceFile
     * @param excludeRootPath
     * @param fileName
     * @return
     */
    public static Boolean publishImage(File sourceFile, String excludeRootPath, String fileName) {
        String fullPath = "";
        if(Validate.isNotEmpty(PROGRAM_PATH)) {
            for(String devPath : PROGRAM_PATH) {
                fullPath = devPath;
                if(!fullPath.endsWith("/")) {
                    fullPath += "/";
                }
                fullPath += excludeRootPath;

                File copyFile = new File(fullPath, fileName);
                File parent = copyFile.getParentFile();
                if(!parent.exists()) {
                    FileUtil.mkdirs(parent);
                }
                FileUtil.copy(sourceFile, copyFile);
            }
        }

        // 개발설정 파일 생성
        DevFileUtil.makeDevPathFile(sourceFile, excludeRootPath, fileName);

        return Boolean.TRUE;
    }

    /**
     * 업로드 콘트롤을 통해 업로드된 파일을 삭제한다.
     * 
     * @param filePath 문자열
     */
    public static void deleteFile(String filePath) {

        String fullPath = WAS_ROOT;

        File file;
        if(Validate.isNotEmpty(filePath)) {
            String strFile = fullPath + filePath;
            strFile.replace("/", File.separator);

            file = new File(strFile);
            if(file.isFile()) {
                file.delete();
                logger.debug("delete file is {}", file.getAbsolutePath());
            }

            if(Validate.isNotEmpty(PROGRAM_PATH)) {
                for(String programPath : PROGRAM_PATH) {
                    fullPath = programPath;
                    if(!fullPath.endsWith("/")) {
                        fullPath += "/";
                    }
                    fullPath += filePath;

                    file = new File(fullPath);
                    if(file.isFile()) {
                        file.delete();
                        logger.debug("delete file is {}", file.getAbsolutePath());
                    }
                }
            }

            // 개발 모드인경우 서버 Plugin ROOT 이외에 로컬 소스 경로(Eclipse)에도 파일을 삭제한다.
            DevFileUtil.deleteDevPathFile(filePath);
        }
    }

    /**
     * 레이아웃 퍼블리싱
     * 
     * @param cmsLayoutVo
     * @return
     */
    public static Boolean publishLayout(CmsLayoutVO cmsLayoutVo) {

        String fullPath = "";

        String content = cmsLayoutVo.getLytContsCn();
        String path = cmsLayoutVo.getFilePathNm();

        // 설정에 동시 저장 경로 목록이 있는 경우
        if(Validate.isNotEmpty(PROGRAM_PATH)) {
            for(String devPath : PROGRAM_PATH) {
                fullPath = devPath;
                if(!fullPath.endsWith("/")) {
                    fullPath += "/";
                }
                fullPath += path;

                File file = new File(fullPath);
                publish(file, content, false);
            }
        }

        // 개발 모드인경우 서버 Plugin ROOT 이외에 로컬 소스 경로(Eclipse)에도 파일을 생성한다.
        if(BaseConfig.PRO_MODE && Validate.isNotEmpty(DEV_PATH)) {
            for(String devPath : DEV_PATH) {
                fullPath = devPath;
                if(!fullPath.endsWith("/")) {
                    fullPath += "/";
                }
                fullPath += path;

                File file = new File(fullPath);
                publish(file, content, false);
            }
        }

        // WAS Root를 기준으로 파일 생성
        fullPath = WAS_ROOT;
        if(!fullPath.endsWith("/")) {
            fullPath += "/";
        }
        fullPath += path;

        File file = new File(fullPath);

        return publish(file, content, false);
    }

    /**
     * 컨텐츠 퍼블리싱
     * 
     * @return
     */
    public static Boolean publishContents(CmsCntntsVO cmsCntntsVo, CmsMetaVO metaVo) {

        String fullPath = "";

        String content = makeHtml(cmsCntntsVo, metaVo);
        String path = cmsCntntsVo.getBscPathNm();
        String fileName = cmsCntntsVo.getUserMenuEngNm() + "." + "jsp";

        // 설정에 동시 저장 경로 목록이 있는 경우
        if(Validate.isNotEmpty(PROGRAM_PATH)) {
            for(String devPath : PROGRAM_PATH) {
                fullPath = devPath;
                if(!fullPath.endsWith("/")) {
                    fullPath += "/";
                }
                fullPath += path;

                File file = new File(fullPath, fileName);
                publish(file, content, true);
            }
        }

        // 개발 모드인경우 서버 Plugin ROOT 이외에 로컬 소스 경로(Eclipse)에도 파일을 생성한다.
        if(BaseConfig.PRO_MODE && Validate.isNotEmpty(DEV_PATH)) {
            for(String devPath : DEV_PATH) {
                fullPath = devPath;
                if(!fullPath.endsWith("/")) {
                    fullPath += "/";
                }
                fullPath += path;

                File file = new File(fullPath, fileName);
                publish(file, content, true);
            }
        }

        // WAS Root를 기준으로 파일 생성
        fullPath = WAS_ROOT;
        if(!fullPath.endsWith("/")) {
            fullPath += "/";
        }
        fullPath += path;

        File file = new File(fullPath, fileName);

        return publish(file, content, true);
    }

    /**
     * 퍼블리싱 대상 내용을 받아서 파일로 생성
     * 
     * @return
     */
    public static Boolean publish(File file, String content, boolean headerYn) {
        Boolean published = Boolean.FALSE;

        try {
            if(headerYn) {
                content = HEADER + content;
            }

            File parent = new File(file.getParent());
            if(!parent.isDirectory()) {
                FileUtil.mkdirs(parent);
                parent.setWritable(true);
            }

            FileUtil.writeString(file, content, FILE_ENCODING);
            published = Boolean.TRUE;

            if(logger.isDebugEnabled()) {
                logger.debug("CMS Content file create path : {}", file.getAbsolutePath());
            }
        } catch (IOException e) {
            if(logger.isDebugEnabled()) {
                logger.debug("file write failed ! path : {}", file.getAbsolutePath(), e);
            }
        }

        return published;
    }

    /**
     * HTML 내용을 생성한다.
     * 
     * @param title 제목
     * @param headContent head tag 사이의 컨텐츠
     * @param bodyContent body tag 사이의 컨텐츠
     * @return
     */
    public static String makeHtml(CmsCntntsVO cmsCntntsVo, CmsMetaVO metaVo) {

        // 제목을 별도로 설정한 경우 제목을 사용
        String title = cmsCntntsVo.getTtl();
        if(Validate.isEmpty(title)) {
            title = cmsCntntsVo.getMenuNm();
        }
        String headContent = cmsCntntsVo.getStrtContsCn();
        String bodyContent = cmsCntntsVo.getMainContsCn();

        StringBuilder sb = new StringBuilder();

        sb.append(Config.getString("cms-config.htmlOp"));
        sb.append("\n");
        sb.append(Config.getString("cms-config.headOp"));
        sb.append("\n");

        if(Validate.isNotEmpty(metaVo)) {
            // 키워드 메타
            String kwrd = metaVo.getUserMenuSrchNm();
            if(Validate.isEmpty(kwrd)) {
                kwrd = cmsCntntsVo.getMenuNm();
            }
            sb.append("<meta name=\"keywords\" content=\"").append(kwrd).append("\" />");
            sb.append("\n");

            // 설명 메타
            String dc = metaVo.getUserMenuExpln();
            if(Validate.isEmpty(dc)) {
                dc = cmsCntntsVo.getMenuNm();
            }
            sb.append("<meta name=\"description\" content=\"").append(dc).append("\" />");
            sb.append("\n");

            // 검색엔진 메타
            String robot = metaVo.getSiteSrchNm();
            if(Validate.isNotEmpty(robot)) {
                sb.append("<meta name=\"robots\" content=\"").append(robot).append("\" />");
                sb.append("\n");
            }

            // 임의 작성 메타
            if(Validate.isNotEmpty(metaVo.getMenuTagCn())) {
                sb.append(metaVo.getMenuTagCn());
                sb.append("\n");
            }
        }
        sb.append(Config.getString("cms-config.titleOp"));
        // title 설정
        sb.append(title);
        sb.append("\n");
        sb.append(Config.getString("cms-config.titleEd"));
        sb.append("\n");

        if(Validate.isNotEmpty(cmsCntntsVo.getCssFileNm())) {
            sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cmsCntntsVo.getCssFileNm() + "\" />");
        }
        sb.append("\n");
        // <head></head> 태그 사이에 들어갈 내용
        if(Validate.isNotEmpty(headContent)) {
            sb.append(headContent.trim());
        }

        sb.append("\n");
        sb.append(Config.getString("cms-config.headEd"));
        sb.append("\n");
        sb.append(Config.getString("cms-config.bodyOp"));
        sb.append("\n");
        // <body></body> 태그 사이에 들어갈 내용
        sb.append(bodyContent.trim());
        sb.append("\n");

        // 저작권 정보
        if(Validate.isNotEmpty(metaVo) && Validate.isNotEmpty(metaVo.getCprgtUseYn()) && "Y".equals(metaVo.getCprgtUseYn()) && Validate.isNotEmpty(metaVo.getCprgtCn())) {
            sb.append(metaVo.getCprgtCn());
        }

        sb.append(Config.getString("cms-config.bodyEd"));
        sb.append("\n");
        sb.append(Config.getString("cms-config.htmlEd"));

        return sb.toString();
    }

    /**
     * 레이아웃 저장 경로와 파일명을 생성한다.
     * 
     * @param cmsLayoutVo
     */
    public static void makeLayoutInfo(CmsLayoutVO cmsLayoutVo) {
        String decoratorRoot = CmsSupport.DECORATOR_FILE_ROOT;
        Integer domainCd = cmsLayoutVo.getSiteSn();

        StringBuilder sb = new StringBuilder();
        sb.append(decoratorRoot);
        if(!decoratorRoot.endsWith("/")) {
            sb.append("/");
        }
        sb.append(domainCd);
        sb.append("/");
        sb.append(cmsLayoutVo.getLytCdNo());
        sb.append(".jsp");

        cmsLayoutVo.setFilePathNm(sb.toString());
    }
}
