/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.resrce.file.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zesinc.component.resrce.file.ResrceFileMapper;
import zesinc.component.resrce.file.ResrceFileService;
import zesinc.component.resrce.file.domain.ResrceFileVO;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 자원파일 정보 서비스 구현 클레스
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

@Service("opResrceFileDownService")
public class ResrceFileServiceImpl extends EgovAbstractServiceImpl implements ResrceFileService {

    @Resource(name = "opResrceFileDownDao")
    private ResrceFileMapper opResrceFileDownDao;

    @Override
    public ResrceFileVO selectResrceFile(ResrceFileVO resrceFileVo) {

        ResrceFileVO dataVo = opResrceFileDownDao.selectResrceFile(resrceFileVo);

        return dataVo;
    }

}
