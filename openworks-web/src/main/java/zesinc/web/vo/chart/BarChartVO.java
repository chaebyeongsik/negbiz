/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo.chart;

import java.io.Serializable;

/**
 * 단순 바 그래프 데이터용 VO
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 9. 24.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class BarChartVO implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -8698946448038240316L;

    /** 이름 */
    private String label;

    /** 값 */
    private float value;

    /** 설명 */
    private String desc;

    /**
     * String label을 반환
     * 
     * @return String label
     */
    public String getLabel() {
        return label;
    }

    /**
     * label을 설정
     * 
     * @param label 을(를) String label로 설정
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * float value을 반환
     * 
     * @return float value
     */
    public float getValue() {
        return value;
    }

    /**
     * value을 설정
     * 
     * @param value 을(를) float value로 설정
     */
    public void setValue(float value) {
        this.value = value;
    }

    /**
     * String desc을 반환
     * 
     * @return String desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * desc을 설정
     * 
     * @param desc 을(를) String desc로 설정
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

}
