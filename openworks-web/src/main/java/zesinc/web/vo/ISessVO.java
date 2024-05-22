/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

/**
 * 세션 정보 추출에 필요한 메소드를 정의한 인터페이스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 2. 8.    방기배   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public interface ISessVO {

    /**
     * 사용자이름
     *
     * @return
     */
    String getFullname();

    /**
     * 사용자계정(ID)
     *
     * @return
     */
    String getUsername();

    /**
     * 사용자 비밀번호
     *
     * @return
     */
    String getPswd();

    /**
     * 부서코드
     *
     * @return
     */
    String getDeptCdId();

    /**
     * 부서명
     *
     * @return
     */
    String getDeptNm();

    /**
     * 계정만료
     *
     * @return 유효하면 true
     */
    boolean isAccountNonExpired();

    /**
     * 계정잠금
     *
     * @return 유효하면 true
     */
    boolean isAccountNonLocked();

    /**
     * 비밀번호만료
     *
     * @return 유효하면 true
     */
    boolean isCredentialsNonExpired();

    /**
     * 정상여부
     *
     * @return
     */
    boolean isEnabled();

    /**
     * 권한그룹코드 목록 반환
     *
     * @return List<String> authrtCdIdList
     */
    List<String> getAuthrtCdIdList();

    /**
     * 권한 목록 반환
     *
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    Collection<? extends GrantedAuthority> getAuthoritiesList();
}
