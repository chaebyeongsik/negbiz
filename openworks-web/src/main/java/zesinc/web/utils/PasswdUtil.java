/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

import zesinc.core.config.Config;
import zesinc.core.crypto.Crypto;
import zesinc.core.crypto.CryptoFactory;
import zesinc.core.crypto.CryptoFactory.EncryptType;
import zesinc.core.lang.Validate;
import zesinc.core.misc.base64.Base64Encoder;

/**
 * 비밀번호 취급관련 유틸 클레스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 10. 15.    방기배   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class PasswdUtil {

    /** 자동생성 비밀번호 길이 */
    public static final int DEFAULT_PASSWD_LENGTH = Config.getInt("passwd-config.defaultPasswdLength");
    /** 비밀번호 갱신주기 */
    public static final int CHANGE_PSWD_TERM = Config.getInt("passwd-config.changePasswordTerm");
    /** 비밀번호 최소 길이 */
    public static final int PASSWD_MIN_LENGTH = Config.getInt("passwd-config.passwdMinLength");
    /** 비밀번호 최대 길이 */
    public static final int PASSWD_MAX_LENGTH = Config.getInt("passwd-config.passwdMaxLength");
    /** 동일문자 제한 횟수 */
    public static final int PASSWD_ALLOW_REPEAT = Config.getInt("passwd-config.passwdAllowRepeat");
    /** 공백문자 허용여부 */
    public static final boolean PASSWD_ALLOW_WHITESPACE = Config.getBoolean("passwd-config.passwdAllowWhiteSpace");
    /** 숫자만인 경우 허용여부 */
    public static final boolean PASSWD_ALLOW_ALL_DIGIT = Config.getBoolean("passwd-config.passwdAllowAllDigit");
    /** 이전비밀번호 재사용가능여부 */
    public static final boolean PASSWD_ALLOW_PRV_PWD = Config.getBoolean("passwd-config.passwdAllowPrvPwd");

    // 아래 3개는 특별한 경우 즉. 표준 보안 지침을 지키지 않는 경우 설정을 추가하여 변경할 수 있다.
    // 기본으로는 숨긴 상태라고 생각하면 된다. 가능한 설정을 추가하지 말고.. 지침을 준수하도록 한다.
    /** 문자종류에 따른 보안적용 수치. 기본값은 지침에 따른 값 */
    public static final int BASE_LENGTH = Config.getInt("passwd-config.baseLength", 10);
    /** 최대 혼합 문자 종류수. 기본값은 지침에 따른 값 */
    public static final int MAX_MIX_CNT = Config.getInt("passwd-config.maxMixCnt", 3);
    /** 최소 혼합 문자 종류수. 기본값은 지침에 따른 값 */
    public static final int MIN_MIX_CNT = Config.getInt("passwd-config.minMixCnt", 2);

    /** 비밀번호로 사용할 수 있는 문자 집합 */
    private static char[] chars;
    /** 비밀번호로 사용할 수 있는 숫자 집합 */
    private static char[] numbers;
    /** 비밀번호로 사용할 수 있는 특수문자 집합 */
    private static char[] specials;
    /** 사용가능한 모든 문자 집합 */
    private static char[] allChars;

    static {
        String strChars = Config.getString("passwd-config.passwdChars.chars");
        if(Validate.isNotEmpty(strChars)) {
            chars = strChars.toCharArray();
        } else {
            chars = "".toCharArray();
        }

        String strNumbers = Config.getString("passwd-config.passwdChars.numbers");
        if(Validate.isNotEmpty(strNumbers)) {
            numbers = strNumbers.toCharArray();
        } else {
            numbers = "".toCharArray();
        }

        String strSpecials = Config.getString("passwd-config.passwdChars.specials");
        if(Validate.isNotEmpty(strSpecials)) {
            specials = strSpecials.toCharArray();
        } else {
            specials = "".toCharArray();
        }

        String allStr = strChars + strNumbers + strSpecials;
        allChars = allStr.toCharArray();
    }

    /**
     * <pre>
     * 회원가입등 최초 비밀번호 생성시 사용 !!<p />
     * 비밀번호 규칙 준수 여부.
     * 공백문자 포함여부/숫자만인지 여부/특정문자가 지정된 수치만큼 포함되었는지/
     * 이전 비밀번호와 동일한지 확인하여 하나라도 해당되는 경우 false를 반환
     * 단 이전 암호화된 비밀번호가 null 인경우 동일 비번은 비교하지 않는다.
     * </pre>
     *
     * @param newPasswd
     * @return 준수기준 만족시 true, 이외 false
     */
    public static boolean isAllowPasswd(String newPasswd) {

        return isAllowPasswd(newPasswd, null, null);
    }

    /**
     * <pre>
     * 기존 사용자가 비밀번호를 변경할 경우 사용 !!<p />
     * 비밀번호 규칙 준수 여부.
     * 공백문자 포함여부/숫자만인지 여부/특정문자가 지정된 수치만큼 포함되었는지/
     * 이전 비밀번호와 동일한지 확인하여 하나라도 해당되는 경우 false를 반환
     * 단 이전 암호화된 비밀번호가 null 인경우 동일 비번은 비교하지 않는다.
     * </pre>
     *
     * @param newPasswd 신규입력 비밀번호
     * @param oldEncPasswd 이전 사용중인 비밀번호
     * @param sessVo 회원정보(개인정보가 비밀번호에 포함되었는지 조회하기 위한 정보)
     * @return 준수기준 만족시 true, 이외 false
     */
    public static boolean isAllowPasswd(String newPasswd, String oldEncPasswd, Object sessVo) {

        int passwdLength = newPasswd.length();
        // 비밀번호 사용제한 길이
        if(passwdLength < PASSWD_MIN_LENGTH || passwdLength > PASSWD_MAX_LENGTH) {
            return false;
        }
        // 공백문자 포함여부
        if(!PASSWD_ALLOW_WHITESPACE && Validate.hasWhitespace(newPasswd)) {
            return false;
        }
        // 숫자만으로 이루어 졌는지 여부
        if(!PASSWD_ALLOW_ALL_DIGIT && Validate.isDigits(newPasswd)) {
            return false;
        }

        // 신규 등록시(이전 비밀번호가 없는 경우)
        if(Validate.isNotEmpty(oldEncPasswd)) {
            // 이전 비밀번호화 동일한지 여부
            if(!PASSWD_ALLOW_PRV_PWD && matche(newPasswd, oldEncPasswd)) {
                return false;
            }
        }

        // 비번 조합 문자 확인
        char[] passwds = newPasswd.toCharArray();

        // 비허용 문자 확인
        for(char passwd : passwds) {
            boolean match = false;
            for(char allChar : allChars) {
                if(passwd == allChar) {
                    match = true;
                }
            }
            if(!match) {
                return false;
            }
        }

        int mixCnt = 0;
        boolean containChar = false;
        boolean containSpecial = false;
        boolean containDigit = false;

        for(char passwd : passwds) {
            // 특수문자 포함여부
            for(char special : specials) {
                if(!containSpecial && passwd == special) {
                    containSpecial = true;
                    mixCnt++;
                }
            }
            // 숫자 포함여부
            if(!containDigit && Character.isDigit(passwd)) {
                containDigit = true;
                mixCnt++;
            }
            // 문자 포함여부
            if(!containChar && ((passwd >= 'a' && passwd <= 'z') || (passwd >= 'A' && passwd <= 'Z'))) {
                containChar = true;
                mixCnt++;
            }
            // 3종류 문자 사용 만족시 중지
            if(mixCnt > 2) {
                break;
            }
        }
        // 지침상으로는 10자 미만인 경우 3종류 문자 사용 필수
        if(passwdLength < BASE_LENGTH && mixCnt < MAX_MIX_CNT) {
            return false;
        }
        // 지침상으로는 10자 이상 인경우 2종류 문자 사용 필수
        if(passwdLength >= BASE_LENGTH && mixCnt < MIN_MIX_CNT) {
            return false;
        }

        char[] chars = newPasswd.toCharArray();

        Map<Character, Integer> countMap = new HashMap<Character, Integer>();
        Character charKey;

        for(char ch : chars) {
            charKey = new Character(ch);
            if(countMap.containsKey(charKey)) {
                Integer count = countMap.get(charKey);
                if(count >= PASSWD_ALLOW_REPEAT) {

                    return false;
                }
                countMap.put(charKey, count + 1);
            } else {
                countMap.put(charKey, 1);
            }
        }
        countMap.clear();
        countMap = null;

        return true;
    }

    /**
     * 비밀번호 암호화 문자열 생성
     * salt 문자열은 자동으로 생성하며 보안 권고에 따라 10회 적용된 후 결과를 생성한다.
     *
     * @param passwd 암호화 대상 비밀번호 문자열
     * @return 암호화된 비밀번호
     * @throws Exception
     */
    public static String encodePasswd(String passwd) {

        String salt = null;
        if(Validate.isNotEmpty(passwd)) {
            if(passwd.length() > 5) {
                salt = passwd.substring(0, 5);
            } else {
                salt = passwd;
            }
        }

        return encodePasswd(passwd, salt);
    }

    /**
     * 비밀번호 암호화 문자열 생성
     * salt 는 보안 권고에 따라 10회 적용된 후 결과를 생성한다.
     *
     * @param passwd 암호화 대상 비밀번호 문자열
     * @param salt salt 적용 문자열
     * @return 암호화된 비밀번호
     * @throws Exception
     */
    public static String encodePasswd(String passwd, String salt) {

        String encPasswd = passwd;

        if(Validate.isNotEmpty(passwd)) {
            Crypto cry = CryptoFactory.getInstance(EncryptType.SHA256);
            cry.setKey(salt);

            for(int i = 0 ; i < 10 ; i++) {
                encPasswd = cry.encrypt(encPasswd);
            }
        }

        encPasswd = Base64Encoder.encode(encPasswd);

        return encPasswd;
    }

    /**
     * 암호화된 비밀번호와 입력받은 문자열의 암호와 결과와 같은지 비교한다.
     *
     * @param rawPasswd 평문장의 비밀번호
     * @param encodedPasswd 암호화되어 저장되어 있던 비밀번호
     * @return
     */
    public static boolean matche(String rawPasswd, String encodedPasswd) {
        String encPasswd = encodePasswd(rawPasswd);

        return (encPasswd.equals(encodedPasswd) ? true : false);
    }

    /**
     * 비밀번호 암호화 문자열 생성
     * 랜덤salt를 사용하여 비밀번호 암호화를 한다.
     *
     * @param passwd 암호화 대상 비밀번호 문자열
     * @return 암호화된 비밀번호
     * @throws Exception
     */
    public static String encode(String passwd) {

        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();

        return standardPasswordEncoder.encode(passwd);
    }

    /**
     * 랜덤salt로 암호화된 비밀번호와 입력받은 문자열의 암호와 결과와 같은지 비교한다.
     *
     * @param rawPasswd 평문장의 비밀번호
     * @param encodedPasswd 암호화되어 저장되어 있던 비밀번호
     * @return
     */
    public static boolean matches(String rawPasswd, String encodedPasswd) {
        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();

        return standardPasswordEncoder.matches(rawPasswd, encodedPasswd);
    }

    /**
     * <pre>
     * 비밀번호 자동 생성
     * 환경설정의 비밀번호 길이 수에 따라서 생성하며, 숫자와 특수문자를 포함한다.
     * /config/commons/passwd-commons-config.xml 설정을 참조한다.
     * </pre>
     *
     * @return
     */
    public static String generatePasswd() {

        int skipCnt = 0;
        int ramdom = 0;

        char[] passwd = new char[DEFAULT_PASSWD_LENGTH];
        Random rd = new Random();

        if(numbers.length > 0) {
            for(int i = 1 ; i <= 2 ; i++) {
                ramdom = rd.nextInt(numbers.length - 1);
                passwd[DEFAULT_PASSWD_LENGTH - i] = numbers[ramdom];
                skipCnt++;
            }
        }

        if(specials.length > 0) {
            for(int i = 3 ; i < 5 ; i++) {
                ramdom = rd.nextInt(specials.length - 1);
                passwd[DEFAULT_PASSWD_LENGTH - i] = specials[ramdom];
                skipCnt++;
            }
        }

        if(chars.length > 0) {
            for(int i = 0 ; i < DEFAULT_PASSWD_LENGTH - skipCnt ; i++) {
                ramdom = rd.nextInt(chars.length - 1);
                passwd[i] = chars[ramdom];
            }
        }
        // 패턴을 없애기 위한 섞기
        shuffle(passwd);

        return new String(passwd);
    }

    /**
     * char 배열의 값을 무작위로 뒤섞는다.
     *
     * @param chars 뒤섞을 배열
     */
    private static void shuffle(char[] chars) {
        int n = chars.length;
        Random random = new Random();
        random.nextInt();

        for(int i = 0 ; i < n ; i++) {
            int change = i + random.nextInt(n - i);
            swap(chars, i, change);
        }
    }

    /**
     * 배열의 현재 위치와 변경할 위치 값으로 배열 값을 서로 이동시킨다.
     *
     * @param a 대상 배열
     * @param i 소스 인덱스
     * @param change 대상 인덱스
     */
    private static void swap(char[] a, int i, int change) {
        char helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }

}
