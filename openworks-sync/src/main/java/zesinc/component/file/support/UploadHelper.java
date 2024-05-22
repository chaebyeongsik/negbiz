/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.file.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import zesinc.component.file.domain.FileVO;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.utils.FileUtil;
import zesinc.web.utils.PathUtil;
import zesinc.web.utils.XssUtil;
import zesinc.web.utils.image.ThumbnailUtil;
import zesinc.web.vo.ISessVO;
import zesinc.web.vo.IUserSessVO;

/**
 * File upload 지원 클레스
 * 
 * @author (주)제스아이엔씨 기술연구소
 * 
 *         <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 2. 7.    방기배   최초작성
 * </pre>
 * @see
 */
public class UploadHelper {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(UploadHelper.class);
    /** 시스템구분 intra 또는 user */
    private static String SYSTEM_KIND = BaseConfig.SYSTEM_KIND;
    /** 첨부 파일 저장 경로 */
    private static final String UPLOAD_FOLDER_PATH = BaseConfig.UPLOAD_FOLDER_PATH;
    /** 컨텐츠 파일 저장 경로(웹서버에서 직접 접근) */
    private static final String RESOURCE_FOLDER_PATH = BaseConfig.RESOURCE_FOLDER_PATH;
    /** 기능별 저장경로를 지정하지 않는 경우의 기본 폴더명 */
    private static final String DEFAULT_UPLOAD_FOLDER_NAME = BaseConfig.DEFAULT_UPLOAD_FOLDER_NAME;
    /** File.separator */
    public static final String separator = "/";
    /** 첨부파일 ROOT 경로 */
    public static final String UPLOAD_ROOT = BaseConfig.UPLOAD_ROOT;
    /** 정적컨텐츠파일 ROOT 경로 */
    public static final String RESOURCE_ROOT = BaseConfig.RESOURCE_ROOT;

    /** 섬네일 생성가능 이미지 확장자 목록 */
    public static final List<String> THUMB_NAIL_EXTS;
    static {
        String[] exts = Config.getString("system-config.imageFileExts", "bmp,gif,jpg,jpeg,jfif,pcx,png,tiff,wbmp").split(",");
        THUMB_NAIL_EXTS = new ArrayList<String>();
        for(String ext : exts) {
            THUMB_NAIL_EXTS.add(ext);
        }
    }

    /**
     * 기본 폴더명을 사용하여 파일 저장
     * 
     * @param request Http 요청 객체
     * @return 저장 파일 목록
     */
    public static List<FileVO> upload(HttpServletRequest request) {

        return upload(request, DEFAULT_UPLOAD_FOLDER_NAME);
    }

    /**
     * 폴더명을 지정하여 파일 저장
     * 
     * @param request
     * @param folderName 파일저장 폴더명
     * @return 저장 파일 목록
     */
    public static List<FileVO> upload(HttpServletRequest request, String folderName) {

        return upload(request, folderName, Boolean.FALSE);
    }

    /**
     * 폴더명을 지정하여 파일 저장
     * 
     * @param request Http 요청 객체
     * @param folderName 파일저장 폴더명
     * @param isResource 정적컨텐츠 여부(웹서버 직접 접근 여부)
     * @return 저장 파일 목록
     */
    public static List<FileVO> upload(HttpServletRequest request, String folderName, Boolean isResource) {

        return upload(request, folderName, isResource, null);
    }

    /**
     * 폴더명을 지정하여 파일 저장하며, inputName에 해당하는 파일만 처리한다.
     * 
     * @param request Http 요청 객체
     * @param folderName 파일저장 폴더명
     * @param inputName 처리할 파일 폼 이름(file input name)
     * @return 저장 파일 목록
     */
    public static List<FileVO> upload(HttpServletRequest request, String folderName, String inputName) {

        return upload(request, folderName, Boolean.FALSE, inputName);
    }

