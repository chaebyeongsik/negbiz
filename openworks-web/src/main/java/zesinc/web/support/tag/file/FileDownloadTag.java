/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.file;

import java.util.List;

import zesinc.web.support.tag.OpTagSupport;
import zesinc.web.vo.IFileVO;

/**
 * 첨부파일 목록 및 다운로드, 이력보기 등의 첨부파일 관련 기능을 일괄 제공한다.
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
public class FileDownloadTag extends OpTagSupport {

    /** 기본 JSP 템플릿 */
    private static final String BASIC_JSP = "file/download.jsp";

    /** 첨부파일 목록 */
    private List<IFileVO> fileList;

    /** 파일등록시 input file tag의 name */
    private String name = null;

    @Override
    public String getPage() {
        if(this.page == null) {
            return BASIC_JSP;
        }

        return this.page;
    }

    @Override
    public void beforeTag() {
        addAttribute("opFileList", this.fileList);
        addAttribute("opFileInputName", this.name);
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
     * name을 설정
     * 
     * @param name 을(를) String name로 설정
     */
    public void setName(String name) {
        this.name = name;
    }

}
