package zesinc.web.utils.image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.imaging.Imaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.web.utils.FileUtil;

public class ThumbnailUtil {

    private static Logger logger = LoggerFactory.getLogger(ThumbnailUtil.class);

    public static final int ZERO = 0;
    public static final int MINUS_ONE = -1;

    /**
     * <pre>
     * 전체 파일 경로와 가로 세로 크기를 인자로 생성.
     * 0 값은 축소 비율을 계산하기 때문에 width와 height 모두 0 값을 가질 수 없음.
     * -1 값은 동일한 크기로 생성하며, 파일 용량을 줄이기 위한 목적으로 사용한다.
     * 이외는 지정된 값만 큼 축소한다.
     * </pre>
     * 
     * @param src 원본
     * @param dest 대상(섬네일)
     * @param width 가로 크기 px
     * @param height 세로 크기 px
     * @return 성공시 true 실패시 false
     */
    public static Boolean resize(String src, String dest, int width, int height) {

        File sFile = new File(src);
        File dFile = new File(dest);

        return resize(sFile, dFile, width, height);
    }

    /**
     * <pre>
     * 원본과 대상 파일 객체와 가로/세로 크기(px 단위)를 인자로 생성
     * 0 값은 축소 비율을 계산하기 때문에 width와 height 모두 0 값을 가질 수 없음.
     * -1 값은 동일한 크기로 생성하며, 파일 용량을 줄이기 위한 목적으로 사용한다.
     * 이외는 지정된 값만 큼 축소한다.
     * </pre>
     * 
     * @param src 원본
     * @param dest 대상(섬네일)
     * @param width 가로 크기 px
     * @param height 세로 크기 px
     * @return 성공시 true 실패시 false
     */
    public static Boolean resize(File src, File dest, int width, int height) {

        Boolean succYn = Boolean.TRUE;
        try {

            Image srcImg = null;
            if(!src.canRead()) {
                logger.error("원본 파일을 읽을 수 없습니다. 원본 : {}", src.getAbsolutePath());
                return Boolean.FALSE;
            }

            String ext = FileUtil.extension(src.getName()).toLowerCase();
            if(ext.equals("jpg") || ext.equals("jpeg")) {
                srcImg = ImageReadUtil.readJpegImage(src);
            } else {
                srcImg = Imaging.getBufferedImage(src);
            }

            TargetImageSize tis = getTargetImageSize(srcImg, width, height);

            Thumbnails.of(src).size(tis.getWidth(), tis.getHeight()).toFile(dest);

            logger.debug("Create Thumbnail image : {}", dest.getAbsolutePath());
        } catch (Exception e) {
            logger.error("Fail to create thumbnail image 원본 : " + src.getAbsolutePath(), e);
            logger.error("Fail to create thumbnail image 썸네일 : " + dest.getAbsolutePath(), e);
            succYn = Boolean.FALSE;
        }

        return succYn;
    }

    /**
     * 워터마크를 포함하여 썸네일(또는 크기)을 생성
     * 
     * @param src 원본파일 경로문자열
     * @param dest 대상파일 경로문자열
     * @param width 대상 가로 크기
     * @param height 대상 세로 크기
     * @param waterMark 워터마크파일 경로문자열
     * @param position 워터마크 표시 위치 TOP/CENTER/BOTTOM + "_" LEFT/CENTER/RIGHT 조합 단 CENTER_CENTER는
     *        CENTER 만 사용
     *        예 : TOP_LEFT, BOTTOM_RIGHT, CENTER, CENTER_RIGHT
     * @param transparent 1f 를 100%로 계산하며 0.5f 면 50%의 투명도로 생성 됨
     * @return
     */
    public static Boolean warterMark(String src, String dest, int width, int height, String waterMark, String position, float transparent) {
        File sFile = new File(src);
        File dFile = new File(dest);
        File wFile = new File(waterMark);

        return warterMark(sFile, dFile, width, height, wFile, position, transparent);
    }

