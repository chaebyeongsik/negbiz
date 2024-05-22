/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.bbsTmplat;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.support.tag.bbsTmplat.support.BbsStyleSupport;
import zesinc.web.support.tag.bbsTmplat.support.BbsTagSupport;
import zesinc.web.utils.MessageUtil;
import zesinc.web.utils.XssUtil;
import zesinc.web.vo.IFileVO;
import zesinc.web.vo.ISessVO;
import zesinc.web.vo.IUserSessVO;
import zesinc.web.vo.cache.BbsConfigCacheVO;

/**
 * 게시판템플릿 태그 : 제목
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 7. 17.    황신욱   최초작성
 *  2015. 11. 1.    방기배   재작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class TtlTag extends BbsTagSupport {

    /**
     * 필드명 설정
     */
    public TtlTag() {
        this.fieldNm = "ttl";
    }

    /**
     * 대상 빈에서 해당 필드를 추출하여 값을 출력
     */
    @SuppressWarnings("unchecked")
    @Override
    public void doTag() throws JspException, IOException {

        JspWriter writer = getJspContext().getOut();

        if("list".equals(this.type)) {
            StringBuilder html = new StringBuilder();

            Object obj = getObj();
            if(Validate.isNotEmpty(obj)) {

                Object sessVo = null;
                String userId = null;
                if(SYSTEM_KIND.equals("intra")) {
                    sessVo = OpHelper.getMgrSession();
                    if(Validate.isNotEmpty(sessVo)) {
                        userId = ((ISessVO) sessVo).getUsername();
                    }
                } else {
                    sessVo = OpHelper.getUserSession();
                    if(Validate.isNotEmpty(sessVo)) {
                        userId = ((IUserSessVO) sessVo).getUserId();
                    }
                }

                BbsConfigCacheVO bbsConfigVo = getConfig();

                Integer bbsSn = getInteger("bbsSn");
                String bbsDocNo = getString("bbsDocNo");

                // 뎁스에 따른 마진
                Integer opnnGrdSn = getInteger("opnnGrdSn");
                Integer marginLeft = 15 * opnnGrdSn;

                // 설정에 따른 제목 길이 조정
                Integer ttlAjmtSz = bbsConfigVo.getTtlAjmtSz();

                String text = "";
                String tmpText = getString();
                if(Validate.isNotEmpty(tmpText)) {
                    text = XssUtil.cleanTag(text, XssUtil.TYPE.ALL);
                    text = StringUtil.fixUnicodeLength(getString(), ttlAjmtSz, "...");
                }

                String rlsYn = getString("rlsYn");
                String mvmnBbsSn = getString("mvmnBbsSn");
                String delYn = getString("delYn");
                String mngrDelYn = getString("mngrDelYn");
                String mngrIndctLmtYn = getString("mngrIndctLmtYn");
                String rgtrId = getString("rgtrId");

                Integer passDay = getInteger("passDay");
                Integer newIndctDayCnt = bbsConfigVo.getNewIndctDayCnt();
                Boolean isPassDay = Boolean.FALSE;

                if(Validate.isNotEmpty(newIndctDayCnt)) {
                    isPassDay = (newIndctDayCnt >= passDay);
                }

                html.append("<span class=\"bbsTtlMargin\" style=\"padding-left:").append(marginLeft).append("px;\">&nbsp;</span>");
                if(opnnGrdSn > 0 && Validate.isNotEmpty(BbsStyleSupport.BBS_REPLY_ICO)) {
                    html.append(" <img class=\"bbsReplyImage\" src=\"").append(BbsStyleSupport.BBS_REPLY_ICO).append("\" alt=\"Reply\" /> ");
                }

                /*
                 * 게시판 설정 및 게시물 상태에 따른 분기 표시
                 */
                if(SYSTEM_KIND.equals("intra")) {
                    // 공개 또는 본인글
                    html.append("<a href=\"BD_selectBbs.do?q_bbsSn=").append(bbsSn).append("&amp;q_bbsDocNo=").append(bbsDocNo)
                        .append("\" onclick=\"opView('").append(bbsDocNo).append("');return false;\">").append(text).append("</a>");
                    // 새글여부. 아이콘 정보가 있는 경우에만 표시
                    if(Validate.isNotEmpty(BbsStyleSupport.BBS_NEW_ICO)) {
                        if(isPassDay) {
                            html.append(" <img class=\"bbsNewImage\" src=\"").append(BbsStyleSupport.BBS_NEW_ICO).append("\" alt=\"New\" /> ");
                        }
                    }
                    // 댓글. 아이콘 정보가 있는 경우에만 표시
                    if(Validate.isNotEmpty(BbsStyleSupport.BBS_CMNT_ICO)) {
                        Integer cmntCo = getInteger("cmntCo");
                        if(cmntCo > 0) {
                            html.append(" <img class=\"bbsCommentImage\" src=\"").append(BbsStyleSupport.BBS_CMNT_ICO).append("\" alt=\"")
                                .append(MessageUtil.getMessage("bbs.cmntTitle", new Integer[] { cmntCo })).append("\" /> ");
                        }
                    }
                    // 첨부파일 아이콘 정보가 있는 경우에만 표시
                    if(Validate.isNotEmpty(BbsStyleSupport.BBS_FILE_ICO)) {
                        List<IFileVO> fileList = (List<IFileVO>) getValue("fileList");
                        if(Validate.isNotEmpty(fileList)) {
                            Integer fileCnt = fileList.size();
                            html.append(" <img class=\"bbsFilesImage\" src=\"").append(BbsStyleSupport.BBS_FILE_ICO).append("\" alt=\"")
                                .append(MessageUtil.getMessage("bbs.attchFileTitle", new Integer[] { fileCnt })).append("\" /> ");
                        }
                    }
                    // 관리자 표시 삭제 또는 제한(플레그삭제)
                    if(mngrIndctLmtYn.equals("Y") || mngrDelYn.endsWith("Y")) {
                        html.append("<br />").append("<span class=\"text-danger\">관리자가 삭제한 글입니다.</span>");
                    }
                    // 삭제된 게시물
                    if(delYn.equals("Y")) {
                        html.append("<br />").append("<span class=\"text-danger\">등록자가 삭제한 글입니다.</span>");
                    }
                    // 타게시판 이관 게시물
                    if(Validate.isNotEmpty(mvmnBbsSn)) {
                        html.append("<br />").append(MessageUtil.getMessage("bbs.moveBbs")).append("(").append(mvmnBbsSn).append(")");
                    }
                    // 비공개여부
                    if(rlsYn.equals("N")) {
                        html.append(MessageUtil.getMessage("bbs.notOpen"));
                    }
                } else {
                    if(delYn.equals("Y")) {
                        // 삭제된 게시물
                        html.append(MessageUtil.getMessage("bbs.deleteBbs"));
                    } else if(mngrIndctLmtYn.equals("Y") || mngrDelYn.endsWith("Y")) {
                        // 관리자 표시 삭제 또는 제한(플레그삭제)
                        html.append(MessageUtil.getMessage("bbs.notDisplayBbs"));
                    } else {
                        // 설정이 없거나, 공개 또는 본인글
                        if(Validate.isEmpty(rlsYn) || rlsYn.equals("Y") || rgtrId.equals(userId)) {
                            html.append("<a href=\"BD_selectBbs.do?q_bbsSn=").append(bbsSn).append("&amp;q_bbsDocNo=").append(bbsDocNo)
                                .append("\" onclick=\"opView('").append(bbsDocNo).append("');return false;\">").append(text).append("</a>");
                            // 새글여부. 아이콘 정보가 있는 경우에만 표시
                            if(Validate.isNotEmpty(BbsStyleSupport.BBS_NEW_ICO)) {
                                if(isPassDay) {
                                    html.append(" <img class=\"bbsNewImage\" src=\"").append(BbsStyleSupport.BBS_NEW_ICO)
                                        .append("\" alt=\"New\" /> ");
                                }
                            }
                            // 댓글. 아이콘 정보가 있는 경우에만 표시
                            if(Validate.isNotEmpty(BbsStyleSupport.BBS_CMNT_ICO)) {
                                Integer cmntCo = getInteger("cmntCo");
                                if(cmntCo > 0) {
                                    html.append(" <img class=\"bbsCommentImage\" src=\"").append(BbsStyleSupport.BBS_CMNT_ICO).append("\" alt=\"")
                                        .append(MessageUtil.getMessage("bbs.cmntTitle", new Integer[] { cmntCo })).append("\" /> ");
                                }
                            }
                            // 첨부파일. 아이콘 정보가 있는 경우에만 표시
                            if(Validate.isNotEmpty(BbsStyleSupport.BBS_FILE_ICO)) {
                                List<IFileVO> fileList = (List<IFileVO>) getValue("fileList");
                                if(Validate.isNotEmpty(fileList)) {
                                    Integer fileCnt = fileList.size();
                                    html.append(" <img class=\"bbsFilesImage\" src=\"").append(BbsStyleSupport.BBS_FILE_ICO).append("\" alt=\"")
                                        .append(MessageUtil.getMessage("bbs.attchFileTitle", new Integer[] { fileCnt })).append("\" /> ");
                                }
                            }
                        } else {
                            // 비공개
                            html.append(MessageUtil.getMessage("bbs.notOpen"));
                        }
                    }
                }
            } else {
                html.append(MessageUtil.getMessage("common.noData"));
            }
            writer.print(html);
        } else if("view".equals(this.type)) {
            writer.print(getString());
        } else if("value".equals(this.type)) {
            writer.print(getValue());
        } else if("text".equals(this.type)) {
            writer.print(getText());
        } else if("label".equals(this.type)) {
            writer.print(makeLabel());
        } else if("desc".equals(this.type)) {
            writer.print(getDesc());
        } else if("form".equals(this.type)) {
            String value = getString();
            StringBuilder html = new StringBuilder();

            html.append("<input class=\"").append(BbsStyleSupport.BBS_INPUT_CLASS).append("\" type=\"text\" name=\"").append(this.fieldNm)
                .append("\" id=\"").append(this.fieldNm).append("\" value=\"").append(value).append("\" />");

            writer.print(html.toString());
        }
    }
}
