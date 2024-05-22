package zesinc.web.vo.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;

import zesinc.web.vo.BaseVO;

public class BbsConfigCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -1819209775167073864L;

    /** 게시판코드 */
    private Integer bbsSn;
    /** 게시판코드명 */
    private String bbsSnNm;
    /** 게시판명 */
    private String bbsNm;
    /** 게시판설명 */
    private String bbsExpln;
    /** 정렬순서 */
    private Integer sortSn;
    /** 사용여부 */
    private String useYn;
    /** 등록일시 */
    private Date regDt;
    /** 수정일시 */
    private Date updtDt;
    /** 분류명 */
    private String clsfNms;
    /** 게시글수 */
    private String bbscttCo;
    /** 의견글수 */
    private String opatclCo;
    /** 파일수 */
    private String fileCo;
    /** 게시판코드 다중코드 */
    private String bbsSns;
    /** 게시판설정 구분코드 */
    private Integer seCdId;

    /** 분류 목록 */
    private List<BbsCtgryCacheVO> bbsCtgryList;

    /** 항목관리 목록 */
    private List<BbsItemCacheVO> bbsItemList;

    /** 목록 항목 */
    private List<BbsItemCacheVO> listColunms;
    /** 상세보기 항목 */
    private List<BbsItemCacheVO> redngColunms;
    /** 입력폼 항목 */
    private List<BbsItemCacheVO> formColunms;
    /** 검색대상 항목 */
    private List<BbsItemCacheVO> searchColunms;

    /** 항목정보 멥 */
    private Map<String, BbsItemCacheVO> bbsItemMap;

    /*
     * -----------------------------------------------------------
     * GLOBAL
     */
    /** 게시판스킨경로 */
    private String bbsSkinCours;
    /** 분류사용여부 */
    private String clsfUseYn = "N";
    /** 공지글사용여부 */
    private String ntcUseYn = "N";
    /** FEED 사용여부 */
    private String nfeedUseYn = "N";
    /** 게시사용여부 */
    private String pstgUseYn = "N";
    /** 개인정보수집여부 */
    private String prvcClctAgreYn;
    /** 개인정보삭제구분 */
    private String prvcDelSeCd;
    /** 개인정보삭제일 */
    private String prvcDelYmd;
    /** 개인정보보유연한 */
    private String prvcStrgDayCnt;
    /** 계층형 사용여부 */
    private String lyrUseYn = "N";
    /** 썸네일 사용여부 */
    private String thmbUseYn = "N";
    /** 가로사이즈 */
    private Integer wdthSz;
    /** 세로사이즈 */
    private Integer vrtcSz;
    /** 마크사용여부 */
    private String wtmkUseYn;
    /** 마크파일 */
    private String wtmkFileNm;
    /** 마크위치 */
    private String wtmkPstnNm;
    /** 마크투명 */
    private String wtmkTrnspc;
    /*
     * -----------------------------------------------------------
     * LIST
     */
    /** 페이지당표시글수 */
    private Integer pagePstCnt = 10;
    /** 제목조정길이 */
    private Integer ttlAjmtSz = 60;
    /** 새글표시기준일 */
    private Integer newIndctDayCnt = 3;
    /** 게시글강조조회수 */
    private Integer pstEmphsInqCnt = 100;
    /** 작성자표시코드 */
    private Integer wrtrIndctSn;

    /*
     * -----------------------------------------------------------
     * VIEW
     */
    /** 글이동(이전/다음글)표시여부 */
    private String bfrAftrDocIndctYn = "N";
    /** 추천사용여부 */
    private String rcmdtnUseYn = "N";
    /** 신고사용여부 */
    private String dclrUseYn = "N";
    /** 만족도사용여부 */
    private String dgstfnUseYn = "N";
    /** 의견글사용여부 */
    private String opnnDocYn = "N";
    /** 태그사용여부 */
    private String tagUseYn = "N";
    /** 조회수증가제한시간 */
    private Integer inqCntLmtHrCnt = 3;

    /*
     * -----------------------------------------------------------
     * FORM
     */
    /** 관리자에디터사용여부 */
    private String mngrEdtrUseYn = "N";
    /** 사용자에디터사용여부 */
    private String userEdtrUseYn = "N";
    /** 첨부파일사용여부 */
    private String atchFileUseYn = "N";
    /** 업로더종류 */
    private Integer uldClsfSn = 1000;
    /** 최대업로드파일수 */
    private Integer fileLmtCnt = 1;
    /** 최대파일사이즈 */
    private Integer lmtFileSz = 0;
    /** 전체업로드사이즈 */
    private Integer wholUldSz = 0;
    /** 첨부파일허용확장자 */
    private String prmsnFileExtnMttr;
    /** CAPTCHA사용여부 */
    private String cchaUseYn = "N";
    /** 공개여부 */
    private String rlsYn = "Y";
    /** 금지단어사용여부 */
    private String phbwdUseYn;
    /** 금지단어코드 : 금지단어관리 기능 참조 */
    private String phbwdCdId;

    /*
     * -----------------------------------------------------------
     * ITEM
     */
    /** 컬럼ID */
    private String colId;
    /** 컬럼명 */
    private String colNm;
    /** 컬럼명 */
    private String scrnNm;
    /** 컬럼유형 */
    private String colTypeNm;
    /** 안내문구 */
    private String bbsColExpln;
    /** 검색조건여부 */
    private String srchStngYn = "N";
    /** 검색유형 */
    private String srchType;
    /** 필수여부 */
    private String esntlYn;

    /*
     * -----------------------------------------------------------
     * SITE_SN_CONFIG
     */

    /** 도메인코드를 키로하는 스킨설정 정보 */
    private Map<Integer, BbsDomnCacheVO> skins;

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
     * String bbsSnNm을 반환
     *
     * @return String bbsSnNm
     */
    public String getBbsSnNm() {
        return bbsSnNm;
    }

    /**
     * bbsSnNm을 설정
     *
     * @param bbsSnNm 을(를) String bbsSnNm로 설정
     */
    public void setBbsSnNm(String bbsSnNm) {
        this.bbsSnNm = bbsSnNm;
    }

    /**
     * String bbsNm을 반환
     *
     * @return String bbsNm
     */
    public String getBbsNm() {
        return bbsNm;
    }

    /**
     * bbsNm을 설정
     *
     * @param bbsNm 을(를) String bbsNm로 설정
     */
    public void setBbsNm(String bbsNm) {
        this.bbsNm = bbsNm;
    }

    /**
     * String bbsExpln을 반환
     *
     * @return String bbsExpln
     */
    public String getBbsExpln() {
        return bbsExpln;
    }

    /**
     * bbsExpln을 설정
     *
     * @param bbsExpln 을(를) String bbsExpln로 설정
     */
    public void setBbsExpln(String bbsExpln) {
        this.bbsExpln = bbsExpln;
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
     * String useYn을 반환
     *
     * @return String useYn
     */
    public String getUseYn() {
        return useYn;
    }

    /**
     * useYn을 설정
     *
     * @param useYn 을(를) String useYn로 설정
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * Date regDt을 반환
     *
     * @return Date regDt
     */
    public Date getRegDt() {
        return regDt;
    }

    /**
     * regDt을 설정
     *
     * @param regDt 을(를) Date regDt로 설정
     */
    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    /**
     * Date updtDt을 반환
     *
     * @return Date updtDt
     */
    public Date getUpdtDt() {
        return updtDt;
    }

    /**
     * updtDt을 설정
     *
     * @param updtDt 을(를) Date updtDt로 설정
     */
    public void setUpdtDt(Date updtDt) {
        this.updtDt = updtDt;
    }

    /**
     * String clsfNms을 반환
     *
     * @return String clsfNms
     */
    public String getClsfNms() {
        return clsfNms;
    }

    /**
     * clsfNms을 설정
     *
     * @param clsfNms 을(를) String clsfNms로 설정
     */
    public void setClsfNms(String clsfNms) {
        this.clsfNms = clsfNms;
    }

    /**
     * String bbscttCo을 반환
     *
     * @return String bbscttCo
     */
    public String getBbscttCo() {
        return bbscttCo;
    }

    /**
     * bbscttCo을 설정
     *
     * @param bbscttCo 을(를) String bbscttCo로 설정
     */
    public void setBbscttCo(String bbscttCo) {
        this.bbscttCo = bbscttCo;
    }

    /**
     * String opatclCo을 반환
     *
     * @return String opatclCo
     */
    public String getOpatclCo() {
        return opatclCo;
    }

    /**
     * opatclCo을 설정
     *
     * @param opatclCo 을(를) String opatclCo로 설정
     */
    public void setOpatclCo(String opatclCo) {
        this.opatclCo = opatclCo;
    }

    /**
     * String fileCo을 반환
     *
     * @return String fileCo
     */
    public String getFileCo() {
        return fileCo;
    }

    /**
     * fileCo을 설정
     *
     * @param fileCo 을(를) String fileCo로 설정
     */
    public void setFileCo(String fileCo) {
        this.fileCo = fileCo;
    }

    /**
     * String bbsSns을 반환
     *
     * @return String bbsSns
     */
    public String getBbsSns() {
        return bbsSns;
    }

    /**
     * bbsSns을 설정
     *
     * @param bbsSns 을(를) String bbsSns로 설정
     */
    public void setBbsSns(String bbsSns) {
        this.bbsSns = bbsSns;
    }

    /**
     * Integer seCdId을 반환
     *
     * @return Integer seCdId
     */
    public Integer getSeCdId() {
        return seCdId;
    }

    /**
     * seCdId을 설정
     *
     * @param seCdId 을(를) Integer seCdId로 설정
     */
    public void setSeCdId(Integer seCdId) {
        this.seCdId = seCdId;
    }

    /**
     * List<BbsCtgryCacheVO> bbsCtgryList을 반환
     *
     * @return List<BbsCtgryCacheVO> bbsCtgryList
     */
    public List<BbsCtgryCacheVO> getBbsCtgryList() {
        return bbsCtgryList;
    }

    /**
     * bbsCtgryList을 설정
     *
     * @param bbsCtgryList 을(를) List<BbsCtgryCacheVO> bbsCtgryList로 설정
     */
    public void setBbsCtgryList(List<BbsCtgryCacheVO> bbsCtgryList) {
        this.bbsCtgryList = bbsCtgryList;
    }

    /**
     * List<BbsItemCacheVO> bbsItemList을 반환
     *
     * @return List<BbsItemCacheVO> bbsItemList
     */
    public List<BbsItemCacheVO> getBbsItemList() {
        return bbsItemList;
    }

    /**
     * bbsItemList을 설정
     *
     * @param bbsItemList 을(를) List<BbsItemCacheVO> bbsItemList로 설정
     */
    public void setBbsItemList(List<BbsItemCacheVO> bbsItemList) {
        this.bbsItemList = bbsItemList;
    }

    /**
     * List<BbsItemCacheVO> listColunms을 반환
     *
     * @return List<BbsItemCacheVO> listColunms
     */
    public List<BbsItemCacheVO> getListColunms() {
        return listColunms;
    }

    /**
     * listColunms을 설정
     *
     * @param listColunms 을(를) List<BbsItemCacheVO> listColunms로 설정
     */
    public void setListColunms(List<BbsItemCacheVO> listColunms) {
        this.listColunms = listColunms;
    }

    /**
     * List<BbsItemCacheVO> redngColunms을 반환
     *
     * @return List<BbsItemCacheVO> redngColunms
     */
    public List<BbsItemCacheVO> getRedngColunms() {
        return redngColunms;
    }

    /**
     * redngColunms을 설정
     *
     * @param redngColunms 을(를) List<BbsItemCacheVO> redngColunms로 설정
     */
    public void setRedngColunms(List<BbsItemCacheVO> redngColunms) {
        this.redngColunms = redngColunms;
    }

    /**
     * List<BbsItemCacheVO> formColunms을 반환
     *
     * @return List<BbsItemCacheVO> formColunms
     */
    public List<BbsItemCacheVO> getFormColunms() {
        return formColunms;
    }

    /**
     * formColunms을 설정
     *
     * @param formColunms 을(를) List<BbsItemCacheVO> formColunms로 설정
     */
    public void setFormColunms(List<BbsItemCacheVO> formColunms) {
        this.formColunms = formColunms;
    }

    /**
     * List<BbsItemCacheVO> searchColunms을 반환
     *
     * @return List<BbsItemCacheVO> searchColunms
     */
    public List<BbsItemCacheVO> getSearchColunms() {
        return searchColunms;
    }

    /**
     * searchColunms을 설정
     *
     * @param searchColunms 을(를) List<BbsItemCacheVO> searchColunms로 설정
     */
    public void setSearchColunms(List<BbsItemCacheVO> searchColunms) {
        this.searchColunms = searchColunms;
    }

    /**
     * Map<String,BbsItemCacheVO> bbsItemMap을 반환
     *
     * @return Map<String,BbsItemCacheVO> bbsItemMap
     */
    public Map<String, BbsItemCacheVO> getBbsItemMap() {
        return bbsItemMap;
    }

    /**
     * bbsItemMap을 설정
     *
     * @param bbsItemMap 을(를) Map<String,BbsItemCacheVO> bbsItemMap로 설정
     */
    public void setBbsItemMap(Map<String, BbsItemCacheVO> bbsItemMap) {
        this.bbsItemMap = bbsItemMap;
    }

    /**
     * String bbsSkinCours을 반환
     *
     * @return String bbsSkinCours
     */
    public String getBbsSkinCours() {
        return bbsSkinCours;
    }

    /**
     * bbsSkinCours을 설정
     *
     * @param bbsSkinCours 을(를) String bbsSkinCours로 설정
     */
    public void setBbsSkinCours(String bbsSkinCours) {
        this.bbsSkinCours = bbsSkinCours;
    }

    /**
     * String clsfUseYn을 반환
     *
     * @return String clsfUseYn
     */
    public String getClsfUseYn() {
        return clsfUseYn;
    }

    /**
     * clsfUseYn을 설정
     *
     * @param clsfUseYn 을(를) String clsfUseYn로 설정
     */
    public void setClsfUseYn(String clsfUseYn) {
        this.clsfUseYn = clsfUseYn;
    }

    /**
     * String ntcUseYn을 반환
     *
     * @return String ntcUseYn
     */
    public String getNtcUseYn() {
        return ntcUseYn;
    }

    /**
     * ntcUseYn을 설정
     *
     * @param ntcUseYn 을(를) String ntcUseYn로 설정
     */
    public void setNtcUseYn(String ntcUseYn) {
        this.ntcUseYn = ntcUseYn;
    }

    /**
     * String nfeedUseYn을 반환
     *
     * @return String nfeedUseYn
     */
    public String getNfeedUseYn() {
        return nfeedUseYn;
    }

    /**
     * nfeedUseYn을 설정
     *
     * @param nfeedUseYn 을(를) String nfeedUseYn로 설정
     */
    public void setNfeedUseYn(String nfeedUseYn) {
        this.nfeedUseYn = nfeedUseYn;
    }

    /**
     * String pstgUseYn을 반환
     *
     * @return String pstgUseYn
     */
    public String getPstgUseYn() {
        return pstgUseYn;
    }

    /**
     * pstgUseYn을 설정
     *
     * @param pstgUseYn 을(를) String pstgUseYn로 설정
     */
    public void setPstgUseYn(String pstgUseYn) {
        this.pstgUseYn = pstgUseYn;
    }

    /**
     * String prvcClctAgreYn을 반환
     * @return String prvcClctAgreYn
     */
    public String getPrvcClctAgreYn() {
        return prvcClctAgreYn;
    }

    /**
     * prvcClctAgreYn을 설정
     * @param prvcClctAgreYn 을(를) String prvcClctAgreYn로 설정
     */
    public void setPrvcClctAgreYn(String prvcClctAgreYn) {
        this.prvcClctAgreYn = prvcClctAgreYn;
    }

    /**
     * String prvcDelSeCd을 반환
     * @return String prvcDelSeCd
     */
    public String getPrvcDelSeCd() {
        return prvcDelSeCd;
    }

    /**
     * prvcDelSeCd을 설정
     * @param prvcDelSeCd 을(를) String prvcDelSeCd로 설정
     */
    public void setPrvcDelSeCd(String prvcDelSeCd) {
        this.prvcDelSeCd = prvcDelSeCd;
    }

    /**
     * String prvcDelYmd을 반환
     * @return String prvcDelYmd
     */
    public String getPrvcDelYmd() {
        return prvcDelYmd;
    }

    /**
     * prvcDelYmd을 설정
     * @param prvcDelYmd 을(를) String prvcDelYmd로 설정
     */
    public void setPrvcDelYmd(String prvcDelYmd) {
        this.prvcDelYmd = prvcDelYmd;
    }

    /**
     * String prvcStrgDayCnt을 반환
     * @return String prvcStrgDayCnt
     */
    public String getPrvcStrgDayCnt() {
        return prvcStrgDayCnt;
    }

    /**
     * prvcStrgDayCnt을 설정
     * @param prvcStrgDayCnt 을(를) String prvcStrgDayCnt로 설정
     */
    public void setPrvcStrgDayCnt(String prvcStrgDayCnt) {
        this.prvcStrgDayCnt = prvcStrgDayCnt;
    }

    /**
     * String lyrUseYn을 반환
     *
     * @return String lyrUseYn
     */
    public String getLyrUseYn() {
        return lyrUseYn;
    }

    /**
     * lyrUseYn을 설정
     *
     * @param lyrUseYn 을(를) String lyrUseYn로 설정
     */
    public void setLyrUseYn(String lyrUseYn) {
        this.lyrUseYn = lyrUseYn;
    }

    /**
     * String thmbUseYn을 반환
     *
     * @return String thmbUseYn
     */
    public String getThmbUseYn() {
        return thmbUseYn;
    }

    /**
     * thmbUseYn을 설정
     *
     * @param thmbUseYn 을(를) String thmbUseYn로 설정
     */
    public void setThmbUseYn(String thmbUseYn) {
        this.thmbUseYn = thmbUseYn;
    }

    /**
     * Integer wdthSz을 반환
     *
     * @return Integer wdthSz
     */
    public Integer getWdthSz() {
        return wdthSz;
    }

    /**
     * wdthSz을 설정
     *
     * @param wdthSz 을(를) Integer wdthSz로 설정
     */
    public void setWdthSz(Integer wdthSz) {
        this.wdthSz = wdthSz;
    }

    /**
     * Integer vrtcSz을 반환
     *
     * @return Integer vrtcSz
     */
    public Integer getVrtcSz() {
        return vrtcSz;
    }

    /**
     * vrtcSz을 설정
     *
     * @param vrtcSz 을(를) Integer vrtcSz로 설정
     */
    public void setVrtcSz(Integer vrtcSz) {
        this.vrtcSz = vrtcSz;
    }

    /**
     * String wtmkUseYn을 반환
     *
     * @return String wtmkUseYn
     */
    public String getWtmkUseYn() {
        return wtmkUseYn;
    }

    /**
     * wtmkUseYn을 설정
     *
     * @param wtmkUseYn 을(를) String wtmkUseYn로 설정
     */
    public void setWtmkUseYn(String wtmkUseYn) {
        this.wtmkUseYn = wtmkUseYn;
    }

    /**
     * String wtmkFileNm을 반환
     *
     * @return String wtmkFileNm
     */
    public String getWtmkFileNm() {
        return wtmkFileNm;
    }

    /**
     * wtmkFileNm을 설정
     *
     * @param wtmkFileNm 을(를) String wtmkFileNm로 설정
     */
    public void setWtmkFileNm(String wtmkFileNm) {
        this.wtmkFileNm = wtmkFileNm;
    }

    /**
     * String wtmkPstnNm을 반환
     *
     * @return String wtmkPstnNm
     */
    public String getWtmkPstnNm() {
        return wtmkPstnNm;
    }

    /**
     * wtmkPstnNm을 설정
     *
     * @param wtmkPstnNm 을(를) String wtmkPstnNm로 설정
     */
    public void setWtmkPstnNm(String wtmkPstnNm) {
        this.wtmkPstnNm = wtmkPstnNm;
    }

    /**
     * String wtmkTrnspc을 반환
     *
     * @return String wtmkTrnspc
     */
    public String getWtmkTrnspc() {
        return wtmkTrnspc;
    }

    /**
     * wtmkTrnspc을 설정
     *
     * @param wtmkTrnspc 을(를) String wtmkTrnspc로 설정
     */
    public void setWtmkTrnspc(String wtmkTrnspc) {
        this.wtmkTrnspc = wtmkTrnspc;
    }

    /**
     * Integer pagePstCnt을 반환
     *
     * @return Integer pagePstCnt
     */
    public Integer getPagePstCnt() {
        return pagePstCnt;
    }

    /**
     * pagePstCnt을 설정
     *
     * @param pagePstCnt 을(를) Integer pagePstCnt로 설정
     */
    public void setPagePstCnt(Integer pagePstCnt) {
        this.pagePstCnt = pagePstCnt;
    }

    /**
     * Integer ttlAjmtSz을 반환
     *
     * @return Integer ttlAjmtSz
     */
    public Integer getTtlAjmtSz() {
        return ttlAjmtSz;
    }

    /**
     * ttlAjmtSz을 설정
     *
     * @param ttlAjmtSz 을(를) Integer ttlAjmtSz로 설정
     */
    public void setTtlAjmtSz(Integer ttlAjmtSz) {
        this.ttlAjmtSz = ttlAjmtSz;
    }

    /**
     * Integer newIndctDayCnt을 반환
     *
     * @return Integer newIndctDayCnt
     */
    public Integer getNewIndctDayCnt() {
        return newIndctDayCnt;
    }

    /**
     * newIndctDayCnt을 설정
     *
     * @param newIndctDayCnt 을(를) Integer newIndctDayCnt로 설정
     */
    public void setNewIndctDayCnt(Integer newIndctDayCnt) {
        this.newIndctDayCnt = newIndctDayCnt;
    }

    /**
     * Integer pstEmphsInqCnt을 반환
     *
     * @return Integer pstEmphsInqCnt
     */
    public Integer getPstEmphsInqCnt() {
        return pstEmphsInqCnt;
    }

    /**
     * pstEmphsInqCnt을 설정
     *
     * @param pstEmphsInqCnt 을(를) Integer pstEmphsInqCnt로 설정
     */
    public void setPstEmphsInqCnt(Integer pstEmphsInqCnt) {
        this.pstEmphsInqCnt = pstEmphsInqCnt;
    }

    /**
     * Integer wrtrIndctSn을 반환
     *
     * @return Integer wrtrIndctSn
     */
    public Integer getWrtrIndctSn() {
        return wrtrIndctSn;
    }

    /**
     * wrtrIndctSn을 설정
     *
     * @param wrtrIndctSn 을(를) Integer wrtrIndctSn로 설정
     */
    public void setWrtrIndctSn(Integer wrtrIndctSn) {
        this.wrtrIndctSn = wrtrIndctSn;
    }

    /**
     * String bfrAftrDocIndctYn을 반환
     *
     * @return String bfrAftrDocIndctYn
     */
    public String getBfrAftrDocIndctYn() {
        return bfrAftrDocIndctYn;
    }

    /**
     * bfrAftrDocIndctYn을 설정
     *
     * @param bfrAftrDocIndctYn 을(를) String bfrAftrDocIndctYn로 설정
     */
    public void setBfrAftrDocIndctYn(String bfrAftrDocIndctYn) {
        this.bfrAftrDocIndctYn = bfrAftrDocIndctYn;
    }

    /**
     * String rcmdtnUseYn을 반환
     *
     * @return String rcmdtnUseYn
     */
    public String getRcmdtnUseYn() {
        return rcmdtnUseYn;
    }

    /**
     * rcmdtnUseYn을 설정
     *
     * @param rcmdtnUseYn 을(를) String rcmdtnUseYn로 설정
     */
    public void setRcmdtnUseYn(String rcmdtnUseYn) {
        this.rcmdtnUseYn = rcmdtnUseYn;
    }

    /**
     * String dclrUseYn을 반환
     *
     * @return String dclrUseYn
     */
    public String getDclrUseYn() {
        return dclrUseYn;
    }

    /**
     * dclrUseYn을 설정
     *
     * @param dclrUseYn 을(를) String dclrUseYn로 설정
     */
    public void setDclrUseYn(String dclrUseYn) {
        this.dclrUseYn = dclrUseYn;
    }

    /**
     * String dgstfnUseYn을 반환
     *
     * @return String dgstfnUseYn
     */
    public String getDgstfnUseYn() {
        return dgstfnUseYn;
    }

    /**
     * dgstfnUseYn을 설정
     *
     * @param dgstfnUseYn 을(를) String dgstfnUseYn로 설정
     */
    public void setDgstfnUseYn(String dgstfnUseYn) {
        this.dgstfnUseYn = dgstfnUseYn;
    }

    /**
     * String opnnDocYn을 반환
     *
     * @return String opnnDocYn
     */
    public String getOpnnDocYn() {
        return opnnDocYn;
    }

    /**
     * opnnDocYn을 설정
     *
     * @param opnnDocYn 을(를) String opnnDocYn로 설정
     */
    public void setOpnnDocYn(String opnnDocYn) {
        this.opnnDocYn = opnnDocYn;
    }

    /**
     * String tagUseYn을 반환
     *
     * @return String tagUseYn
     */
    public String getTagUseYn() {
        return tagUseYn;
    }

    /**
     * tagUseYn을 설정
     *
     * @param tagUseYn 을(를) String tagUseYn로 설정
     */
    public void setTagUseYn(String tagUseYn) {
        this.tagUseYn = tagUseYn;
    }

    /**
     * Integer inqCntLmtHrCnt을 반환
     *
     * @return Integer inqCntLmtHrCnt
     */
    public Integer getInqCntLmtHrCnt() {
        return inqCntLmtHrCnt;
    }

    /**
     * inqCntLmtHrCnt을 설정
     *
     * @param inqCntLmtHrCnt 을(를) Integer inqCntLmtHrCnt로 설정
     */
    public void setInqCntLmtHrCnt(Integer inqCntLmtHrCnt) {
        this.inqCntLmtHrCnt = inqCntLmtHrCnt;
    }

    /**
     * String mngrEdtrUseYn을 반환
     *
     * @return String mngrEdtrUseYn
     */
    public String getMngrEdtrUseYn() {
        return mngrEdtrUseYn;
    }

    /**
     * mngrEdtrUseYn을 설정
     *
     * @param mngrEdtrUseYn 을(를) String mngrEdtrUseYn로 설정
     */
    public void setMngrEdtrUseYn(String mngrEdtrUseYn) {
        this.mngrEdtrUseYn = mngrEdtrUseYn;
    }

    /**
     * String userEdtrUseYn을 반환
     *
     * @return String userEdtrUseYn
     */
    public String getUserEdtrUseYn() {
        return userEdtrUseYn;
    }

    /**
     * userEdtrUseYn을 설정
     *
     * @param userEdtrUseYn 을(를) String userEdtrUseYn로 설정
     */
    public void setUserEdtrUseYn(String userEdtrUseYn) {
        this.userEdtrUseYn = userEdtrUseYn;
    }

    /**
     * String atchFileUseYn을 반환
     *
     * @return String atchFileUseYn
     */
    public String getAtchFileUseYn() {
        return atchFileUseYn;
    }

    /**
     * atchFileUseYn을 설정
     *
     * @param atchFileUseYn 을(를) String atchFileUseYn로 설정
     */
    public void setAtchFileUseYn(String atchFileUseYn) {
        this.atchFileUseYn = atchFileUseYn;
    }

    /**
     * Integer uldClsfSn을 반환
     *
     * @return Integer uldClsfSn
     */
    public Integer getUldClsfSn() {
        return uldClsfSn;
    }

    /**
     * uldClsfSn을 설정
     *
     * @param uldClsfSn 을(를) Integer uldClsfSn로 설정
     */
    public void setUldClsfSn(Integer uldClsfSn) {
        this.uldClsfSn = uldClsfSn;
    }

    /**
     * Integer fileLmtCnt을 반환
     *
     * @return Integer fileLmtCnt
     */
    public Integer getFileLmtCnt() {
        return fileLmtCnt;
    }

    /**
     * fileLmtCnt을 설정
     *
     * @param fileLmtCnt 을(를) Integer fileLmtCnt로 설정
     */
    public void setFileLmtCnt(Integer fileLmtCnt) {
        this.fileLmtCnt = fileLmtCnt;
    }

    /**
     * Integer lmtFileSz을 반환
     *
     * @return Integer lmtFileSz
     */
    public Integer getLmtFileSz() {
        return lmtFileSz;
    }

    /**
     * lmtFileSz을 설정
     *
     * @param lmtFileSz 을(를) Integer lmtFileSz로 설정
     */
    public void setLmtFileSz(Integer lmtFileSz) {
        this.lmtFileSz = lmtFileSz;
    }

    /**
     * Integer wholUldSz을 반환
     *
     * @return Integer wholUldSz
     */
    public Integer getWholUldSz() {
        return wholUldSz;
    }

    /**
     * wholUldSz을 설정
     *
     * @param wholUldSz 을(를) Integer wholUldSz로 설정
     */
    public void setWholUldSz(Integer wholUldSz) {
        this.wholUldSz = wholUldSz;
    }

    /**
     * String prmsnFileExtnMttr을 반환
     *
     * @return String prmsnFileExtnMttr
     */
    public String getPrmsnFileExtnMttr() {
        return prmsnFileExtnMttr;
    }

    /**
     * prmsnFileExtnMttr을 설정
     *
     * @param prmsnFileExtnMttr 을(를) String prmsnFileExtnMttr로 설정
     */
    public void setPrmsnFileExtnMttr(String prmsnFileExtnMttr) {
        this.prmsnFileExtnMttr = prmsnFileExtnMttr;
    }

    /**
     * String cchaUseYn을 반환
     *
     * @return String cchaUseYn
     */
    public String getCchaUseYn() {
        return cchaUseYn;
    }

    /**
     * cchaUseYn을 설정
     *
     * @param cchaUseYn 을(를) String cchaUseYn로 설정
     */
    public void setCchaUseYn(String cchaUseYn) {
        this.cchaUseYn = cchaUseYn;
    }

    /**
     * String rlsYn을 반환
     *
     * @return String rlsYn
     */
    public String getRlsYn() {
        return rlsYn;
    }

    /**
     * rlsYn을 설정
     *
     * @param rlsYn 을(를) String rlsYn로 설정
     */
    public void setRlsYn(String rlsYn) {
        this.rlsYn = rlsYn;
    }

    /**
     * String phbwdUseYn을 반환
     *
     * @return String phbwdUseYn
     */
    public String getPhbwdUseYn() {
        return phbwdUseYn;
    }

    /**
     * phbwdUseYn을 설정
     *
     * @param phbwdUseYn 을(를) String phbwdUseYn로 설정
     */
    public void setPrhibtWrdUseYn(String phbwdUseYn) {
        this.phbwdUseYn = phbwdUseYn;
    }

    /**
     * String phbwdCdId을 반환
     *
     * @return String phbwdCdId
     */
    public String getPhbwdCdId() {
        return phbwdCdId;
    }

    /**
     * phbwdCdId을 설정
     *
     * @param phbwdCdId 을(를) String phbwdCdId로 설정
     */
    public void setPhbwdCdId(String phbwdCdId) {
        this.phbwdCdId = phbwdCdId;
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
     * String srchStngYn을 반환
     *
     * @return String srchStngYn
     */
    public String getSrchStngYn() {
        return srchStngYn;
    }

    /**
     * srchStngYn을 설정
     *
     * @param srchStngYn 을(를) String srchStngYn로 설정
     */
    public void setSrchStngYn(String srchStngYn) {
        this.srchStngYn = srchStngYn;
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
     * Map<String,BbsDomnCacheVO> skins을 반환
     *
     * @return Map<String,BbsDomnCacheVO> skins
     */
    public Map<Integer, BbsDomnCacheVO> getSkins() {
        return skins;
    }

    /**
     * skins을 설정
     *
     * @param skins 을(를) Map<String,BbsDomnCacheVO> skins로 설정
     */
    public void setSkins(Map<Integer, BbsDomnCacheVO> skins) {
        this.skins = skins;
    }

}
