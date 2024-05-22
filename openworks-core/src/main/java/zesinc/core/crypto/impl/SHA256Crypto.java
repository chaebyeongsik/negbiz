/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.crypto.impl;

import java.security.MessageDigest;

import zesinc.core.crypto.Crypto;
import zesinc.core.lang.Validate;
import zesinc.core.misc.base64.Base64Encoder;

/**
 * SHA256 복호화 불가능한 암호화
 * setKey() 메소드를 통하여 key를 설정하는 경우 salt를 적용하여 암호화한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 23.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class SHA256Crypto implements Crypto {

    /** salt 키 */
    private String saltKey;

    @Override
    public String encrypt(String encStr) {
        String result = "";
        try {
            byte[] bytes = encStr.getBytes();

            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.reset();
            if(Validate.isNotEmpty(saltKey)) {
                sha256.update(saltKey.getBytes());
            }
            byte[] hash = sha256.digest(bytes);

            result = Base64Encoder.encode(hash);
        } catch (Exception e) {
            result = encStr;
        }

        return result;
    }

    @Override
    public String decrypt(String decStr) {

        return null;
    }

    @Override
    public Crypto setKey(String cryptoKey) {
        this.saltKey = cryptoKey;

        return this;
    }

}
