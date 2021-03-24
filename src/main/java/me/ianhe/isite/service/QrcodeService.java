package me.ianhe.isite.service;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.OutputStream;

/**
 * @author iHelin
 * @since 2017/11/13 21:45
 */
@Service
public class QrcodeService {

    /**
     * 二维码生成
     *
     * @param content      二维码内容
     * @param width        二维码宽度(像素)
     * @param height       二维码高度(像素)
     * @param format       二维码格式(jpg、png等)
     * @param outputStream 输出流
     * @author iHelin
     */
    public void generate(String content, int width, int height, String format, OutputStream outputStream) {
        QrConfig config = new QrConfig(width, height);
        config.setMargin(2);
        config.setForeColor(Color.CYAN);
        config.setBackColor(Color.GRAY);
        QrCodeUtil.generate(content, config, format, outputStream);
    }

}
