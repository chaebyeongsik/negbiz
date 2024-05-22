/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import zesinc.core.lang.Validate;
import zesinc.web.spring.security.support.OpenworksRequestMatcher;

/**
 * 메뉴 URL과 접근 권한을 DB에서 조회하는 DAO
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 30.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class OpenworksSecuredObjectDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String rolePrefix = "ROLE_";

    private String sqlRolesAndUrl;

    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    public String getRolePrefix() {
        return rolePrefix;
    }

    /**
     * 롤에 대한 URL 정보를 가져오는 SQL을 얻어온다.
     * 
     * @return
     */
    public String getSqlRolesAndUrl() {
        return sqlRolesAndUrl;
    }

    /**
     * Opwnworks 메뉴 관리 특성상 URL 패턴만을 지원한다.
     * 따라서 메뉴 URL 정보를 DB에서 읽어 패턴을 생성한다.
     * 
     * @param sqlRolesAndUrl
     */
    public void setSqlRolesAndUrl(String sqlRolesAndUrl) {
        this.sqlRolesAndUrl = sqlRolesAndUrl;
    }

    public LinkedHashMap<Object, List<ConfigAttribute>> getRolesAndUrl() throws Exception {

        LinkedHashMap<Object, List<ConfigAttribute>> resourcesMap = new LinkedHashMap<Object, List<ConfigAttribute>>();

        List<Map<String, Object>> resultList = this.namedParameterJdbcTemplate.queryForList(getSqlRolesAndUrl(), new HashMap<String, String>());
        Iterator<Map<String, Object>> itr = resultList.iterator();

        Map<String, Object> menuMap;

        String role;
        String menuSn, grntCdId, param1, param2, param3;

        String menuUri;
        List<String> paramList;
        Object urlResource;

        while(itr.hasNext()) {
            menuMap = itr.next();

            menuSn = (menuMap.get("MENU_SN")).toString();
            grntCdId = (String) menuMap.get("GRNT_CD_ID");
            role = getRolePrefix() + menuSn + "_" + grntCdId;

            menuUri = (String) menuMap.get("MENU_URL_ADDR");

            param1 = (String) menuMap.get("PRMTT_NM1");
            param2 = (String) menuMap.get("PRMTT_NM2");
            param3 = (String) menuMap.get("PRMTT_NM3");

            paramList = new ArrayList<String>();
            if(Validate.isNotEmpty(param1)) {
                paramList.add(param1);
            }
            if(Validate.isNotEmpty(param2)) {
                paramList.add(param2);
            }
            if(Validate.isNotEmpty(param3)) {
                paramList.add(param3);
            }

            urlResource = new OpenworksRequestMatcher(menuUri, paramList);

            if(logger.isDebugEnabled()) {
                logger.debug("Security Matcher URI is : {}", menuUri);
                logger.debug("Security Matcher Parameter is : {}", paramList);
            }

            List<ConfigAttribute> configList;
            // 이전 등록이 있다면 목록을 가져와 추가하고, 없으면 목록을 생성하여 추가한다.
            if(resourcesMap.containsKey(urlResource)) {
                configList = resourcesMap.get(urlResource);
                configList.add(new SecurityConfig(role));
            } else {
                configList = new LinkedList<ConfigAttribute>();
                configList.add(new SecurityConfig(role));
                resourcesMap.put(urlResource, configList);
            }
        }

        return resourcesMap;
    }
}
