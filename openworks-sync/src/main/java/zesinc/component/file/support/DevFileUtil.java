/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.file.support;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.FileUtil;

/**
 * ROOT 이외에 로컬 소스 경로(Eclipse)에 파일을 생성하도록 하는 유틸
 * 기본적으로는 파일 복사 기능을 하지만, 환경설정상의 개발설정 경로를
 * 기본 루트로 하여 복사를 한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2016. 1. 6.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class DevFileUtil {

    /** logger */
    private static Logger logger = LoggerFactory.getLogger(DevFileUtil.class);
    /** 개발환경 소스파일 Root 경로 */
    public static List<String> DEV_PATH = BaseConfig.DEV_PATH_LIST;

    /**
     * 원본파일을 소스 파일의 ROOT 경로를 이하 인자의 경로와 파일명을 합하여
     * 파일을 복사한다.
     * 
     * @param sourceFile
     * @param excludeRootPath /로 시작되는 경로
     * @param fileName
     */
    public static void makeDevPathFile(File sourceFile, String excludeRootPath, String fileName) {
        makeDevPathFile(sourceFile, excludeRootPath + "/" + fileName);
    }

    /**
     * 원본파일을 소스 파일의 ROOT 경로를 이하 인자의 파일명 포함 경로을 합하여
     * 파일을 복사한다.
     * 
     * @param sourceFile
     * @param excludeRootPath /로 시작되는 경로
     */
    public static void makeDevPathFile(File sourceFile, String excludeRootPath) {
        String fullPath = "";
        try {
            if(BaseConfig.PRO_MODE && Validate.isNotEmpty(DEV_PATH)) {
                for(String devPath : DEV_PATH) {
                    fullPath = devPath;
                    if(!fullPath.endsWith("/")) {
                        fullPath += "/";
                    }
                    fullPath += excludeRootPath;

                    File copyFile = new File(fullPath);
                    File parent = copyFile.getParentFile();
                    if(!parent.exists()) {
                        FileUtil.mkdirs(parent);
                    }
                    FileUtil.copy(sourceFile, copyFile);

                    logger.debug("Create makeDevPathFile : {}", copyFile.getAbsolutePath());
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("개발 설정 파일 생성 실패 {}, Exception : {}", fullPath, e);
        }
    }

    /**
     * 원본파일을 소스 파일의 ROOT 경로를 이하 인자의 경로와 파일명을 합하여
     * 파일을 복사한다.
     * 
     * @param excludeRootPath /로 시작되는 경로
     * @param fileName
     */
    public static void deleteDevPathFile(String excludeRootPath, String fileName) {
        deleteDevPathFile(excludeRootPath + "/" + fileName);
    }

    /**
     * 원본파일을 소스 파일의 ROOT 경로를 이하 인자의 파일명 포함 경로을 합하여
     * 파일을 복사한다.
     * 
     * @param excludeRootPath /로 시작되는 경로
     */
    public static void deleteDevPathFile(String excludeRootPath) {
        String fullPath = "";
        try {
            if(BaseConfig.PRO_MODE && Validate.isNotEmpty(DEV_PATH)) {
                for(String devPath : DEV_PATH) {
                    fullPath = devPath;
                    if(fullPath.endsWith("/")) {
                        fullPath = fullPath.substring(0, fullPath.length() - 1);
                    }

                    fullPath += excludeRootPath;
                    File file = new File(fullPath);
                    if(file.exists() && file.canWrite()) {
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("개발 설정 파일 삭제 실패 {}, Exception : {}", fullPath, e);
        }
    }
}
