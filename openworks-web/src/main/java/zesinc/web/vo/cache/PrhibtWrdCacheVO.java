/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo.cache;

import zesinc.web.vo.BaseVO;

/**
 * 금지단어 정보 케시 VO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-06-17.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class PrhibtWrdCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = 1180355475284373487L;

    /** 금지단어코드 */
    private String phbwdCdId;

    /** 제목 */
    private String ttl;

    /** 금지단어내용 */
    private String phbwdCn;

    /** 사용여부 */
    private String useYn;

    /**
     * 금지단어코드 반환
     * 
     * @return String phbwdCdId
     */
    public String getPhbwdCdId() {
        return phbwdCdId;
    }

    /**
     * 금지단어코드 설정
     * 
     * @param phbwdCdId 을(를) String phbwdCdId로 설정
     */
    public void setPhbwdCdId(String phbwdCdId) {
        this.phbwdCdId = phbwdCdId;
    }

    /**
     * 제목 설정
     * 
     * @param ttl을(를) String ttl로 설정
     */
    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    /**
     * 제목 반환
     * 
     * @return String ttl
     */
    public String getTtl() {
        return ttl;
    }

    /**
     * 금지단어내용 설정
     * 
     * @param phbwdCn을(를) String phbwdCn로 설정
     */
    public void setPhbwdCn(String phbwdCn) {
        this.phbwdCn = phbwdCn;
    }

    /**
     * 금지단어내용 반환
     * 
     * @return String phbwdCn
     */
    public String getPhbwdCn() {
        return phbwdCn;
    }

    /**
     * 사용여부 설정
     * 
     * @param useYn을(를) String useYn로 설정
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * 사용여부 반환
     * 
     * @return String useYn
     */
    public String getUseYn() {
        return useYn;
    }

}
