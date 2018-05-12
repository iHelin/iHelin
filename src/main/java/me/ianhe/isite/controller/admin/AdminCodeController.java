package me.ianhe.isite.controller.admin;

import me.ianhe.isite.utils.RequestUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 二维码图片生成
 * Date           16/12/20
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@RestController
@RequestMapping("admin/qrcode")
public class AdminCodeController extends BaseAdminController {

    /**
     * 生成二维码
     *
     * @author iHelin
     * @since 2017/8/31 22:24
     */
    @GetMapping("generate")
    public void generateQRCode(String content, HttpServletRequest request, HttpServletResponse response) {
        String format = "png";
        logger.info("Success generate qrcode： {},ip is {}", content, RequestUtil.getRealIp(request));
        try {
            qrcodeService.generate(content, 300, 300, format, response.getOutputStream());
        } catch (Exception e) {
            logger.error("生成二维码失败", e);
        }
    }

}
