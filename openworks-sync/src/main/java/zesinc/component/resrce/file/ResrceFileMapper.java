/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.resrce.file;

import zesinc.component.resrce.file.domain.ResrceFileVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 자원파일 정보 DAO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-10-14.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Mapper("opResrceFileDownDao")
public interface ResrceFileMapper {

    /**
     * 자원파일 상세 조회
     * 
     * @param resrceFileVo
     * @return
     */
    ResrceFileVO selectResrceFile(ResrceFileVO resrceFileVo);
}
