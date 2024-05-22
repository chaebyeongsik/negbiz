/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.crypto;

import zesinc.core.config.Config;
import zesinc.core.crypto.impl.ARIACrypto;
import zesinc.core.crypto.impl.SEEDCrypto;
import zesinc.core.crypto.impl.SHA256Crypto;

/**
 * 암호화 모듈을 반환한다.
 * 지원되는 암호화 방식은 SEED, ARIA, SHA256 타입이다. <br />
 * 암호화 모듈 별칭으로 지정하지 않는 경우 프레임워크 기본 설정에 따라
 * 암호화 모듈이 반환된다.
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
 * @see GlobalConfig
 */
public class CryptoFactory {

    /** 암복호화 유형이 지정되지 않은 경우 환경설정의 기본 값을 사용 */
    public static final String DEFAULT_CRYPTO = Config.getString("system-config.defaultEnctype");

    /** 암복호화 유형 */
    public enum EncryptType {
        SEED, ARIA, SHA256
    }

    /**
     * 프레임 워크 설정 기본 암호화 모듈 반환
     * 
     * @return
     */
    public static Crypto getInstance() {

        return getInstance(EncryptType.valueOf(DEFAULT_CRYPTO));
    }

    /**
     * 별칭으로 암호화 모듈 반환
     * EncryptType.SEED, EncryptType.ARIA, EncryptType.SHA256 중 하나를 인자로 사용
     * 
     * @param cryptoType
     * @return
     */
    public static Crypto getInstance(EncryptType cryptoType) {
        if(EncryptType.SEED.equals(cryptoType)) {
            return new SEEDCrypto();
        } else if(EncryptType.ARIA.equals(cryptoType)) {
            return new ARIACrypto();
        } else if(EncryptType.SHA256.equals(cryptoType)) {
            return new SHA256Crypto();
        } else {
            new Throwable("지원하지 않는 암호화 방식입니다.");
        }
        return null;
    }

}
