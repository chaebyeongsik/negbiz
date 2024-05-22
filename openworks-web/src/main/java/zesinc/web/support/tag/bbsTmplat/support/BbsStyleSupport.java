/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.bbsTmplat.support;

import zesinc.core.config.Config;

/**
 * 게시판 Tag lib에서 자동 생성하는 html 코드들에 적용되는 class 이름에 해당하는 상수 값
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 11. 4.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class BbsStyleSupport {

    /** bbsCheckBoxLabelClass : input type="checkbox" tag에 적용되는 class 이름 */
    public static final String BBS_CHECKBOX_CLASS = Config.getString("bbs-config.bbsStyle.bbsCheckBoxClass", "bbsCheckBoxClass");
    /** bbsRadioLabelClass : input type="radio" tag에 적용되는 class 이름 */
    public static final String BBS_RADIO_CLASS = Config.getString("bbs-config.bbsStyle.bbsRadioClass", "bbsRadioClass");
    /** bbsTextareaClass : textarea tag에 적용되는 class 이름 */
    public static final String BBS_TEXTAREA_CLASS = Config.getString("bbs-config.bbsStyle.bbsTextareaClass", "bbsTextareaClass");
    /** bbsInputClass : 일반 input tag에 적용되는 class 이름 */
    public static final String BBS_INPUT_CLASS = Config.getString("bbs-config.bbsStyle.bbsInputClass", "bbsInputClass");
    /** bbsSelectClass : select tag에 적용되는 class 이름 */
    public static final String BBS_SELECT_CLASS = Config.getString("bbs-config.bbsStyle.bbsSelectClass", "bbsSelectClass");
    /** bbsFileClass : input type="file" tag에 적용되는 class 이름 */
    public static final String BBS_FILE_CLASS = Config.getString("bbs-config.bbsStyle.bbsFileClass", "bbsFileClass");
    /** bbsDateClass : input type="date" 또는 날짜형 input tag에 적용되는 class 이름 */
    public static final String BBS_DATE_CLASS = Config.getString("bbs-config.bbsStyle.bbsDateClass", "bbsDateClass");
    /** bbsEmailClass : input type="date" 또는 E-mail input tag에 적용되는 class 이름 */
    public static final String BBS_EMAIL_CLASS = Config.getString("bbs-config.bbsStyle.bbsEmailClass", "bbsEmailClass");

    /** bbsLabelClass : label tag에 적용되는 class 이름 */
    public static final String BBS_LABEL_CLASS = Config.getString("bbs-config.bbsStyle.bbsLabelClass", "bbsLabelClass");
    /** bbsCheckBoxLabelClass : checkbox를 감싸고 있는 label tag에 적용되는 class 이름 */
    public static final String BBS_CHECKBOXLABEL_CLASS = Config.getString("bbs-config.bbsStyle.bbsCheckBoxLabelClass", "bbsCheckBoxLabelClass");
    /** bbsRadioLabelClass : radio를 감싸고 있는 label tag에 적용되는 class 이름 */
    public static final String BBS_RADIOLABEL_CLASS = Config.getString("bbs-config.bbsStyle.bbsRadioLabelClass", "bbsRadioLabelClass");

    /** bbsHelpBlockClass : 해당컬럼에 대한 설명을 표시하는 class 이름 */
    public static final String BBS_HELP_BLOCK_CLASS = Config.getString("bbs-config.bbsStyle.bbsHelpBlockClass", "bbsHelpBlockClass");

    /** bbsInnerButtonClass : 화면 내부에 위치하는 버튼 class 이름 */
    public static final String BBS_INNER_BUTTON_CLASS = Config.getString("bbs-config.bbsStyle.bbsInnerButtonClass", "bbsInnerButtonClass");

    /** 필수입력항목 태그 class 이름 */
    public static final String BBS_REQUIRE_CLASS = Config.getString("bbs-config.bbsStyle.bbsRequireClass", "mandatory");

    /** 답글표시 아이콘 */
    public static final String BBS_REPLY_ICO = Config.getString("bbs-config.bbsStyle.bbsReplyIco", "");
    /** 새글표시 아이콘 */
    public static final String BBS_NEW_ICO = Config.getString("bbs-config.bbsStyle.bbsNewIco", "");
    /** 파일표시 아이콘 */
    public static final String BBS_FILE_ICO = Config.getString("bbs-config.bbsStyle.bbsFileIco", "");
    /** 댓글갯수 아이콘 */
    public static final String BBS_CMNT_ICO = Config.getString("bbs-config.bbsStyle.bbsCmntIco", "");

}
