/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.core.privacy.impl;

import zesinc.core.privacy.AbstractPrivacyFilter;
import zesinc.core.privacy.PrivacyFilter;
import zesinc.core.privacy.PrivacyResultVO;

/**
 * 단순 문자열에서 개인정보 포함 여부 확인
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일               수정자                  수정내용
 * --------------  --------  -------------------------------
 *  
 * 
 *  2013. 06. 04.       (주)제스아이앤씨         개인정보 필터링
 * </pre>
 */
public class PrivacyStringFilter extends AbstractPrivacyFilter implements PrivacyFilter {

    public PrivacyStringFilter(String content) {
        this.content = content;
    }

    /*
     * 단순 문자열내에 개인정보가 포함되어있는지 검사
     * @see zesinc.privacy.PrivacyFilter#doFilter()
     */
    @Override
    public PrivacyResultVO doFilter() {

        return doPrivacyCheck(this.content);
    }

}
