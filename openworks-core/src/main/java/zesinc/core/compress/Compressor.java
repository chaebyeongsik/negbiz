/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.core.lang.Validate;

/**
 * ZIP / TAR 유형의 압축/해제 클레스
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
 * @see
 */
public class Compressor {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(Compressor.class);

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

        File resultFile = null;

        if(Validate.isNotEmpty(files) && Validate.isNotEmpty(savePath) && Validate.isNotEmpty(fileName)) {

            File saveFolder = new File(savePath);
            if(!saveFolder.exists()) {
                saveFolder.mkdirs();
            }

            resultFile = new File(saveFolder, fileName);

            OutputStream fos = null;
            ArchiveOutputStream aos = null;

            try {
                fos = new FileOutputStream(resultFile);
                aos = new ArchiveStreamFactory().createArchiveOutputStream(type.getCdId(), fos);

                String modRemovePath = "";
                if(Validate.isNotEmpty(removePath)) {

                    File removeFile = new File(removePath);
                    modRemovePath = removeFile.getAbsolutePath();
                    if(!modRemovePath.endsWith(File.separator)) {
                        modRemovePath += File.separator;
                    }
                }

                logger.debug("removePath is " + modRemovePath);

                ArchiveEntry entry = null;
                for(File file : files) {

                    String archFile = file.getAbsolutePath();
                    if(!modRemovePath.equals("")) {
                        archFile = StringUtils.replaceOnce(archFile, modRemovePath, "");
                    }

                    entry = getArchiveEntry(type, file, archFile);
                    aos.putArchiveEntry(entry);

                    IOUtils.copy(new FileInputStream(file), aos);
                    aos.closeArchiveEntry();

                    logger.debug("Result Compress File is : " + resultFile.getAbsolutePath()
                        + ", Adding Compress File is : " + file.getAbsolutePath());
                }
            } finally {
                if(aos != null) {
                    try {
                        aos.close();
                    } catch (Exception e) {}
                }
                if(fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e) {}
                }
            }
        }

        return resultFile;
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
    public static List<File> uncompress(CompressType type, File compFile, File savedir) throws Exception {

        if(Validate.isEmpty(compFile) || Validate.isEmpty(savedir)) {
            return null;
        }

        if(!savedir.exists()) {
            savedir.mkdirs();
        }

        List<File> files = new ArrayList<File>();

        FileInputStream fis = null;
        ArchiveInputStream ais = null;

        try {
            fis = new FileInputStream(compFile);
            ais = new ArchiveStreamFactory().createArchiveInputStream(type.getCdId(), fis);

            ArchiveEntry entry = null;
            while((entry = ais.getNextEntry()) != null) {
                File uncompFile = new File(savedir, entry.getName());
                uncompFile.getParentFile().mkdirs();

                if(entry.isDirectory()) {
                    uncompFile.mkdir();
                    continue;
                }

                FileOutputStream fos = new FileOutputStream(uncompFile);
                try {
                    IOUtils.copy(ais, fos);
                } finally {
                    fos.close();
                }
                files.add(uncompFile);

                logger.debug("Result Uncompress File is : " + uncompFile.getAbsolutePath());
            }
        } finally {
            if(fis != null) {
                fis.close();
            }
            if(ais != null) {
                try {
                    ais.close();
                } catch (Exception e) {}
            }
        }

        return files;
    }

    /**
     * 압축유형에 따른 ArchiveEntry 객체 생성 후 반환
     * 
     * @param type 압축유형
     * @param file 압축대상파일이며 ArchiveEntry 객체 생성에 인자로 사용
     * @param archFile 압축파일 명
     * @return
     */
    private static ArchiveEntry getArchiveEntry(CompressType type, File file, String archFile) {

        if(CompressType.zip.equals(type)) {
            return new ZipArchiveEntry(file, archFile);
        } else if(CompressType.tar.equals(type)) {
            return new TarArchiveEntry(file, archFile);
        }
        return null;
    }
}
