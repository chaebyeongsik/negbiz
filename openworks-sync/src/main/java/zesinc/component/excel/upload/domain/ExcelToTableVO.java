package zesinc.component.excel.upload.domain;

import java.io.Serializable;

public class ExcelToTableVO implements Serializable {

    /**  */
    private static final long serialVersionUID = 964600667642400357L;

    /** 한글헤더명 */
    private String[] headerNm;
    /** 영문헤더명 */
    private String[] headerId;
    /** 각 컬럼의 사이즈 */
    private String[] size;
    /** 엑셀에서 읽어들이는 row의 갯수 */
    private Integer rowNo = 0;
    /** input사용여부 (테이블 가장 왼쪽에 체크박스 사용여부) */
    private String inputUseYn;
    /** input 명 */
    private String inputNm;
    /** input Id */
    private String inputId;
    /** input size */
    private String inputSize;
    /** 각 컬럼의 값 */
    private String value;
    /** 헤더 줄 수(기본값:0) */
    private Integer headerLineCnt = 0;

    /**
     * String[] headerNm을 반환
     * 
     * @return String[] headerNm
     */
    public String[] getHeaderNm() {
        return headerNm;
    }

    /**
     * headerNm을 설정
     * 
     * @param headerNm 을(를) String[] headerNm로 설정
     */
    public void setHeaderNm(String[] headerNm) {
        this.headerNm = headerNm;
    }

    /**
     * String[] headerId을 반환
     * 
     * @return String[] headerId
     */
    public String[] getHeaderId() {
        return headerId;
    }

    /**
     * headerId을 설정
     * 
     * @param headerId 을(를) String[] headerId로 설정
     */
    public void setHeaderId(String[] headerId) {
        this.headerId = headerId;
    }

    /**
     * String[] size을 반환
     * 
     * @return String[] size
     */
    public String[] getSize() {
        return size;
    }

    /**
     * size을 설정
     * 
     * @param size 을(를) String[] size로 설정
     */
    public void setSize(String[] size) {
        this.size = size;
    }

    /**
     * Integer rowNo을 반환
     * 
     * @return Integer rowNo
     */
    public Integer getRowNo() {
        return rowNo;
    }

    /**
     * rowNo을 설정
     * 
     * @param rowNo 을(를) Integer rowNo로 설정
     */
    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    /**
     * String inputUseYn을 반환
     * 
     * @return String inputUseYn
     */
    public String getInputUseYn() {
        return inputUseYn;
    }

    /**
     * inputUseYn을 설정
     * 
     * @param inputUseYn 을(를) String inputUseYn로 설정
     */
    public void setInputUseYn(String inputUseYn) {
        this.inputUseYn = inputUseYn;
    }

    /**
     * String inputNm을 반환
     * 
     * @return String inputNm
     */
    public String getInputNm() {
        return inputNm;
    }

    /**
     * inputNm을 설정
     * 
     * @param inputNm 을(를) String inputNm로 설정
     */
    public void setInputNm(String inputNm) {
        this.inputNm = inputNm;
    }

    /**
     * String inputId을 반환
     * 
     * @return String inputId
     */
    public String getInputId() {
        return inputId;
    }

    /**
     * inputId을 설정
     * 
     * @param inputId 을(를) String inputId로 설정
     */
    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    /**
     * String inputSize을 반환
     * 
     * @return String inputSize
     */
    public String getInputSize() {
        return inputSize;
    }

    /**
     * inputSize을 설정
     * 
     * @param inputSize 을(를) String inputSize로 설정
     */
    public void setInputSize(String inputSize) {
        this.inputSize = inputSize;
    }

    /**
     * String value을 반환
     * 
     * @return String value
     */
    public String getValue() {
        return value;
    }

    /**
     * value을 설정
     * 
     * @param value 을(를) String value로 설정
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Integer headerLineCnt을 반환
     * 
     * @return Integer headerLineCnt
     */
    public Integer getHeaderLineCnt() {
        return headerLineCnt;
    }

    /**
     * headerLineCnt을 설정
     * 
     * @param headerLineCnt 을(를) Integer headerLineCnt로 설정
     */
    public void setHeaderLineCnt(Integer headerLineCnt) {
        this.headerLineCnt = headerLineCnt;
    }

}
