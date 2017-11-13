package me.ianhe.service;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import me.ianhe.model.MatrixToImageWriter;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/11/13 21:45
 */
@Service
public class QrcodeService {
//    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean generate(String content, int size, String format, OutputStream outputStream) throws IOException, WriterException {
        return generate(content, size, size, format, outputStream);
    }

    /**
     * 二维码生成
     *
     * @param content      二维码内容
     * @param width        二维码宽度(像素)
     * @param height       二维码高度(像素)
     * @param format       二维码格式(jpg、png等)
     * @param outputStream 输出流
     * @throws WriterException
     * @throws IOException
     * @author iHelin
     */
    private boolean generate(String content, int width, int height, String format, OutputStream outputStream)
            throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = Maps.newHashMap();
        hints.put(EncodeHintType.CHARACTER_SET, CharEncoding.UTF_8);
        hints.put(EncodeHintType.MARGIN, 1);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE,
                width, height, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return ImageIO.write(image, format, outputStream);
    }

}