    /**
     * upload 설명
     * 
     * @param request Http 요청 객체
     * @param folderName 파일저장 폴더명
     * @param isResource 정적컨텐츠 여부(웹서버 직접 접근 여부)
     * @param inputName 처리할 파일 폼 이름(file input name)
     * @return 저장 파일 목록
     * @throws Exception
     */
    public static List<FileVO> upload(HttpServletRequest request, String folderName, Boolean isResource, String inputName) {

        if(!MultipartHttpServletRequest.class.isAssignableFrom(request.getClass())) {
            return new ArrayList<FileVO>();
        }

        if(Validate.isEmpty(folderName)) {
            folderName = DEFAULT_UPLOAD_FOLDER_NAME;
        }

        String dataYn = (isResource ? "Y" : "N");

        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> multiList = new ArrayList<MultipartFile>();
        Iterator<String> fileNames = multiRequest.getFileNames();
        while(fileNames.hasNext()) {
            multiList.addAll(multiRequest.getFiles(fileNames.next()));
        }

        List<FileVO> fileList = new ArrayList<FileVO>();

        int fileCnt = multiList.size();
        if(fileCnt > 0) {

            String userId = "";
            try {
                Object sessVo = null;
                if(SYSTEM_KIND.equals("intra")) {
                    sessVo = OpHelper.getMgrSession();
                    userId = ((ISessVO) sessVo).getUsername();
                } else {
                    sessVo = OpHelper.getUserSession();
                    userId = ((IUserSessVO) sessVo).getUserId();
                }
            } catch (Exception e) {
                // do nothing
            }

            try {
                String rootPath = getRootPath(isResource);
                String filePath = getFilePath(isResource);
                String datePath = getDatePath();

                String webFolderPath = separator + filePath + folderName + separator + datePath;
                String concludeFolderPath = rootPath + filePath + folderName + separator + datePath;
                FileUtil.mkdirs(concludeFolderPath);

                String[] fileDescs = request.getParameterValues("fileExpln");

                FileVO fileVo;
                List<String> extList = Arrays.asList(BaseConfig.NOT_ALLOW_FILE_EXTS);

                for(int i = 0 ; i < fileCnt ; i++) {

                    MultipartFile multiFile = multiList.get(i);

                    if(!multiFile.isEmpty()) {
                        // fileInput 폼네임 필터
                        if(Validate.isEmpty(inputName) || multiFile.getName().equals(inputName)) {

                            String fileDesc = "";
                            if(fileDescs != null && fileDescs.length > i && fileDescs[i] != null) {
                                fileDesc = XssUtil.cleanTag(fileDescs[i], XssUtil.TYPE.ALL);
                            }

                            String uuid = UUID.randomUUID().toString();
                            String ext = extension(multiFile.getOriginalFilename());

                            // 허용불가 첨부파일 유형인 경우 업로드 된 파일을 무시하며, 결과에도 포함하지 않는다.
                            if(extList.contains(ext)) {
                                multiFile.getInputStream().close();
                                continue;
                            }

                            String newFileName = uuid + "." + ext;
                            File newFileObject = new File(concludeFolderPath, newFileName);
                            String fileType = multiFile.getContentType();

                            multiFile.transferTo(newFileObject);

                            fileVo = new FileVO();

                            fileVo.setFile(newFileObject);
                            fileVo.setFileUrlAddr(PathUtil.toUnixPath(webFolderPath) + newFileName);
                            fileVo.setInptDataNm(multiFile.getName());
                            fileVo.setSrvrFileNm(newFileName);
                            fileVo.setOrgnlFileNm(multiFile.getOriginalFilename());
                            fileVo.setFileTypeNm(fileType);
                            fileVo.setFileSzNm(FileUtil.toDisplaySize(multiFile.getSize()));
                            fileVo.setByteFileSz(multiFile.getSize());
                            fileVo.setFileId(UUID.randomUUID().toString());
                            fileVo.setFileExtnNm(ext);
                            fileVo.setDataYn(dataYn);
                            fileVo.setFileExpln(fileDesc);
                            fileVo.setRgtrId(userId);

                            fileList.add(fileVo);
                            logger.debug("UploadHelper upload 파일 업로드 경로 : {}", newFileObject.getAbsolutePath());
                            // 개발설정 파일 생성
                            DevFileUtil.makeDevPathFile(newFileObject, fileVo.getFileUrlAddr());
                        }
                    }
                }
            } catch (IOException e) {
                logger.error("Upload error", e);
            }
        }
        return fileList;
    }

