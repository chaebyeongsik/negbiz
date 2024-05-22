/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo.cache;

import java.io.Serializable;

/**
 * 다국어 캐시용 VO 객체
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 18.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class MultilangCacheVO implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 6813274017042522706L;

    /** 다국어키 */
    private String mtlngUnqCdNm;

    /** 다국어코드 */
    private String mtlngCdNm;

    /** 언어코드 */
    private String langCdId;

    /** 메시지 */
    private String multiLangMsgCn;

    /** 사용여부 */
    private String useYn;

    /**
     * String mtlngUnqCdNm을 반환
     * 
     * @return String mtlngUnqCdNm
     */
    public String getMtlngUnqCdNm() {
        return mtlngUnqCdNm;
    }

    /**
     * mtlngUnqCdNm을 설정
     * 
     * @param mtlngUnqCdNm 을(를) String mtlngUnqCdNm로 설정
     */
    public void setMtlngUnqCdNm(String mtlngUnqCdNm) {
        this.mtlngUnqCdNm = mtlngUnqCdNm;
    }

    /**
     * String mtlngCdNm을 반환
     * 
     * @return String mtlngCdNm
     */
    public String getMtlngCdNm() {
        return mtlngCdNm;
    }

    /**
     * mtlngCdNm을 설정
     * 
     * @param mtlngCdNm 을(를) String mtlngCdNm로 설정
     */
    public void setMtlngCdNm(String mtlngCdNm) {
        this.mtlngCdNm = mtlngCdNm;
    }

    /**
     * String langCdId을 반환
     * 
     * @return String langCdId
     */
    public String getLangCdId() {
        return langCdId;
    }

    /**
     * langCdId을 설정
     * 
     * @param langCdId 을(를) String langCdId로 설정
     */
    public void setLangCdId(String langCdId) {
        this.langCdId = langCdId;
    }

    /**
     * String multiLangMsgCn을 반환
     * 
     * @return String multiLangMsgCn
     */
    public String getMultiLangMsgCn() {
        return multiLangMsgCn;
    }

    /**
     * multiLangMsgCn을 설정
     * 
     * @param multiLangMsgCn 을(를) String multiLangMsgCn로 설정
     */
    public void setMultiLangMsgCn(String multiLangMsgCn) {
        this.multiLangMsgCn = multiLangMsgCn;
    }

    /**
     * String useYn을 반환
     * 
     * @return String useYn
     */
    public String getUseYn() {
        return useYn;
    }

    /**
     * useYn을 설정
     * 
     * @param useYn 을(를) String useYn로 설정
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

}