    /**
     * 워터마크를 포함하여 썸네일(또는 크기)을 생성
     * 
     * @param src 원본파일 객체
     * @param dest 대상파일 객체
     * @param width 대상 가로 크기
     * @param height 대상 세로 크기
     * @param waterMark 워터마크파일 객체
     * @param position 워터마크 표시 위치 TOP/CENTER/BOTTOM + "_" LEFT/CENTER/RIGHT 조합 단 CENTER_CENTER는
     *        CENTER 만 사용
     *        예 : TOP_LEFT, BOTTOM_RIGHT, CENTER, CENTER_RIGHT
     * @param transparent 1f 를 100%로 계산하며 0.5f 면 50%의 투명도로 생성 됨
     * @return
     */
    public static Boolean warterMark(File src, File dest, int width, int height, File waterMark, String position, float transparent) {

        BufferedImage srcImg = null;
        BufferedImage waterMarkImg = null;

        if(!src.canRead() || !waterMark.canRead()) {
            logger.error("원본 또는 워터마크 파일을 읽을 수 없습니다. 원본 : {}, 워터마크 {} ", src.getAbsolutePath(), waterMark.getAbsolutePath());

            return Boolean.FALSE;
        }

        Boolean succYn = Boolean.TRUE;

        try {
            String ext = FileUtil.extension(src.getName()).toLowerCase();
            if(ext.equals("jpg") || ext.equals("jpeg")) {
                srcImg = ImageReadUtil.readJpegImage(src);
            } else {
                srcImg = Imaging.getBufferedImage(waterMark);
            }
            waterMarkImg = Imaging.getBufferedImage(waterMark);

            Positions pos = Positions.valueOf(position);
            TargetImageSize tis = getTargetImageSize(srcImg, width, height);

            Thumbnails.of(srcImg).size(tis.getWidth(), tis.getHeight()).watermark(pos, waterMarkImg, transparent).toFile(dest);

            logger.debug("Create WarterMark Thumbnail image : {}", dest.getAbsolutePath());
        } catch (Exception e) {
            logger.error("Fail to create thumbnail image " + dest.getAbsolutePath(), e);
            succYn = Boolean.FALSE;
        }

        return succYn;
    }

    /**
     * 원본 파일과 입력한 가로/세로 크리로 최종 대상 이미지의 크기를 구한다.
     * 
     * @param srcImg 원본이미지
     * @param width 대상 가로 크기
     * @param height 대상 세로 크기
     * @return
     */
    private static TargetImageSize getTargetImageSize(Image srcImg, int width, int height) {

        int srcWidth = srcImg.getWidth(null);
        int srcHeight = srcImg.getHeight(null);

        int destWidth = -1;
        int destHeight = -1;

        /*
         * 인자가 가로 세로가 모두 0 인경우 원본 크기 유지(설정오류)
         */
        if(width == ZERO && height == ZERO) {
            destWidth = srcWidth;
            destHeight = srcHeight;
        } else {
            /*
             * 인자가 -1 인경우 원본 크기를 유지한다.
             * 0 인 경우 상대 측 크기 축소 비율에 따라서 비율로 축소한다.
             * 0 보다 큰 경우 지정된 크기를 사용
             */
            if(width == MINUS_ONE) {
                destWidth = srcWidth;
            } else if(width == ZERO) {
                double ratio = ((double) height) / ((double) srcHeight);
                destWidth = (int) ((double) srcWidth * ratio);
            } else if(width > 0) {
                destWidth = width;
            }

            if(height == MINUS_ONE) {
                destHeight = srcHeight;
            } else if(height == ZERO) {
                double ratio = ((double) width) / ((double) srcWidth);
                destHeight = (int) ((double) srcHeight * ratio);
            } else if(height > 0) {
                destHeight = height;
            }
        }

        /*
         * 최종 결과 원본 크기가 width, height 보다 작은 경우는 원본 크기로 지정
         */
        if(srcWidth < destWidth) {
            destWidth = srcWidth;
        }
        if(srcHeight < destHeight) {
            destHeight = srcHeight;
        }

        return new TargetImageSize(destWidth, destHeight);
    }
}
