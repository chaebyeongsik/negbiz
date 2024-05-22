/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import zesinc.core.cache.CacheService;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.vo.ISessVO;
import zesinc.web.vo.cache.MenuUrlCacheVO;

/**
 * 권한 관련 지원 함수 모음 클레스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 24.    방기배   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class AuthSupport {

    /** 권한코드 목록 */
    private static final List<Map<String, String>> AUTH_CODE_LIST;
    /** 권한유형을 문자열 권한코드로 설정 */
    private static final Map<String, AuthType> AUTH_CODE_MAP;
    // 초기화
    static {
        AUTH_CODE_LIST = new ArrayList<Map<String, String>>();
        AUTH_CODE_MAP = new HashMap<String, AuthType>();

        AuthType[] authTypes = AuthType.values();

        Map<String, String> authCode;
        for(AuthType authType : authTypes) {
            authCode = new HashMap<String, String>();
            authCode.put("cdId", authType.getCdId());
            authCode.put("name", authType.getName());
            AUTH_CODE_LIST.add(authCode);

            AUTH_CODE_MAP.put(authType.getCdId(), authType);
            AUTH_CODE_MAP.put(authType.name(), authType);
        }
    }

    /**
     * 인자의 권한그룹코드를 보유하면 허용
     * 복수 권한 확인시 쉼표(,)로 구분한다.
     * 예 : aaaa,bbbb,ccc
     *
     * @param groupCodes
     * @return
     */
    public static Boolean isAuthGroup(String groupCodes) {

        return isAuthGroup(groupCodes, null);
    }

    /**
     * 인자의 권한그룹코드를 보유하거나, 인자의 ID가 로그인 ID와 같다면 허용
     * 복수 권한 확인시 쉼표(,)로 구분한다.
     * 예 : aaaa,bbbb,ccc
     *
     * @param groupCodes
     * @param picId
     * @return
     */
    public static Boolean isAuthGroup(String groupCodes, String picId) {
        Boolean hasAuthGroup = Boolean.FALSE;

        ISessVO user = (ISessVO) OpHelper.getMgrSession();
        List<String> authorGroupCodeList = user.getAuthrtCdIdList();

        if(Validate.isNotEmpty(groupCodes) && Validate.isNotEmpty(authorGroupCodeList)) {
            String[] arrGroupCodes = groupCodes.split(",");
            for(String groupCode : arrGroupCodes) {
                if(authorGroupCodeList.contains(groupCode.trim())) {
                    hasAuthGroup = Boolean.TRUE;
                    break;
                }
            }
        }
        // 사용자 ID가 설정된 경우
        if(Validate.isNotEmpty(picId) && picId.equals(user.getUsername())) {
            hasAuthGroup = Boolean.TRUE;
        }
        return hasAuthGroup;
    }

    /**
     * <pre>
     * 현재 요청에 대하여 권한이 있는지 여부를 확인한다.
     * (소유한 권한보다 같거나 큰지를 비교)
     * picId 담당자ID 인자는 로그인ID와 비교하여 동일한 경우 권한을 가진다.
     * </pre>
     *
     * @return
     */
    public static Boolean isAuth(String picId) {

        return isAuth(null, picId);
    }

    /**
     * <pre>
     * 현재 요청에 대하여 권한이 있는지 여부를 확인한다.
     * (소유한 권한보다 같거나 큰지를 비교)
     * </pre>
     *
     * @return
     */
    public static Boolean isAuth() {

        return isAuth(null, null);
    }

    /**
     * <pre>
     * 현재 요청에 대하여 권한이 있는지 여부를 확인한다.
     * picId 담당자ID 인자가 포함된경우 로그인ID와 비교하여 동일한 경우 권한을 가진다.
     * </pre>
     *
     * @param needAuthType AuthType.READ/AuthType.BASIC/AuthType.MANAGER 중 하나이며, 확장된 경우 다른 유형이 가능하다.
     * @param picId 로그인ID와 비교할 담당자ID
     * @return
     */
    public static Boolean isAuth(AuthType needAuthType, String picId) {
        Boolean hasAuth = Boolean.FALSE;

        // 나의 권한 코드
        String hasAuthCode = getAuthCode();
        /*
         * 총괄운영 권한을 가진 경우는 무조건 권한이 있음으로 처리
         */
        if(AuthType.MANAGER.getCdId().equals(hasAuthCode)) {
            hasAuth = Boolean.TRUE;
        } else {
            /*
             * 비교 권한이 있는 경우. 대상 권한코드과 현재 나의 권한코드를 비교
             */
            if(Validate.isNotEmpty(needAuthType)) {
                if(AuthSupport.greaterThenEquals(needAuthType.getCdId(), hasAuthCode)) {
                    hasAuth = Boolean.TRUE;
                }
            }
            /*
             * 비교 대상 담당자 ID가 존재하는 경우 담당자ID도 비교하여 결과에 포함한다.
             */
            if(Validate.isNotEmpty(picId)) {
                UserDetails user = (UserDetails) OpHelper.getMgrSession();
                if(picId.equals(user.getUsername())) {
                    hasAuth = Boolean.TRUE;
                } else {
                    hasAuth = Boolean.FALSE;
                }
            }
        }
        return hasAuth;
    }

    /**
     * <pre>
     * 현재 요청에 대하여 권한이 있는지 여부를 확인한다.
     * (소유한 권한보다 같거나 큰지를 비교)
     * picId 담당자ID 인자는 로그인ID와 비교하여 동일한 경우 권한을 가진다.
     * </pre>
     *
     * @param needAuthType READ/BASIC/MANAGER 중 하나의 문자열이며, 확장된 경우 다른 유형이 가능하다.
     * @param picId 로그인ID와 비교할 담당자ID
     * @return
     */
    public static Boolean isAuthType(String needAuthType, String picId) {

        return isAuth(AuthType.valueOf(needAuthType), picId);
    }

    /**
     * 현재 나의 권한코드 문자열을 반환한다.
     * <p />
     * 1001/2001/3001 중 하나의 문자열이며, 확장된 경우 다른 코드가 가능하다.
     *
     * @return
     * @See {@link AuthType}
     */
    public static String getAuthCode() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authCode = (String) request.getAttribute(BaseConfig.AUTH_CODE_KEY);

        if (Validate.isEmpty(authCode)) {
            authCode = getAuthCode(request);
        }

        return authCode;
    }

    /**
     * 현재 나의 권한코드 문자열을 반환한다.
     * <p />
     * 1001/2001/3001 중 하나의 문자열이며, 확장된 경우 다른 코드가 가능하다.
     *
     * @return
     * @See {@link AuthType}
     */
    @SuppressWarnings("unchecked")
    public static String getAuthCode (HttpServletRequest request) {

        String authCode = "";
        String menuSn = "";
        String reqURI = request.getRequestURI();
        Map<String, Object> paramMap = (Map<String, Object>) request.getAttribute("paramMap");

        // 케쉬에서 메뉴URL Map을 가져 온다.
        Map<String, MenuUrlCacheVO> menuActionUriMap = (HashMap<String, MenuUrlCacheVO>) CacheService.get(BaseConfig.MENU_ACTION_URI_MAP_KEY);

        if (menuActionUriMap != null) {

            for (Map.Entry<String, MenuUrlCacheVO> entry : menuActionUriMap.entrySet()) {
                String [] req = entry.getKey().split("‡");
                if (req.length == 1) {
                    String uriPattern = req[0];
                    if (uriPattern.equals(reqURI)) {
                        menuSn = entry.getValue().getMenuSn().toString();
                        break;
                    }
                } else if (req.length == 2) {
                    String uriPattern = req[0];
                    String [] paramList = req[1].split(",");
                    boolean isMatchValue = true;
                    if (uriPattern.equals(reqURI)) {
                        for (String param : paramList) {
                            String[] params = param.split("=");
                            String value = (String) paramMap.get(params[0]);
                            if (!params[1].equals(value)) {
                                isMatchValue = false;
                                break;
                            }
                        }

                        if (isMatchValue) {
                            menuSn = entry.getValue().getMenuSn().toString();
                            break;
                        }
                    }
                }
            }

            if (Validate.isNotEmpty(menuSn)) {

                ISessVO user = (ISessVO) OpHelper.getMgrSession();
                Collection<? extends GrantedAuthority> authorities = user.getAuthoritiesList();

                String prefixMenuSn = "OPENWORKS_" + menuSn + "_";

                for (GrantedAuthority authority : authorities) {
                    String auth = authority.toString();
                    // 메뉴 자체의 권한 정보
                    if (auth.indexOf(prefixMenuSn) > -1) {
                        authCode = auth.replace(prefixMenuSn, "");
                        break;
                    }
                }
            }
        }

        return authCode;
    }

    /**
     * <pre>
     * 현재 요청에 대하여 권한이 있는지 여부를 확인한다.
     * (소유한 권한보다 같거나 큰지를 비교)
     * picId 담당자ID 인자는 로그인ID와 비교하여 동일한 경우 권한을 가진다.
     * </pre>
     *
     * @param authCode 1001/2001/3001 중 하나의 문자열이며, 확장된 경우 다른 코드가 가능하다.
     * @param picId 로그인ID와 비교할 담당자ID
     * @return
     */
    public static Boolean isAuthCode(String authCode, String picId) {

        return isAuth(getAuthType(authCode), picId);
    }

    /**
     * 현재 나의 권한타입을 반환한다.
     * <p />
     * AuthType.READ/AuthType.BASIC/AuthType.MANAGER 중 하나이며, 확장된 경우 다른 유형이 가능하다.
     *
     * @return
     * @See {@link AuthType}
     */
    public static AuthType getAuthType() {
        String authCode = getAuthCode();

        return AUTH_CODE_MAP.get(authCode);
    }

    /**
     * 인자의 문자열 권한코드에 해당하는 <code>AuthType</code>을 반환한다.
     *
     * @param authStr
     * @return
     */
    public static AuthType getAuthType(String authStr) {

        return AUTH_CODE_MAP.get(authStr);
    }

    /**
     * 권한코드 문자열 인자(needAuthCode)와 현재 나의 권한코드가 동일한지 비교
     *
     * @param needAuthCode
     * @return 동일하면 true 이외 false
     */
    public static boolean equals(String needAuthCode) {

        String hasAuthCode = getAuthCode();

        return Validate.isEmpty(needAuthCode) && hasAuthCode.equals(needAuthCode);
    }

    /**
     * 권한코드 문자열 인자(needAuthCode)보다 현재 나의 권한코드가 같거나 큰지 비교
     *
     * @param needAuthCode
     * @return 요구되는 권한보다 나의 권한이 같거나 크면 true, 아니면 false
     */
    public static boolean greaterThenEquals(String needAuthCode) {

        String hasAuthCode = getAuthCode();

        return greaterThenEquals(needAuthCode, hasAuthCode);
    }

    /**
     * 두개의 권한코드 문자열을 받아서 (needAuthCode, hasAuthCode) hasAuthCode 코드가 큰지를 비교
     *
     * @param needAuthCode 의미적으로 요구되는 권한
     * @param hasAuthCode 의미적으로 내가가진 권한
     * @return needAuthCode 권한보다 hasAuthCode 권한이 같거나 크면 true, 아니면 false
     */
    public static boolean greaterThenEquals(String needAuthCode, String hasAuthCode) {

        if(Validate.isEmpty(needAuthCode) || Validate.isEmpty(hasAuthCode)) {
            return false;
        }

        Integer intNeedAuthCode = new Integer(needAuthCode);
        Integer intHasAuthCode = new Integer(hasAuthCode);

        return intHasAuthCode >= intNeedAuthCode;
    }

    /**
     * <pre>
     * 정의된 권한 코드 목록을 반환
     *
     * 권한목록은 <code>HashMap&lt;String, String&gt;</code> 형태이며, code와 name으로
     * 코드와 명칭을 담고있다. map.get("code"), map.get("name");
     * 예 : map.get("1001"); map.get("READ");
     * </pre>
     *
     * @return
     * @see AuthType
     */
    public static List<Map<String, String>> getAuthCodeList() {

        return AUTH_CODE_LIST;
    }

    /**
     * <pre>
     * 정의된 권한 코드 Map을 반환
     *
     * 권한 코드 Map은 <code>HashMap&lt;String, AuthType&gt;</code> 형태이다. map.get("code");
     * 예 : map.get("1001"); map.get("2001");
     * </pre>
     *
     * @return
     */
    public static Map<String, AuthType> getAuthCodeMap() {

        return AUTH_CODE_MAP;
    }

    /**
     * <code>SortedSet</code>를 통한 중복권한 제거
     *
     * @param authorities 권한목록
     * @return
     */
    public static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for(GrantedAuthority grantedAuthority : authorities) {
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    /**
     * 중복 권한 비교 용 <code>Comparator</code> 구현객체
     *
     * <pre>
     * << 개정이력(Modification Information) >>
     *
     *     수정일       수정자   수정내용
     * --------------  --------  -------------------------------
     *  2015. 4. 24.    방기배   최초작성
     * </pre>
     *
     * @author (주)제스아이엔씨 기술연구소
     * @see
     */
    public static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if(g2.getAuthority() == null) {
                return -1;
            }
            if(g1.getAuthority() == null) {
                return 1;
            }
            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
}
