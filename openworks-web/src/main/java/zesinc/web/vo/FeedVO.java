/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo;

import java.sql.Date;
import java.util.List;

/**
 * RSS, Atom Feed VO
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 4.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class FeedVO extends BaseVO {

    private static final long serialVersionUID = 4314981010698028031L;

    /** URL 생성을 위한 파라미터1 */
    private String param1;

    /** URL 생성을 위한 파라미터2 */
    private String param2;

    /** URL 생성을 위한 파라미터3 */
    private String param3;

    /** URL 생성을 위한 파라미터4 */
    private String param4;

    /** 제목 */
    private String title;

    /** 요약 부분 */
    private String description;

    /** 본페이지 링크 */
    private String link;

    /** 작성자 */
    private String author;

    /** 작성일 */
    private Date pubDate;

    /** FEED 목록 */
    private List<FeedVO> itemList;

    /**
     * String param1을 반환
     * 
     * @return String param1
     */
    public String getParam1() {
        return param1;
    }

    /**
     * param1을 설정
     * 
     * @param param1 을(를) String param1로 설정
     */
    public void setParam1(String param1) {
        this.param1 = param1;
    }

    /**
     * String param2을 반환
     * 
     * @return String param2
     */
    public String getParam2() {
        return param2;
    }

    /**
     * param2을 설정
     * 
     * @param param2 을(를) String param2로 설정
     */
    public void setParam2(String param2) {
        this.param2 = param2;
    }

    /**
     * String param3을 반환
     * 
     * @return String param3
     */
    public String getParam3() {
        return param3;
    }

    /**
     * param3을 설정
     * 
     * @param param3 을(를) String param3로 설정
     */
    public void setParam3(String param3) {
        this.param3 = param3;
    }

    /**
     * String param4을 반환
     * 
     * @return String param4
     */
    public String getParam4() {
        return param4;
    }

    /**
     * param4을 설정
     * 
     * @param param4 을(를) String param4로 설정
     */
    public void setParam4(String param4) {
        this.param4 = param4;
    }

    /**
     * String title을 반환
     * 
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * title을 설정
     * 
     * @param title 을(를) String title로 설정
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * String description을 반환
     * 
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * description을 설정
     * 
     * @param description 을(를) String description로 설정
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * String link을 반환
     * 
     * @return String link
     */
    public String getLink() {
        return link;
    }

    /**
     * link을 설정
     * 
     * @param link 을(를) String link로 설정
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * String author을 반환
     * 
     * @return String author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * author을 설정
     * 
     * @param author 을(를) String author로 설정
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Date pubDate을 반환
     * 
     * @return Date pubDate
     */
    public Date getPubDate() {
        return pubDate;
    }

    /**
     * pubDate을 설정
     * 
     * @param pubDate 을(를) Date pubDate로 설정
     */
    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * List<BaseFeedVO> itemList을 반환
     * 
     * @return List<BaseFeedVO> itemList
     */
    public List<FeedVO> getItemList() {
        return itemList;
    }

    /**
     * itemList을 설정
     * 
     * @param itemList 을(를) List<BaseFeedVO> itemList로 설정
     */
    public void setItemList(List<FeedVO> itemList) {
        this.itemList = itemList;
    }

}
