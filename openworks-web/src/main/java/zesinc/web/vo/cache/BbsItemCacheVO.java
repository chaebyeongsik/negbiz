package zesinc.web.vo.cache;

import zesinc.web.vo.BaseVO;

public class BbsItemCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -5676036172123517304L;

    /** 게시판코드 */
    private Integer bbsSn;
    /** 컬럼ID */
    private String colId;
    /** 컬럼명 */
    private String colNm;
    /** 화면명 */
    private String scrnNm;
    /** 컬럼유형 */
    private String colTypeNm;
    /** 안내문구 */
    private String bbsColExpln;
    /** 컬럼명에 따른 VO 멤버명 */
    private String fieldNm;
    /** 검색유형 */
    private String srchType;
    /** 필수여부 */
    private String esntlYn;
    /** 정렬순서 */
    private Integer sortSn;
    /** 목록표시여부 */
    private String lstIndctYn;
    /** 읽기표시여부 */
    private String inqIndctYn;
    /** 폼표시여부 */
    private String inptIndctYn;

    /**
     * Integer bbsSn을 반환
     * 
     * @return Integer bbsSn
     */
    public Integer getBbsSn() {
        return bbsSn;
    }

    /**
     * bbsSn을 설정
     * 
     * @param bbsSn 을(를) Integer bbsSn로 설정
     */
    public void setBbsSn(Integer bbsSn) {
        this.bbsSn = bbsSn;
    }

    /**
     * String colId을 반환
     * 
     * @return String colId
     */
    public String getColId() {
        return colId;
    }

    /**
     * colId을 설정
     * 
     * @param colId 을(를) String colId로 설정
     */
    public void setColId(String colId) {
        this.colId = colId;
    }

    /**
     * String colNm을 반환
     * 
     * @return String colNm
     */
    public String getColNm() {
        return colNm;
    }

    /**
     * colNm을 설정
     * 
     * @param colNm 을(를) String colNm로 설정
     */
    public void setColNm(String colNm) {
        this.colNm = colNm;
    }

    /**
     * String scrnNm을 반환
     * 
     * @return String scrnNm
     */
    public String getScrnNm() {
        return scrnNm;
    }

    /**
     * scrnNm을 설정
     * 
     * @param scrnNm 을(를) String scrnNm로 설정
     */
    public void setScrnNm(String scrnNm) {
        this.scrnNm = scrnNm;
    }

    /**
     * String colTypeNm을 반환
     * 
     * @return String colTypeNm
     */
    public String getColTypeNm() {
        return colTypeNm;
    }

    /**
     * colTypeNm을 설정
     * 
     * @param colTypeNm 을(를) String colTypeNm로 설정
     */
    public void setColTypeNm(String colTypeNm) {
        this.colTypeNm = colTypeNm;
    }

    /**
     * String bbsColExpln을 반환
     * 
     * @return String bbsColExpln
     */
    public String getBbsColExpln() {
        return bbsColExpln;
    }

    /**
     * bbsColExpln을 설정
     * 
     * @param bbsColExpln 을(를) String bbsColExpln로 설정
     */
    public void setBbsColExpln(String bbsColExpln) {
        this.bbsColExpln = bbsColExpln;
    }

    /**
     * String fieldNm을 반환
     * 
     * @return String fieldNm
     */
    public String getFieldNm() {
        return fieldNm;
    }

    /**
     * fieldNm을 설정
     * 
     * @param fieldNm 을(를) String fieldNm로 설정
     */
    public void setFieldNm(String fieldNm) {
        this.fieldNm = fieldNm;
    }

    /**
     * String srchType을 반환
     * 
     * @return String srchType
     */
    public String getSrchType() {
        return srchType;
    }

    /**
     * srchType을 설정
     * 
     * @param srchType 을(를) String srchType로 설정
     */
    public void setSrchType(String srchType) {
        this.srchType = srchType;
    }

    /**
     * String esntlYn을 반환
     * 
     * @return String esntlYn
     */
    public String getEsntlYn() {
        return esntlYn;
    }

    /**
     * esntlYn을 설정
     * 
     * @param esntlYn 을(를) String esntlYn로 설정
     */
    public void setEsntlYn(String esntlYn) {
        this.esntlYn = esntlYn;
    }

    /**
     * Integer sortSn을 반환
     * 
     * @return Integer sortSn
     */
    public Integer getSortSn() {
        return sortSn;
    }

    /**
     * sortSn을 설정
     * 
     * @param sortSn 을(를) Integer sortSn로 설정
     */
    public void setSortSn(Integer sortSn) {
        this.sortSn = sortSn;
    }

    /**
     * String lstIndctYn을 반환
     * 
     * @return String lstIndctYn
     */
    public String getLstIndctYn() {
        return lstIndctYn;
    }

    /**
     * lstIndctYn을 설정
     * 
     * @param lstIndctYn 을(를) String lstIndctYn로 설정
     */
    public void setLstIndctYn(String lstIndctYn) {
        this.lstIndctYn = lstIndctYn;
    }

    /**
     * String inqIndctYn을 반환
     * 
     * @return String inqIndctYn
     */
    public String getInqIndctYn() {
        return inqIndctYn;
    }

    /**
     * inqIndctYn을 설정
     * 
     * @param inqIndctYn 을(를) String inqIndctYn로 설정
     */
    public void setInqIndctYn(String inqIndctYn) {
        this.inqIndctYn = inqIndctYn;
    }

    /**
     * String inptIndctYn을 반환
     * 
     * @return String inptIndctYn
     */
    public String getInptIndctYn() {
        return inptIndctYn;
    }

    /**
     * inptIndctYn을 설정
     * 
     * @param inptIndctYn 을(를) String inptIndctYn로 설정
     */
    public void setInptIndctYn(String inptIndctYn) {
        this.inptIndctYn = inptIndctYn;
    }

}
