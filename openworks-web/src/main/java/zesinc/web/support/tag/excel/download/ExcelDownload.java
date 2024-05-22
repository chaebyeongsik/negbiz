package zesinc.web.support.tag.excel.download;

import zesinc.core.lang.Validate;
import zesinc.web.support.tag.OpTagSupport;

/**
 * 엑셀다운로드(script소스) 태그라이브러리
 * excelKey(필수입력), excelFileNm, searchAt, btnId(필수입력)
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 16.    황신욱   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ExcelDownload extends OpTagSupport {

    /** 미지정 시 기본 화면 JSP 템플릿 */
    private static final String DEFAULT_JSP = "excel/excelForm.jsp";
    /** 미지정 시 input 사용여부 N */
    private static final String DEFAULT_SEARCH = "N";

    /** excel키값 */
    private String excelKey = "";

    /** excel 파일명 */
    private String excelFileNm = "";

    /** 검색조건 사용여부 */
    private String searchAt = "";

    /** 출력버튼 ID */
    private String btnId = "";

    /**
     * excelKey을 설정
     * 
     * @param excelKey 을(를) String excelKey로 설정
     */
    public void setExcelKey(String excelKey) {
        this.excelKey = excelKey;
    }

    /**
     * excelFileNm을 설정
     * 
     * @param excelFileNm 을(를) String excelFileNm로 설정
     */
    public void setExcelFileNm(String excelFileNm) {
        this.excelFileNm = excelFileNm;
    }

    /**
     * searchAt을 설정
     * 
     * @param searchAt 을(를) String searchAt로 설정
     */
    public void setSearchAt(String searchAt) {
        this.searchAt = searchAt;
    }

    /**
     * btnId을 설정
     * 
     * @param btnId 을(를) String btnId로 설정
     */
    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    @Override
    protected String getPage() {
        if(Validate.isEmpty(page)) {
            return DEFAULT_JSP;
        }

        return this.page;
    }

    @Override
    public void beforeTag() {
        addAttribute("excelKey", excelKey); // 엑셀키
        addAttribute("excelFileNm", excelFileNm); // 엑셀파일명
        addAttribute("btnId", btnId); // 버튼아이디

        // 검색조건여부
        if(Validate.isEmpty(searchAt)) {
            addAttribute("searchAt", DEFAULT_SEARCH);
        } else {
            addAttribute("searchAt", searchAt);
        }
    }

}
