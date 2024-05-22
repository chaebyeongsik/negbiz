/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.bbsTmplat;

import zesinc.core.lang.Validate;
import zesinc.web.support.tag.bbsTmplat.support.BbsUiTagSupport;

/**
 * 게시판템플릿 태그 : 저작권사용여부
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
public class CpyrhtTag extends BbsUiTagSupport {

    /** jsp */
    private static final String DEFAULT_JSP = "bbsTmplat/cpyrht.jsp";

    /**
     * 필드명 설정
     */
    public CpyrhtTag() {
        this.fieldNm = "cpyrht";
    }

    @Override
    protected String getPage() {

        if(Validate.isEmpty(this.page)) {
            return DEFAULT_JSP;
        }

        return this.page;
    }

    @Override
    public void beforeTag() {

        addAttribute("bbsObj", getObj());
        addAttribute("bbsFieldNm", this.fieldNm);
        addAttribute("bbsType", this.type);
        addAttribute("bbsItemVo", getItem());

        // 저작권사용여부
        String cprgtUseYn = getString("cprgtUseYn");
        if(Validate.isNotEmpty(cprgtUseYn) && cprgtUseYn.equals("Y")) {

            // 관리자/사용자 구분 (사용자만 파싱)
            String autTypeNm = getString("autTypeNm");
            if(autTypeNm.equals("USER")) {
                String cprgtTypeNm = getString("cprgtTypeNm");
                if(Validate.isNotEmpty(this.type) && Validate.isNotEmpty(cprgtTypeNm)) {
                    if(this.type.equals("form")) {
                        String[] arrCprgtTypeNm = cprgtTypeNm.split("-", 3);
                        addAttribute("authrIndict", arrCprgtTypeNm[0]);
                        addAttribute("prftmkPurpsPerm", arrCprgtTypeNm[1]);
                        addAttribute("cntntsChangePerm", arrCprgtTypeNm[2]);
                    } else if(this.type.equals("value") || this.type.equals("view")) {
                        if(cprgtTypeNm.endsWith("-")) {
                            cprgtTypeNm = cprgtTypeNm.substring(0, cprgtTypeNm.length() - 1);
                        } else {
                            cprgtTypeNm = cprgtTypeNm.replaceAll("--", "-");
                        }
                        addAttribute("OpbbsCprgtTypeNm", cprgtTypeNm);
                    }
                }
            }

        }

    }
}