    /**
     * <pre>
     * 파일목록을 받아서 섬네일 생성 가능 이미지 파일인 경우 섬네일을 생성하고,
     * FileVO.setThmbPathNm(PATH); 메소드를 통하여 설정해 준다.
     * 
     * 섬네일은 용도상 브라우저로 직접 접근가능한 웹경로를 ROOT로 하여 생성한다.
     * 예 : /thumb/이하/원본/경로/파일명
     * 
     * FileVO 목록을 인자로 생성하므로 FileService에서 파일 목록을 가져와서 사용할 수도 있다.
     * 프로젝트상 썸네일은 필요하지만 생성되어 있지 않은 경우에 사용할 수 있도록 메소드를 분리함
     * </pre>
     * 
     * @param fileList 파일 목록. 확장자가 system-config 설정의 imageFileExts에 정의된 확장자만 생성한다.
     * @param width 썸네일 width 0인 경우 높이에 비례하여 축소
     * @param height 썸네일 height 0인 경우 너비에 비례하여 축소
     * @param prefix 썸네일이미지 파일명 접두사 prefix가 "test_"라면 "test_파일명.확장자" 형태로 이름이 생성됨
     */
    public static void makeThumbNail(List<FileVO> fileList, int width, int height, String prefix) {
        if(Validate.isNotEmpty(fileList)) {
            // 일반 첨부와 웹공개 첨부파일을 구분하여 파일 위치를 탐색
            String destRrootPath = getRootPath(Boolean.TRUE);
            String srcRootPath = null;
            for(FileVO fileVo : fileList) {
                if(Validate.isNotEmpty(fileVo.getDataYn()) && fileVo.getDataYn().equals("Y")) {
                    srcRootPath = getRootPath(Boolean.TRUE);
                } else {
                    srcRootPath = getRootPath(Boolean.FALSE);
                }

                String ext = fileVo.getFileExtnNm().toLowerCase();
                if(THUMB_NAIL_EXTS.contains(ext)) {

                    String filePath = "";
                    String thumbPath = "";
                    String thumbName = "";

                    if(Validate.isNotEmpty(fileVo.getThmbPathNm())) {
                        filePath = fileVo.getThmbPathNm();
                        int pos = filePath.lastIndexOf("/");
                        thumbPath = filePath.substring(1, pos);
                        thumbName = filePath.substring(pos + 1);
                    } else {
                        filePath = fileVo.getFileUrlAddr();
                        int pos = filePath.lastIndexOf("/");
                        thumbPath = getFilePath(Boolean.TRUE) + filePath.substring(1, pos);
                        thumbName = UUID.randomUUID().toString() + "." + ext;
                    }

                    if(Validate.isNotEmpty(prefix)) {
                        thumbName = prefix + thumbName;
                    }

                    FileUtil.mkdirs(destRrootPath + thumbPath);

                    File thumb = new File(destRrootPath + thumbPath, thumbName);
                    File file = new File(srcRootPath + fileVo.getFileUrlAddr().substring(1));
                    if(file.exists()) {
                        if(ThumbnailUtil.resize(file, thumb, width, height)) {
                            // 복수 이미지인 경우 이미 첫 섬네일이 생성되었다면 이름을 유지 시켜준다.
                            if(Validate.isEmpty(fileVo.getThmbPathNm())) {
                                fileVo.setThmbPathNm("/" + thumbPath + "/" + thumbName);
                            }
                            // 개발설정 파일 생성
                            DevFileUtil.makeDevPathFile(thumb, thumbPath, thumbName);
                        } else {
                            logger.debug("===========================================================");
                            logger.debug("===========================================================");
                            logger.debug("ThumbnailUtil.resize 실패 원본파일 : {} ", file.getAbsolutePath());
                            logger.debug("ThumbnailUtil.resize 실패 썸네일파일 : {} ", thumb.getAbsolutePath());
                            logger.debug("===========================================================");
                            logger.debug("===========================================================");
                        }
                    } else {
                        logger.debug("===========================================================");
                        logger.debug("===========================================================");
                        logger.debug("ThumbnailUtil.resize 원본경로 파일없음: {} ", file.getAbsolutePath());
                        logger.debug("===========================================================");
                        logger.debug("===========================================================");
                    }
                    thumb = null;
                    file = null;
                }
            }
        }
    }

    /**
     * <pre>
     * 파일목록을 받아서 섬네일 생성 가능 이미지 파일인 경우 섬네일을 생성하고,
     * FileVO.setThmbPathNm(PATH); 메소드를 통하여 설정해 준다.
     * 
     * 섬네일은 용도상 브라우저로 직접 접근가능한 웹경로를 ROOT로 하여 생성한다.
     * 예 : /thumb/이하/원본/경로/파일명
     * 
     * FileVO 목록을 인자로 생성하므로 FileService에서 파일 목록을 가져와서 사용할 수도 있다.
     * 프로젝트상 썸네일은 필요하지만 생성되어 있지 않은 경우에 사용할 수 있도록 메소드를 분리함
     * </pre>
     * 
     * @param fileList 파일 목록. 확장자가 system-config 설정의 imageFileExts에 정의된 확장자만 생성한다.
     * @param width 썸네일 width
     * @param height 썸네일 height
     */
    public static void makeThumbNail(List<FileVO> fileList, int width, int height) {

        makeThumbNail(fileList, width, height, null);
    }

