/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.compare;

import java.io.Serializable;

import difflib.DiffRow;
import difflib.DiffRow.Tag;

/**
 * 데이터 가공을 위해 자체 로직을 조금 추가한 VO 클레스를 사용한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 20.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CompareViewVO implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 5524865972082565655L;

    /** 줄 번호 */
    private Integer lineNum;
    /** 결과 유형 */
    private String tag;
    /** 결과 유형 한글명 */
    private String tagName;
    /** 우측 내용 */
    private String rightLine;
    /** 좌측 내용 */
    private String leftLine;

    /**
     * 결과 유형 한글명을 설정하며 초기화 한다.
     * 
     * @param row
     */
    public CompareViewVO(DiffRow row) {

        String tag = "";// all, add, delete, modify, same
        String tagName = "";

        Tag diffTag = row.getTag();
        switch(diffTag) {
            case INSERT:
                tag = "add";
                tagName = "추가";
                break;
            case CHANGE:
                tag = "modify";
                tagName = "변경";
                break;
            case DELETE:
                tag = "delete";
                tagName = "삭제";
                break;
            default:
                tag = "same";
                tagName = "동일";
                break;
        }
        this.tagName = tagName;
        this.tag = tag;
        this.rightLine = row.getNewLine();
        this.leftLine = row.getOldLine();
    }

    /**
     * Integer lineNum을 반환
     * 
     * @return Integer lineNum
     */
    public Integer getLineNum() {
        return lineNum;
    }

    /**
     * lineNum을 설정
     * 
     * @param lineNum 을(를) Integer lineNum로 설정
     */
    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * String tag을 반환
     * 
     * @return String tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * setTag 설명
     * 
     * @param tag
     * @return <code>this</code> 자기 자신
     */
    public CompareViewVO setTag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     * String tagName을 반환
     * 
     * @return String tagName
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * tagName을 설정
     * 
     * @param tagName 을(를) String tagName로 설정
     * @return <code>this</code> 자기 자신
     */
    public CompareViewVO setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    /**
     * String rightLine을 반환
     * 
     * @return String rightLine
     */
    public String getRightLine() {
        return rightLine;
    }

    /**
     * rightLine을 설정
     * 
     * @param rightLine 을(를) String rightLine로 설정
     * @return <code>this</code> 자기 자신
     */
    public CompareViewVO setRightLine(String rightLine) {
        this.rightLine = rightLine;
        return this;
    }

    /**
     * String leftLine을 반환
     * 
     * @return String leftLine
     */
    public String getLeftLine() {
        return leftLine;
    }

    /**
     * leftLine을 설정
     * 
     * @param leftLine 을(를) String leftLine로 설정
     * @return <code>this</code> 자기 자신
     */
    public CompareViewVO setLeftLine(String leftLine) {
        this.leftLine = leftLine;
        return this;
    }

}
