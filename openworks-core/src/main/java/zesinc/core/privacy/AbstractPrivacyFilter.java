/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.core.privacy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.core.config.Config;
import zesinc.core.lang.Validate;

/**
 * <pre>
 * 파일 내에 아래 개인정보를 포함하고 있는지 제공된 정규표현식으로 검증한다.
 * 
 * 주민번호, 여권번호, 운전면허 번호, 핸드폰번호, 신용카드번호, 건강보험번호, 계좌번호
 * 
 * << 개정이력(Modification Information) >>
 * 
 *     수정일               수정자                  수정내용
 * --------------  --------  -------------------------------
 *  
 * 
 *  2013. 06. 04.       (주)제스아이앤씨         신규등록
 * </pre>
 */
public abstract class AbstractPrivacyFilter implements PrivacyFilter {

    /** SL4J 로깅 */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 정규표현식 목록 */
    public static final List<Pattern> REG_EXPS = new ArrayList<Pattern>();

    public static List<Object> REGEXPS;

    static {
        REGEXPS = Config.getList("privacy-config.privacyRegexp");

        for(Object regexp : REGEXPS) {
            REG_EXPS.add(Pattern.compile((String) regexp, Pattern.CASE_INSENSITIVE));
        }
    }

    /** 문자열 또는 파일 경로 */
    protected String content;

    /** 파일 객체 **/
    protected File file;

    /**
     * 문자열에 개인정보가 포함되어 있는지 확인하고 결과를 반환한다.
     * 
     * @param content 검사대상 문자열 또는 경로를 포함한 전체 파일명
     * @return
     */
    protected PrivacyResultVO doPrivacyCheck(String content) {

        PrivacyResultVO result = new PrivacyResultVO();

        if(Validate.isNotEmpty(content)) {
            Matcher matcher;
            for(Pattern pattern : REG_EXPS) {
                matcher = pattern.matcher(content);
                while(matcher.find()) {
                    result.addFileterList(matcher.group());
                }
            }
        }
        if(result.getFilterList().size() > 0) {
            result.setResult(true);
        }
        return result;
    }
}