    /**
     * <pre>
     * 파일목록을 받아서 섬네일 생성 가능 이미지 파일인 경우 워터마크를 포함하여 썸네일을 생성하고,
     * FileVO.setThmbPathNm(PATH); 메소드를 통하여 설정해 준다.
     * 
     * 섬네일은 용도상 브라우저로 직접 접근가능한 웹경로를 ROOT로 하여 생성한다.
     * 예 : /thumb/이하/원본/경로/파일명
     * 
     * FileVO 목록을 인자로 생성하므로 FileService에서 파일 목록을 가져와서 사용할 수도 있다.
     * 프로젝트상 썸네일은 필요하지만 생성되어 있지 않은 경우에 사용할 수 있도록 메소드를 분리함
     * </pre>
     * 
     * @param fileList 파일 목록. 확장자가 system-config 설정의 imageFileExts에 정의된 확장자만 생성한다.
     * @param width 썸네일 width 0인 경우 높이에 비례하여 축소
     * @param height 썸네일 height 0인 경우 너비에 비례하여 축소
     * @param waterMark 워터마크파일 경로문자열
     * @param position 워터마크 표시 위치 TOP/CENTER/BOTTOM + "_" LEFT/CENTER/RIGHT 조합 단 CENTER_CENTER는
     *        CENTER 만 사용
     *        예 : TOP_LEFT, BOTTOM_RIGHT, CENTER, CENTER_RIGHT
     * @param transparent 1f 를 100%로 계산하며 0.5f 면 50%의 투명도로 생성 됨
     * @param prefix 썸네일이미지 파일명 접두사 prefix가 "test_"라면 "test_파일명.확장자" 형태로 이름이 생성됨
     */
    public static void makeWarterMark(List<FileVO> fileList, int width, int height, String waterMark, String position,
        float transparent, String prefix) {

        if(Validate.isNotEmpty(fileList)) {
            // 일반 첨부와 웹공개 첨부파일을 구분하여 파일 위치를 탐색
            String destRrootPath = getRootPath(Boolean.TRUE);
            String srcRootPath = null;
            File waterWtmkFileNm = new File(destRrootPath + waterMark.substring(1));

            for(FileVO fileVo : fileList) {
                if(Validate.isNotEmpty(fileVo.getDataYn()) && fileVo.getDataYn().equals("Y")) {
                    srcRootPath = getRootPath(Boolean.TRUE);
                } else {
                    srcRootPath = getRootPath(Boolean.FALSE);
                }

                String ext = fileVo.getFileExtnNm().toLowerCase();
                if(THUMB_NAIL_EXTS.contains(ext)) {

                    String filePath = "";
                    String thumbPath = "";
                    String thumbName = "";

                    if(Validate.isNotEmpty(fileVo.getThmbPathNm())) {
                        filePath = fileVo.getThmbPathNm();
                        int pos = filePath.lastIndexOf("/");
                        thumbPath = filePath.substring(1, pos);
                        thumbName = filePath.substring(pos + 1);
                    } else {
                        filePath = fileVo.getFileUrlAddr();
                        int pos = filePath.lastIndexOf("/");
                        thumbPath = getFilePath(Boolean.TRUE) + filePath.substring(1, pos);
                        thumbName = UUID.randomUUID().toString() + "." + ext;
                    }

                    if(Validate.isNotEmpty(prefix)) {
                        thumbName = prefix + thumbName;
                    }

                    FileUtil.mkdirs(destRrootPath + thumbPath);

                    File file = new File(srcRootPath + fileVo.getFileUrlAddr().substring(1));
                    File thumb = new File(destRrootPath + thumbPath, thumbName);
                    if(file.exists()) {
                        if(ThumbnailUtil.warterMark(file, thumb, width, height, waterWtmkFileNm, position, transparent)) {
                            // 복수 이미지인 경우 이미 첫 섬네일이 생성되었다면 이름을 유지 시켜준다.
                            if(Validate.isEmpty(fileVo.getThmbPathNm())) {
                                fileVo.setThmbPathNm("/" + thumbPath + "/" + thumbName);
                            }
                            // 개발설정 파일 생성
                            DevFileUtil.makeDevPathFile(thumb, thumbPath, thumbName);
                        } else {
                            logger.debug("===========================================================");
                            logger.debug("===========================================================");
                            logger.debug("ThumbnailUtil.warterMark 실패 원본파일 : {} ", file.getAbsolutePath());
                            logger.debug("ThumbnailUtil.warterMark 실패 썸네일파일 : {} ", thumb.getAbsolutePath());
                            logger.debug("===========================================================");
                            logger.debug("===========================================================");
                        }
                    } else {
                        logger.debug("===========================================================");
                        logger.debug("===========================================================");
                        logger.debug("ThumbnailUtil.warterMark DB 원본경로 파일없음: {} ", file.getAbsolutePath());
                        logger.debug("===========================================================");
                        logger.debug("===========================================================");
                    }
                    thumb = null;
                    file = null;
                }
            }
        }
    }

