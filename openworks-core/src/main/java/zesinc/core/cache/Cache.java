/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.cache;

/**
 * 케시클레스 인터페이스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 11. 21.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public interface Cache {

    /**
     * 케시 데이터 생성
     */
    void createCache();

    /**
     * 케시 데이터 재 생성
     */
    void reloadCache();

    /**
     * 도메인별 케시 리로딩
     * 
     * @param siteSn
     */
    void reloadCache(Integer siteSn);

    /**
     * 케시 데이터 명
     * 
     * @return
     */
    String getName();

    /**
     * 캐시 저장위치
     * 
     * @return
     */
    CacheLocation getLocation();
}
