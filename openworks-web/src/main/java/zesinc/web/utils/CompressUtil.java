/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zesinc.core.compress.CompressType;
import zesinc.core.compress.Compressor;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.IFileVO;

/**
 * <pre>
 * 압축/해제 유형에 따른 압축/해제를 지원하는 유틸 클레스이다.
 * 기본 라이브러리는 org.apache.commons.compress 를 이용한다.
 * 
 * config/commons/core-commons-config.xml 설정에서 기본 압축/해제 유형을 설정한다.
 * </pre>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 9. 26.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 */
public class CompressUtil {

    /** 환경설정 기본 압축/해제 유형 */
    private static final String DEFAULT_TYPE = Config.getString("system-config.defaultCompressType");
    /** 첨부파일 루트 경로 */
    public static final String UPLOAD_ROOT = BaseConfig.UPLOAD_ROOT;

    /**
     * 압축대상 파일 목록을 받아서 압축을 실행한다.
     * 
     * @param files 압축대상 파일 목록
     * @param removePath 압축대상 파일 전체경로에서 압축에서 제외시킬 경로를 지정한다.<br />
     *        예) 파일 전체경로가 '/temp/xxx/ttt/report/excel/report1.xls' 라 할때
     *        '/temp/xxx/ttt/' 경로가 의미 없는 경로인 경우 '/temp/xxx/ttt/'를 인자로 전달하면,
     *        '/report/excel/report1.xls' 로 압축을 실행한다.<br />
     *        윈도우 시스템의 경우 드라이브 문자 포함여부에 유의한다. 예) C:\temp\xxx\ttt\<br />
     *        즉 압축을 해제하면 해제하는 경로에 '/report/excel/report1.xls' 이와 같이 압축이 해제된다.<br />
     *        인자가 없는 경우 원본 전체 경로를 그대로 사용하여 압축한다.
     * @param savePath 압축파일을 저장할 경로를 지정한다.
     * @param fileName 압축파일의 이름을 지정한다.
     * @return 압축된 파일 객체
     * @throws Exception
     */
    public static File compressVo(List<? extends IFileVO> voFiles, String savePath, String fileName) throws Exception {

        List<File> files = new ArrayList<File>();
        String filePath;
        File file;
        for(IFileVO fileVo : voFiles) {
            filePath = UPLOAD_ROOT + fileVo.getFileUrlAddr();
            file = new File(filePath);
            if(file.exists()) {
                files.add(file);
            }
        }
        String removePath = UPLOAD_ROOT;

        return compress(CompressType.valueOf(DEFAULT_TYPE), files, removePath, savePath, fileName);
    }

    /**
     * 압축대상 파일 목록을 받아서 압축을 실행한다.
     * 
     * @param files 압축대상 파일 목록
     * @param removePath 압축대상 파일 전체경로에서 압축에서 제외시킬 경로를 지정한다.<br />
     *        예) 파일 전체경로가 '/temp/xxx/ttt/report/excel/report1.xls' 라 할때
     *        '/temp/xxx/ttt/' 경로가 의미 없는 경로인 경우 '/temp/xxx/ttt/'를 인자로 전달하면,
     *        '/report/excel/report1.xls' 로 압축을 실행한다.<br />
     *        윈도우 시스템의 경우 드라이브 문자 포함여부에 유의한다. 예) C:\temp\xxx\ttt\<br />
     *        즉 압축을 해제하면 해제하는 경로에 '/report/excel/report1.xls' 이와 같이 압축이 해제된다.<br />
     *        인자가 없는 경우 원본 전체 경로를 그대로 사용하여 압축한다.
     * @param savePath 압축파일을 저장할 경로를 지정한다.
     * @param fileName 압축파일의 이름을 지정한다.
     * @return 압축된 파일 객체
     * @throws Exception
     */
    public static File compress(List<File> files, String removePath, String savePath, String fileName) throws Exception {

        return compress(CompressType.valueOf(DEFAULT_TYPE), files, removePath, savePath, fileName);
    }

    /**
     * 압축대상 파일 목록을 받아서 압축을 실행한다.
     * 
     * @param type 압축 유형 CompressType.ZIP / CompressType.TAR 둘중 하나만 지원한다.
     * @param files 압축대상 파일 목록
     * @param removePath 압축대상 파일 전체경로에서 압축에서 제외시킬 경로를 지정한다.<br />
     *        예) 파일 전체경로가 '/temp/xxx/ttt/report/excel/report1.xls' 라 할때
     *        '/temp/xxx/ttt/' 경로가 의미 없는 경로인 경우 '/temp/xxx/ttt/'를 인자로 전달하면,
     *        '/report/excel/report1.xls' 로 압축을 실행한다.<br />
     *        윈도우 시스템의 경우 드라이브 문자 포함여부에 유의한다. 예) C:\temp\xxx\ttt\<br />
     *        즉 압축을 해제하면 해제하는 경로에 '/report/excel/report1.xls' 이와 같이 압축이 해제된다.<br />
     *        인자가 없는 경우 원본 전체 경로를 그대로 사용하여 압축한다.
     * @param savePath 압축파일을 저장할 경로를 지정한다.
     * @param fileName 압축파일의 이름을 지정한다.
     * @return 압축된 파일 객체
     * @throws Exception
     */
    public static File compress(CompressType type, List<File> files, String removePath, String savePath, String fileName) throws Exception {

        if(Validate.isEmpty(files) || Validate.isEmpty(savePath) || Validate.isEmpty(fileName)) {
            throw new IllegalArgumentException("files or savePath or fileName is null");
        }

        return Compressor.compress(type, files, removePath, savePath, fileName);
    }

    /**
     * 압축파일 정보를 받아 지정된 위치에 압축을 해제하고 해제된 파일 목록을 반환한다.
     * 
     * @param compFile 압축파일 정보
     * @param savedir 압축을 해제할 경로 문자열
     * @return
     * @throws Exception
     */
    public static List<File> uncompress(File compFile, String savedir) throws Exception {

        return uncompress(CompressType.valueOf(DEFAULT_TYPE), compFile, savedir);
    }

    /**
     * 압축파일 정보를 받아 지정된 위치에 압축을 해제하고 해제된 파일 목록을 반환한다.
     * 
     * @param type 압축 유형 CompressType.ZIP / CompressType.TAR 둘중 하나만 지원한다.
     * @param compFile 압축파일 정보
     * @param savedir 압축을 해제할 경로 파일객체
     * @return
     * @throws Exception
     */
    public static List<File> uncompress(CompressType type, File compFile, String savedir) throws Exception {

        if(Validate.isEmpty(compFile) || Validate.isEmpty(savedir)) {
            throw new IllegalArgumentException("compFile or savedir is null");
        }

        File uncompDir = new File(savedir);

        if(uncompDir.isFile()) {
            throw new IllegalArgumentException("savedir is file");
        }

        return Compressor.uncompress(type, compFile, uncompDir);
    }
}
