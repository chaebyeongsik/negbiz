/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.javascript.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 화면 단에 자바 환경 설정 정보를 json 형식으로 전달하기 위한 VO 클레스
 * 
 * @author (주)제스아이엔씨 기술연구소
 * 
 *         <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 1. 11.    방기배   최초작성
 * </pre>
 * @see
 */
public class JavaScriptConfigVO implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 4599971500737340841L;

    private List<String> rootKey;

    /**
     * List<String> rootKey을 반환
     * 
     * @return List<String> rootKey
     */
    public List<String> getRootKey() {
        return rootKey;
    }

    /**
     * rootKey을 설정
     * 
     * @param rootKey 을(를) List<String> rootKey로 설정
     */
    public void setRootKey(List<String> rootKey) {
        this.rootKey = rootKey;
    }
}
