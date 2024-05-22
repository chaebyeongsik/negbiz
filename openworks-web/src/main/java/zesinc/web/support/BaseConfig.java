/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support;

import java.io.File;
import java.util.List;

import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;
import zesinc.web.auth.AuthType;
import zesinc.web.auth.anotation.OpenworksAuth;

/**
 * 고정 상수 클레스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 10. 17.    방기배   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class BaseConfig {

    /* ------------------------- Common Info ----------------------- */

    /** 기관명 명 */
    public static final String ORG_NAME = Config.getString("webapp-config.orgName");
    /** 시스템 명 */
    public static final String SYSTEM_NAME = Config.getString("system-config.systemName");
    /** 시스템구분 */
    public static final String SYSTEM_KIND = Config.getString("system-config.system-kind", "intra");
    /** 기본인코딩 */
    public static final String CHAR_SET = Config.getString("system-config.defaultCharset");
    /** 운영 모드 true 또는 false */
    public static final Boolean PRO_MODE = Config.getBoolean("webapp-config.productionMode");
    /** 관리자 로그 모드 true 또는 false */
    public static final Boolean MGR_LOG_MODE = Config.getBoolean("webapp-config.mgrLogMode", false);
    /** 개발환경 소스파일 Root 경로 PRO_MODE 에 따라서 개발환경시 일부에서 사용되는 값 */
    public static List<String> DEV_PATH_LIST = Config.getList(String.class, "locations-config.devPaths.value");
    /** 게시판 템플릿, cms 등 화면을 통하여 파일을 생성하는 경우 동시에 만들어져야 하는 경로 */
    public static List<String> PROGRAM_PATH_LIST = Config.getList(String.class, "locations-config.programPaths.value");
    /** 첨부제한 업로드 파일 */
    public static final String[] NOT_ALLOW_FILE_EXTS;
    static {
        NOT_ALLOW_FILE_EXTS = StringUtil.deleteWhitespace(
            Config.getString("system-config.notAllowFileExts", "jsp,java,exe,asp,php,bat,sh")).split(",");
    }
    /** 이미지 업로드 파일 */
    public static final String[] IMAGE_FILE_EXTS;
    static {
        IMAGE_FILE_EXTS = StringUtil.deleteWhitespace(
            Config.getString("system-config.imageFileExts", "bmp,gif,jpe,jpg,jpeg,jfif,pcx,png,tiff,wbmp")).split(",");
    }

    /* ----------------------- LOCATION INFO --------------------- */
    /** 서버 루트 경로 */
    public static final String WEBAPP_ROOT;
    /** 첨부파일 저장위치 */
    public static final String UPLOAD_ROOT;
    /** 정적컨텐츠 저장위치 */
    public static final String RESOURCE_ROOT;
    /*
     * 별도의 설정이 있는 경우 사용하며, 없는 경우는 서버 기본값으로 설정
     * 설정파일 : /src/main/resources/config/commons/location-commons-config.xml
     */
    static {
        String tmp;
        String webappRoot = Config.getString("locations-config.webappRoot");
        if(Validate.isEmpty(webappRoot)) {
            String webAppRootKey = Config.getString("locations-config.webAppRootKey");
            String systemRootKey = "openworks4.intra.root";
            if(SYSTEM_KIND.equals("user")) {
                systemRootKey = "openworks4.user.root";
            }
            tmp = Config.getString(webAppRootKey, systemRootKey);
            tmp = StringUtil.replace(tmp, File.separator, "/");
            WEBAPP_ROOT = tmp;
        } else {
            tmp = webappRoot;
            tmp = StringUtil.replace(tmp, File.separator, "/");
            WEBAPP_ROOT = tmp;
        }

        String uploadRoot = Config.getString("locations-config.uploadRoot");
        if(Validate.isEmpty(uploadRoot)) {
            UPLOAD_ROOT = WEBAPP_ROOT;
        } else {
            tmp = uploadRoot;
            tmp = StringUtil.replace(tmp, File.separator, "/");
            UPLOAD_ROOT = tmp;
        }

        String resourceRoot = Config.getString("locations-config.resourceRoot");
        if(Validate.isEmpty(resourceRoot)) {
            RESOURCE_ROOT = WEBAPP_ROOT;
        } else {
            tmp = resourceRoot;
            tmp = StringUtil.replace(tmp, File.separator, "/");
            RESOURCE_ROOT = tmp;
        }
        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("WEBAPP_ROOT :::  " + WEBAPP_ROOT);
        System.out.println("UPLOAD_ROOT :::  " + UPLOAD_ROOT);
        System.out.println("RESOURCE_ROOT :::  " + RESOURCE_ROOT);
        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("==============================================================");
    }

    public static final String UPLOAD_FOLDER_PATH = Config.getString("locations-config.uploadFolderPath", "");
    public static final String RESOURCE_FOLDER_PATH = Config.getString("locations-config.resourceFolderPath", "");
    public static final String DEFAULT_UPLOAD_FOLDER_NAME = Config.getString("locations-config.defaultUploadFolderPath", "");
    public static final String INCLUDE_TAGLIB_BASE = Config.getString("locations-config.includeTaglibRoot", "");

    /* ------------------------- Model Object ----------------------- */
    /** 기본정보 목록을 가진 객체명 */
    public static final String KEY_BASE_LIST = "baseList";
    /** 데이터 목록 정보를 가진 객체명 */
    public static final String KEY_DATA_LIST = "dataList";
    /** 파라미터 목록 정보를 가진 객체명 */
    public static final String KEY_PARAM_LIST = "postParamList";
    /** 사이트 목록 */
    public static final String KEY_DOMAIN_LIST = "domainList";
    /** 공지 목록 정보를 가진 객체명 */
    public static final String KEY_NOTICE_LIST = "noticeList";

    /** 페이징 객체 키값 */
    public static final String KEY_PAGER = "pager";

    /** 세션정보를 가진 객체명 */
    public static final String KEY_LOGIN_VO = "loginVo";

    /** 주요정보를 가진 객체명 */
    public static final String KEY_BASE_VO = "baseVo";
    /** 데이터 정보를 가진 객체명 */
    public static final String KEY_DATA_VO = "dataVo";

    /** 권한코드목록 정보를 가진 객체명 */
    public static final String KEY_AUTH_LIST = "authList";

    /** 검색정보를 가진 BEAN 객체명 */
    public static final String KEY_SEARCH_VO = "searchVo";
    /** 검색정보를 가진 Map 객체명 */
    public static final String KEY_SEARCH_MAP = "searchMap";

    /** 검증 객체 명 */
    public static final String KEY_VALIDATE = "validate";
    /** 검증결과 객체명 */
    public static final String KEY_VALIDATE_RESULT_KEY = "validateResult";
    /** 검증용 스크립트 객체명 */
    public static final String KEY_VALIDATE_SCRIPT_KEY = "validateScript";

    /* -----------------------CODE KEY NAME --------------------- */
    /**
     * 현재 메뉴에 대한 사용자 권한코드
     *
     * @see AuthType
     * @see OpenworksAuth
     */
    public static final String AUTH_CODE_KEY = "__ack";

    /* ----------------------- DATA KEY NAME --------------------- */
    public static final String TEXT_DATA_KEY = "__tdk";
    public static final String HTML_DATA_KEY = "__hdk";
    public static final String SCRIPT_DATA_KEY = "__sdk";
    public static final String MESSAGE_DATA_KEY = "__mdk";
    public static final String JSON_DATA_KEY = "__jdk";
    public static final String EXCEL_DATA_KEY = "__edk";
    public static final String PDF_DATA_KEY = "__pdk";
    public static final String RSS_DATA_KEY = "__rdk";
    public static final String ATOM_DATA_KEY = "__adk";
    public static final String FILE_DATA_KEY = "__fdk";
    public static final String FILE_LIST_KEY = "__flk";
    public static final String OBJ_DATA_KEY = "__odk";
    public static final String EXCEL_HEADER_KEY = "__ehk";

    /* ----------------------- CACHE KEY NAME --------------------- */
    // 다국어
    public static final String MULTILANG_CACHE_KEY = "__MultilangCache";
    // 금칙어
    public static final String PRHIBT_CACHE_KEY = "__PrhibtCache";
    // 메뉴
    public static final String MENU_LIST_KEY = "__MenuListCache";
    public static final String MENU_URI_MAP_KEY = "__MenuUriMapCache";
    public static final String MENU_ACTION_URI_MAP_KEY = "__MenuActionUriMapCache";
    public static final String MENU_SN_MAP_KEY = "__MenuCodeMapCache";
    public static final String TOP_MENU_INFO_KEY = "__TopMenuKey";
    public static final String SUB_MENU_INFO_KEY = "__SubMenuKey";
    public static final String MENU_INFO_KEY = "__MenuKey";
    public static final String MENU_PATH_KEY = "__MenuPath";
    public static final String MY_MENU_KEY = "__MyMenuKey";
    // 코드
    public static final String CODE_MAP_CACHE_KEY = "__CodeMapCache";
    public static final String CODE_LIST_CACHE_KEY = "__CodeListCache";
    public static final String DEFAULT_LWPRT_KEY = "__DefaultLwprtNm";
    // 도메인
    public static final String SITE_SN_MAP_CACHE_KEY = "__DomnCodeMapCache";
    public static final String DOMN_NM_MAP_CACHE_KEY = "__DomnNmMapCache";
    public static final String DOMN_LIST_CACHE_KEY = "__DomnListCache";
    // 도움말
    public static final String HPCM_CACHE_KEY = "__hpcmCache";
    // 게시판
    public static final String BBS_CONFIG_CACHE_KEY = "__BbsConfigVo";
    public static final String BBS_CACHE_KEY = "__BbsCache";
    public static final String BBS_ITEM_CACHE_KEY = "__BbsItemCache";
    public static final String BBS_ITEM_MAP_CACHE_KEY = "__BbsItemMapCache";
    public static final String BBS_ITEM_LIST_KEY = "__BbsItemListKey";
    public static final String BBS_ITEM_MAP_KEY = "__BbsItemMapKey";
    public static final String BBS_CONFIG_VO_KEY = "bbsConfigVo";

    // 사용자메뉴
    public static final String USER_MENU_LIST_KEY = "UserMenuListCache";
    public static final String USER_MENU_URI_MAP_KEY = "__UserMenuUriMapCache";
    public static final String USER_MENU_ENG_NM_MAP_KEY = "__UserMenuCodeMapCache";
    public static final String USER_TOP_MENU_INFO_KEY = "UserTopMenu";
    public static final String USER_SUB_MENU_INFO_KEY = "UserSubMenu";
    public static final String USER_MENU_INFO_KEY = "UserMenu";

    /* ---------------------- BEAN VIEW NAME --------------------- */
    public static final String TEXT_VIEW_NAME = "textView";
    public static final String HTML_VIEW_NAME = "htmlView";
    public static final String JSON_VIEW_NAME = "jsonObjView";
    public static final String EXCEL_VIEW_NAME = "excelView";
    public static final String PDF_VIEW_NAME = "pdfView";
    public static final String RSS_VIEW_NAME = "rssView";
    public static final String ATOM_VIEW_NAME = "atomView";
    public static final String DOWNLOAD_VIEW_NAME = "downloadView";
    public static final String EXCEL_FILE_NAME = "excelFileName";

    /* ---------------------- JSP PAGE NAME ---------------------- */
    public static final String ALERT_AND_BACK = Config.getString("locations-config.jspViewPage.alertAndBack");
    public static final String ALERT_AND_CLOSE = Config.getString("locations-config.jspViewPage.alertAndClose");
    public static final String ALERT_AND_REDIRECT = Config.getString("locations-config.jspViewPage.alertAndRedirect");
    public static final String POST_METHOD_REDIRECT = Config.getString("locations-config.jspViewPage.postMethodRedirect");
    public static final String CONFIRM_AND_REDIRECT = Config.getString("locations-config.jspViewPage.confirmAndRedirect");
    public static final String RUN_SCRIPT = Config.getString("locations-config.jspViewPage.runScript");

    /* --------------------- SESSION CONFIG ---------------------- */
    /** 관리자 세션키 */
    public static final String MGR_SESSION_KEY = Config.getString("system-config.session.mgrSessionKey", "__msk");
    /** 사용자 세션키 */
    public static final String USER_SESSION_KEY = Config.getString("system-config.session.userSessionKey", "__usk");

    /* -------------------------- PAGING ------------------------- */
    public static final int DEFAULT_ROW_PER_PAGE = Config.getInt("system-config.paging.rowPerPage", 10);
    public static final int DEFAULT_PAGE_PER_PAGE = Config.getInt("system-config.paging.pagePerPage", 10);
    public static final Integer[] DEFAULT_PAGE_NUMS;
    static {
        String[] pageNums = Config.getString("system-config.paging.pageNums", "5, 10, 15, 30, 50, 100, 200, 300").split(",");

        DEFAULT_PAGE_NUMS = new Integer[pageNums.length];
        int idx = 0;
        for(String pageNum : pageNums) {
            DEFAULT_PAGE_NUMS[idx++] = Integer.valueOf(pageNum.trim());
        }
    }

    /** 검색 파라미터 접두어 */
    public static final String PREFIX_SEARCH_PARAM = Config.getString("webapp-config.prefixSearchParam", "q_");
}
