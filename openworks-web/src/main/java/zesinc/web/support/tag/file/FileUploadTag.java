/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.file;

import java.util.ArrayList;
import java.util.List;

import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;
import zesinc.web.support.tag.OpTagSupport;
import zesinc.web.utils.FileUtil;
import zesinc.web.vo.IFileVO;

/**
 * 첨부파일 업로드 UI를 생성한다.
 * 지정된 유형에 해당되는 단순 폼, jquery multifile, blueimp-file-upload 중에서 구성된다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 2. 25.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class FileUploadTag extends OpTagSupport {

    /** 미지정 시 기본 JSP 템플릿 */
    private static final String BASIC_JSP = "file/basic.jsp";
    /** 팝업 화면 목록 JSP 템플릿 */
    private static final String MULTI_JSP = "file/multi.jsp";
    /** 상세(수정등 포함) 화면 기본 JSP 템플릿 */
    private static final String UPLOADER_JSP = "file/uploader.jsp";
    /** 기본 값은 단순 폼 */
    private UPLOAD_TYPE view = UPLOAD_TYPE.valueOf(Config.getString("system-config.fileUpload.view"));

    /** input name 속성 */
    private String name = Config.getString("system-config.fileUpload.name");
    /** 첨부파일 갯수 제한 */
    private int count = Config.getInt("system-config.fileUpload.count");
    /** 첨부파일 개별 크기 제한 */
    private long size = Config.getInt("system-config.fileUpload.size");
    /** 전체 파일 크기 제한 */
    private long maxSize = Config.getInt("system-config.fileUpload.maxSize");
    /** 허용되는 확장자 정보 */
    private String exts = Config.getString("system-config.fileUpload.fileExts");
    /** 첨부파일 목록 */
    private List<IFileVO> fileList;
    /** 파일 설명 입력 필요 여부 */
    private Boolean needDc = Boolean.FALSE;
    /** 삭제파일 일련번호 */
    private String fileIdNm = "fileIds";

    /** byte 크기 단위 */
    private static long KILO = 1024L;
    private static long MEGA = KILO * KILO;
    private static long GIGA = MEGA * KILO;

    @Override
    public String getPage() {
        if(this.page == null) {
            return this.view.getJsp();
        }

        return this.page;
    }

    @Override
    public void beforeTag() {
        int opFileListSize = 0;
        List<IFileVO> nameFileList = new ArrayList<IFileVO>();
        if(Validate.isNotEmpty(this.fileList)) {
            for(IFileVO fileVo : this.fileList) {
                if(fileVo.getInptDataNm().equals(this.name)) {
                    nameFileList.add(fileVo);
                }
            }
        }
        if(Validate.isNotEmpty(nameFileList)) {
            opFileListSize = nameFileList.size();
        }
        String exten = this.exts;
        long opFileSize = this.size;
        long opFileMaxSize = this.maxSize;

        long prvTotalFileSize = 0L;
        if(Validate.isNotEmpty(nameFileList)) {
            for(IFileVO fileVo : nameFileList) {
                prvTotalFileSize += fileVo.getByteFileSz();
            }
        }

        // 이전에 등록되어 있는 파일의 전체 크기를 빼고난 값으로 전체 업로드 가능 사이즈로 잡는다.
        opFileMaxSize = opFileMaxSize - prvTotalFileSize;

        if(this.view.equals(UPLOAD_TYPE.multi)) {
            exten = StringUtil.replace(this.exts, ",", "|");
            opFileSize = opFileSize / KILO;
            opFileMaxSize = opFileMaxSize / KILO;
        } else if(this.view.equals(UPLOAD_TYPE.uploader)) {
            exten = StringUtil.replace(this.exts, ",", "|");
        }

        addAttribute("opFileInputName", this.name);
        addAttribute("opFileCount", this.count);

        addAttribute("opFileSize", opFileSize);
        addAttribute("opFileMaxSize", opFileMaxSize);

        addAttribute("opFileViewSize", FileUtil.toDisplaySize(opFileSize));
        addAttribute("opFileViewMaxSize", FileUtil.toDisplaySize(opFileMaxSize));

        addAttribute("opFileIdNm", fileIdNm);
        addAttribute("opFileExts", exten);
        addAttribute("opFileList", nameFileList);
        addAttribute("opFileListSize", opFileListSize);

        addAttribute("needDc", needDc);
    }

    /**
     * view을 설정
     * 
     * @param view 을(를) String view로 설정
     */
    public void setView(String view) {

        if(UPLOAD_TYPE.valueOf(view) != null) {
            this.view = UPLOAD_TYPE.valueOf(view);
        }
        this.page = this.view.getJsp();
    }

    /**
     * name을 설정
     * 
     * @param name 을(를) String name로 설정
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * count을 설정
     * 
     * @param count 을(를) int count로 설정
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * size을 설정
     * 
     * @param size 을(를) String size로 설정
     */
    public void setSize(String size) {

        this.size = explainSize(size);
    }

    /**
     * maxSize을 설정
     * 
     * @param maxSize 을(를) String maxSize로 설정
     */
    public void setMaxSize(String maxSize) {

        this.maxSize = explainSize(maxSize);
    }

    /**
     * fileIdNm을 설정
     * 
     * @param fileIdNm 을(를) String fileIdNm로 설정
     */
    public void setFileIdNm(String fileIdNm) {
        this.fileIdNm = fileIdNm;
    }

    /**
     * exts을 설정
     * 
     * @param exts 을(를) String exts로 설정
     */
    public void setExts(String exts) {

        this.exts = exts;
    }

    /**
     * fileList을 설정
     * 
     * @param fileList 을(를) List<IFileVO> fileList로 설정
     */
    public void setFileList(List<IFileVO> fileList) {
        this.fileList = fileList;
    }

    /**
     * needDc을 설정
     * 
     * @param needDc 을(를) Boolean needDc로 설정
     */
    public void setNeedDc(String needDc) {
        Boolean isNeed = Boolean.valueOf(needDc);

        this.needDc = isNeed;
    }

    /**
     * <pre>
     * 업로드 페이지 유형
     * 
     * basic : 기본 폼 사용
     * multi : jquery multifile 사용
     * uploader : html5 업로드 플러그인 사용
     * </pre>
     * 
     * <pre>
     * << 개정이력(Modification Information) >>
     *    
     *     수정일       수정자   수정내용
     * --------------  --------  -------------------------------
     *  2015. 2. 25.    방기배   최초작성
     * </pre>
     * 
     * @author (주)제스아이엔씨 기술연구소
     * @see
     */
    private static enum UPLOAD_TYPE {
        /** 목록형 */
        basic(BASIC_JSP),

        /** 팝업 화면 목록형 */
        multi(MULTI_JSP),

        /** 상세형(등록, 수정 포함) */
        uploader(UPLOADER_JSP);

        /** 항목에 따른 jsp 페이지 */
        private String jsp;

        /**
         * 유형에 해당하는 jsp 설정
         * 
         * @param jsp
         */
        UPLOAD_TYPE(String jsp) {
            this.jsp = jsp;
        }

        /**
         * 유형에 해당하는 jsp 반환
         * 
         * @return
         */
        public String getJsp() {
            return jsp;
        }
    }

    /**
     * 실제 BYTE 크기로 변경
     * explainSize 설명
     * 
     * @param size
     * @return
     */
    private long explainSize(String size) {

        size = size.trim();
        if(Validate.isDigits(size)) {
            return Long.parseLong(size);
        }

        String numStr;
        String markStr;

        int splitPos = size.length() - 1;
        numStr = size.substring(0, splitPos);
        markStr = size.substring(splitPos).toLowerCase();

        long longs = Long.parseLong(numStr);

        if("m".equals(markStr)) {
            return longs * MEGA;
        } else if("k".equals(markStr)) {
            return longs * KILO;
        } else if("g".equals(markStr)) {
            return longs * GIGA;
        }

        return 0;
    }

}