    /**
     * <pre>
     * 파일목록을 받아서 섬네일 생성 가능 이미지 파일인 경우 워터마크를 포함하여 썸네일을 생성하고,
     * FileVO.setThmbPathNm(PATH); 메소드를 통하여 설정해 준다.
     * 
     * 섬네일은 용도상 브라우저로 직접 접근가능한 웹경로를 ROOT로 하여 생성한다.
     * 예 : /thumb/이하/원본/경로/파일명
     * 
     * FileVO 목록을 인자로 생성하므로 FileService에서 파일 목록을 가져와서 사용할 수도 있다.
     * 프로젝트상 썸네일은 필요하지만 생성되어 있지 않은 경우에 사용할 수 있도록 메소드를 분리함
     * </pre>
     * 
     * @param fileList 파일 목록. 확장자가 system-config 설정의 imageFileExts에 정의된 확장자만 생성한다.
     * @param width 썸네일 width
     * @param height 썸네일 height
     * @param waterMark 워터마크파일 경로문자열
     * @param position 워터마크 표시 위치 TOP/CENTER/BOTTOM + "_" LEFT/CENTER/RIGHT 조합 단 CENTER_CENTER는
     *        CENTER 만 사용
     *        예 : TOP_LEFT, BOTTOM_RIGHT, CENTER, CENTER_RIGHT
     * @param transparent 1f 를 100%로 계산하며 0.5f 면 50%의 투명도로 생성 됨
     */
    public static void makeWarterMark(List<FileVO> fileList, int width, int height, String waterMark, String position, String transparent) {

        Float tp = new Float(transparent);

        makeWarterMark(fileList, width, height, waterMark, position, tp.floatValue(), null);
    }

    /**
     * 최상위 ROOT 경로 반환
     * 
     * @return 최상위 저장경로
     */
    public static String getRootPath(Boolean isResource) {

        String rootPath;

        if(Validate.isNotEmpty(isResource) && isResource) {
            rootPath = RESOURCE_ROOT;
        } else {
            rootPath = UPLOAD_ROOT;
        }

        rootPath += rootPath.endsWith(separator) ? "" : separator;

        return rootPath;
    }

    /**
     * ROOT 경로를 제외한 경로
     * 
     * @param isResource
     * @return
     */
    public static String getFilePath(Boolean isResource) {
        String filePath;

        if(Validate.isNotEmpty(isResource) && isResource) {
            filePath = RESOURCE_FOLDER_PATH;
        } else {
            filePath = UPLOAD_FOLDER_PATH;
        }
        filePath += separator;

        return filePath;
    }

    /**
     * 년월일 기반 저장 경로 생성
     * 
     * @param folderPath
     * @return
     */
    public static String getDatePath() {

        Calendar cal = Calendar.getInstance();
        String yearFolderName = Integer.toString(cal.get(Calendar.YEAR));
        String monthFolderName = Integer.toString(cal.get(Calendar.MONTH) + 1);
        String dayFolderName = Integer.toString(cal.get(Calendar.DATE));

        String datePath = yearFolderName + separator + monthFolderName
            + separator + dayFolderName + separator;

        return datePath;
    }

    /**
     * 파일 확장자 추출
     * 
     * @param fileName
     * @return
     */
    private static String extension(String fileName) {

        return FileUtil.extension(fileName).toLowerCase().trim();
    }
}
