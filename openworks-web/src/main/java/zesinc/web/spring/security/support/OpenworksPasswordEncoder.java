/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.security.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import zesinc.core.lang.Validate;
import zesinc.web.utils.CryptoUtil;
import zesinc.web.utils.PasswdUtil;

/**
 * SpringFramework Security 인증시 사용될 비밀번호 암호화 및 비교 클레스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 28.    방기배   최초작성
 *  2019. 8. 23.    황신욱  암호화전송된 비밀번호 복호화 후 matche
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class OpenworksPasswordEncoder implements PasswordEncoder {

    /** SL4J 로깅 */
    private static Logger logger = LoggerFactory.getLogger(OpenworksPasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPswd) {

        String passwd = String.valueOf(rawPswd);
        if(Validate.isEmpty(rawPswd)) {
            logger.debug("Pswd is null -------------------------------------------------");
            return null;
        }

//        return PasswdUtil.encodePasswd(passwd);
        return PasswdUtil.encode(passwd);
    }

    @Override
    public boolean matches(CharSequence rawPswd, String encodedPswd) {

        try {
            if(Validate.isEmpty(rawPswd) || Validate.isEmpty(encodedPswd)) {
                logger.debug("Pswd is null == input pswd is {} : user info pswd is {}", rawPswd, encodedPswd);
                return false;
            }
            logger.debug("input pswd is {} ({})", rawPswd, CryptoUtil.decrypt(String.valueOf(rawPswd)));

//            return PasswdUtil.matche(CryptoUtil.decrypt(String.valueOf(rawPswd)), String.valueOf(encodedPswd));
            return PasswdUtil.matches(CryptoUtil.decrypt(String.valueOf(rawPswd)), String.valueOf(encodedPswd));
        } catch (Exception e) {
            return false;
        }
    }

}
