/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils.image;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import org.apache.commons.imaging.formats.jpeg.JpegImageParser;
import org.apache.commons.imaging.formats.jpeg.segments.Segment;
import org.apache.commons.imaging.formats.jpeg.segments.UnknownSegment;

/**
 * 실사판 이미지 등의 경우 별도로 처리해야 하는 문제로 인한 내부 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 8. 8.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ImageReadUtil {

    public static final int COLOR_TYPE_RGB = 1;
    public static final int COLOR_TYPE_CMYK = 2;
    public static final int COLOR_TYPE_YCCK = 3;

    private static int colorType = COLOR_TYPE_RGB;
    private static boolean hasAdobeMarker = false;

    public static BufferedImage readJpegImage(File file) throws IOException, ImageReadException {
        colorType = COLOR_TYPE_RGB;
        hasAdobeMarker = false;

        ImageInputStream stream = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(stream);

        while(iter.hasNext()) {
            ImageReader reader = iter.next();
            reader.setInput(stream);

            BufferedImage image;
            ICC_Profile profile = null;

            try {
                image = reader.read(0);
            } catch (IIOException e) {
                colorType = COLOR_TYPE_CMYK;

                checkAdobeMarker(file);

                profile = Imaging.getICCProfile(file);
                WritableRaster raster = (WritableRaster) reader.readRaster(0, null);

                if(colorType == COLOR_TYPE_YCCK) {
                    convertYcckToCmyk(raster);
                }
                if(hasAdobeMarker) {
                    convertInvertedColors(raster);
                }
                image = convertCmykToRgb(raster, profile);
            } finally {
                if (reader != null) reader.dispose();
                if (stream != null) stream.close();
            }

            return image;
        }

        return null;
    }

    public static void checkAdobeMarker(File file) throws IOException, ImageReadException {
        JpegImageParser parser = new JpegImageParser();
        ByteSource byteSource = new ByteSourceFile(file);

        List<Segment> segments = parser.readSegments(byteSource, new int[] { 0xffee }, true);

        if(segments != null && segments.size() >= 1) {
            UnknownSegment app14Segment = (UnknownSegment) segments.get(0);
            byte[] data = app14Segment.getSegmentData();

            if(data.length >= 12 && data[0] == 'A' && data[1] == 'd' && data[2] == 'o' && data[3] == 'b' && data[4] == 'e') {
                hasAdobeMarker = true;
                int transform = app14Segment.getSegmentData()[11] & 0xff;
                if(transform == 2)
                    colorType = COLOR_TYPE_YCCK;
            }
        }
    }

    public static void convertYcckToCmyk(WritableRaster raster) {
        int height = raster.getHeight();
        int width = raster.getWidth();
        int stride = width * 4;
        int[] pixelRow = new int[stride];

        for(int h = 0 ; h < height ; h++) {
            raster.getPixels(0, h, width, 1, pixelRow);

            for(int x = 0 ; x < stride ; x += 4) {
                int y = pixelRow[x];
                int cb = pixelRow[x + 1];
                int cr = pixelRow[x + 2];

                int c = (int) (y + 1.402 * cr - 178.956);
                int m = (int) (y - 0.34414 * cb - 0.71414 * cr + 135.95984);
                y = (int) (y + 1.772 * cb - 226.316);

                if(c < 0) {
                    c = 0;
                } else if(c > 255) {
                    c = 255;
                }

                if(m < 0) {
                    m = 0;
                } else if(m > 255) {
                    m = 255;
                }

                if(y < 0) {
                    y = 0;
                } else if(y > 255) {
                    y = 255;
                }

                pixelRow[x] = 255 - c;
                pixelRow[x + 1] = 255 - m;
                pixelRow[x + 2] = 255 - y;
            }

            raster.setPixels(0, h, width, 1, pixelRow);
        }
    }

    public static void convertInvertedColors(WritableRaster raster) {
        int height = raster.getHeight();
        int width = raster.getWidth();
        int stride = width * 4;
        int[] pixelRow = new int[stride];

        for(int h = 0 ; h < height ; h++) {
            raster.getPixels(0, h, width, 1, pixelRow);

            for(int x = 0 ; x < stride ; x++) {
                pixelRow[x] = 255 - pixelRow[x];
            }

            raster.setPixels(0, h, width, 1, pixelRow);
        }
    }

    public static BufferedImage convertCmykToRgb(Raster cmykRaster, ICC_Profile cmykProfile) throws IOException {
        if(cmykProfile == null) {
            cmykProfile = ICC_Profile.getInstance(ThumbnailUtil.class.getResourceAsStream("CGATS21_CRPC6.icc"));
        }

        ICC_ColorSpace cmykCS = new ICC_ColorSpace(cmykProfile);
        BufferedImage rgbImage = new BufferedImage(cmykRaster.getWidth(), cmykRaster.getHeight(), BufferedImage.TYPE_INT_RGB);
        WritableRaster rgbRaster = rgbImage.getRaster();

        ColorSpace rgbCS = rgbImage.getColorModel().getColorSpace();
        ColorConvertOp cmykToRgb = new ColorConvertOp(cmykCS, rgbCS, null);
        cmykToRgb.filter(cmykRaster, rgbRaster);

        return rgbImage;
    }
}
