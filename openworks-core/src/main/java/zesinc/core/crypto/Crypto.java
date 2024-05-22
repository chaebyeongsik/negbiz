/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.crypto;

/**
 * 암 복호화 표준 타입 인터페이스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일        수정자       수정내용
 * --------------   --------     -------------------------------
 *  2014. 9. 24.    방기배     최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public interface Crypto {

    /**
     * 문자열 암호화
     * 
     * @param encStr
     * @return
     */
    String encrypt(String encStr);

    /**
     * 문자열 복호화
     * 
     * @param decStr
     * @return
     * @throws Exception
     */
    String decrypt(String decStr);

    /**
     * 키 문자열을 설정한다.
     * 단! SHA-256 암호화의 경우는 salt로 사용된다.
     * <p />
     * 키 파일은 ClassPath 로 설정한다.<br />
     * 예) zes/core/lang/crypto/descrypto.key.ser
     * 
     * @param cryptoKey
     * @return
     */
    Crypto setKey(String cryptoKey);
}
