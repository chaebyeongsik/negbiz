package zesinc.web.support.tag.bbsTmplat;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import zesinc.core.lang.Validate;
import zesinc.web.support.tag.bbsTmplat.support.BbsStyleSupport;
import zesinc.web.support.tag.bbsTmplat.support.BbsTagSupport;
import zesinc.web.vo.cache.BbsConfigCacheVO;

/**
 * 게시판템플릿 태그 : 게시시작일시
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2018. 11. 21.    유보라   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class PstgEndDtTag extends BbsTagSupport {
    /**
     * 필드명 설정
     */
    public PstgEndDtTag() {
        this.fieldNm = "pstgEndDt";
    }

    /**
     * 대상 빈에서 해당 필드를 추출하여 값을 출력
     */
    @Override
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();

        if("list".equals(this.type)) {
            writer.print(getString());
        } else if("view".equals(this.type)) {
            writer.print(getString());
        } else if("value".equals(this.type)) {
            writer.print(getString());
        } else if("text".equals(this.type)) {
            writer.print(getText());
        } else if("label".equals(this.type)) {
            writer.print(makeLabel());
        } else if("desc".equals(this.type)) {
            writer.print(getDesc());
        } else if("form".equals(this.type)) {
            BbsConfigCacheVO bbsConfigVo = getConfig();
            // 게시사용여부 우선 확인
            if(bbsConfigVo.getPstgUseYn().equals("Y")) {
                String value = getString();
                StringBuilder html = new StringBuilder();

                html.append("<input class=\"").append(BbsStyleSupport.BBS_DATE_CLASS).append("\" type=\"date\" name=\"")
                    .append(this.fieldNm).append("\" id=\"").append(this.fieldNm).append("\" value=\"").append(value).append("\"");

                String ntcPstYn = getString("ntcPstYn");
                if(Validate.isEmpty(ntcPstYn) || ntcPstYn.equals("Y")) {
                    html.append(" disabled=\"disabled\"");
                }
                html.append(" />");

                writer.print(html.toString());
            } else {
                writer.print("게시기간을 사용할 수 없습니다. 게시판 설정을 변경해야 합니다.");
            }
        }
    }
}
