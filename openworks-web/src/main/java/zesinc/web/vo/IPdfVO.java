/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo;

import java.util.Map;

import com.lowagie.text.Document;

/**
 * PDF View 구현을 위한 기본 공통 메소드 정의<br />
 * lowagie itext 라이브러리 이용
 * < p />
 * org.springframework.web.servlet.view.BeanNameViewResolver를 설정으로 pdfView 를 통하여 화면 출력
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 4.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see zes.base.spring.view.PdfView
 */
public interface IPdfVO {

    /**
     * Create pdf document
     * 
     * @param document
     * @param model
     */
    void createPdfDocument(Document document, Map<String, Object> model) throws Exception;
}
