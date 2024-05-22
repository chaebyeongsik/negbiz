/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.popup.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zesinc.component.file.FileService;
import zesinc.component.popup.JsPopupMapper;
import zesinc.component.popup.JsPopupService;
import zesinc.component.popup.domain.JsPopupVO;
import zesinc.core.lang.Validate;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 팝업 표시를 위한 서비스 구현 객체
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 7. 24.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Service("opJsPopupService")
public class JsPopupServiceImpl extends EgovAbstractServiceImpl implements JsPopupService {

    @Resource(name = "opJsPopupDao")
    private JsPopupMapper opJsPopupDao;

    // 첨부파일
    @Resource(name = "opFileService")
    private FileService opFileService;

    @Override
    public JsPopupVO selectPopup(JsPopupVO jsPopupVo) {

        JsPopupVO dataVo = opJsPopupDao.selectPopup(jsPopupVo);
        // 첨부파일
        if(Validate.isNotEmpty(dataVo.getFileSn())) {
            dataVo.setFileList(opFileService.selectFileList(dataVo.getFileSn()));
        }

        return dataVo;
    }

    @Override
    public List<JsPopupVO> selectPopupList(JsPopupVO jsPopupVo) {

        return opJsPopupDao.selectPopupList(jsPopupVo);
    }
}
