/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.bbsTmplat;

import java.util.ArrayList;
import java.util.List;

import zesinc.core.lang.Validate;
import zesinc.web.support.tag.bbsTmplat.support.BbsUiTagSupport;
import zesinc.web.vo.IFileVO;
import zesinc.web.vo.cache.BbsConfigCacheVO;

/**
 * 게시판템플릿 태그 : 파일일련번호
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 7. 17.    황신욱   최초작성
 *  2015. 11. 1.    방기배   재작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ThumbNailTag extends BbsUiTagSupport {

    /** 미지정 시 기본 화면 JSP 템플릿 */
    private static final String DEFAULT_JSP = "bbsTmplat/thumbnail.jsp";

    /**
     * 필드명 설정
     */
    public ThumbNailTag() {
        this.fieldNm = "thumbnail";
    }

    @Override
    protected String getPage() {
        if(Validate.isEmpty(page)) {
            return DEFAULT_JSP;
        }

        return this.page;
    }

    @Override
    public void beforeTag() {
        addAttribute("bbsObj", getObj());
        addAttribute("bbsType", this.type);

        BbsConfigCacheVO bbsConfigVo = getConfig();
        if(Validate.isNotEmpty(this.type) && (this.type.equals("list") || this.type.equals("view"))) {
            if(bbsConfigVo.getThmbUseYn().equals("Y")) {
                List<IFileVO> thumbList = new ArrayList<IFileVO>();
                @SuppressWarnings("unchecked")
                List<IFileVO> fileList = (List<IFileVO>) getValue("fileList");
                if(Validate.isNotEmpty(fileList)) {
                    for(IFileVO fileVo : fileList) {
                        if(Validate.isNotEmpty(fileVo.getThmbPathNm())) {
                            thumbList.add(fileVo);
                        }
                    }
                }
                addAttribute("bbsThumbList", thumbList);
            }
        }
    }
}
