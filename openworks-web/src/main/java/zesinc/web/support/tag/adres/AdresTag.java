/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.adres;

import zesinc.web.support.tag.OpTagSupport;

/**
 * TODO 설명을 입력하세요.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 10. 19.    ZES-INC   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class AdresTag extends OpTagSupport {

    /** JSP 페이지 */
    private static String JSP_PAGE = "adres/adresScript.jsp";

    /** 주소찾기버튼 */
    private String btnClass = "adresPopBtn";

    /** 우편번호 */
    private String zip = "zip";

    /** 주소 */
    private String adres = "userAddr";

    /** 주소2 */
    private String adres2 = "daddr";

    @Override
    protected String getPage() {
        if(this.page == null) {
            return JSP_PAGE;
        }

        return this.page;
    }

    /**
     * btnClass을 설정
     * 
     * @param btnClass 을(를) String btnClass로 설정
     */
    public void setBtnClass(String btnClass) {
        this.btnClass = btnClass;
    }

    /**
     * zip을 설정
     * 
     * @param zip 을(를) String zip로 설정
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * adres을 설정
     * 
     * @param adres 을(를) String adres로 설정
     */
    public void setAdres(String adres) {
        this.adres = adres;
    }

    /**
     * adres2을 설정
     * 
     * @param adres2 을(를) String adres2로 설정
     */
    public void setAdres2(String adres2) {
        this.adres2 = adres2;
    }

    @Override
    public void beforeTag() {
        addAttribute("adresPopBtn", btnClass);
        addAttribute("zip", zip);
        addAttribute("adres", adres);
        addAttribute("adres2", adres2);
    }
}
