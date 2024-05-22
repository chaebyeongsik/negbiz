/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils.image;

/**
 * 이미지 사이즈 VO 객체
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 11. 29.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class TargetImageSize {

    private int width;
    private int height;

    public TargetImageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * int width을 반환
     * 
     * @return int width
     */
    public int getWidth() {
        return width;
    }

    /**
     * width을 설정
     * 
     * @param width 을(를) int width로 설정
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * int height을 반환
     * 
     * @return int height
     */
    public int getHeight() {
        return height;
    }

    /**
     * height을 설정
     * 
     * @param height 을(를) int height로 설정
     */
    public void setHeight(int height) {
        this.height = height;
    }

}
