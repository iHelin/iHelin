package me.ianhe.isite.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import me.ianhe.isite.entity.Poem;
import me.ianhe.isite.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author iHelin
 * @since 2017/11/10 17:05
 */
@RestController
public class CommonController extends BaseController {

    @Autowired
    private CommonService commonService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private HttpSession session;

    /**
     * 古诗接口
     *
     * @author iHelin
     * @since 2017/11/10 17:26
     */
    @GetMapping("poem")
    public Poem getPoemByRandom() {
        return commonService.getPoemByRandom();
    }

    /**
     * 创建验证码
     *
     * @author iHelin
     * @since 2017/10/20 16:38
     */
    @GetMapping("kaptcha")
    public void createKaptcha(HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = defaultKaptcha.createText();
        logger.info("图形验证码为：{}", code);
        BufferedImage image = defaultKaptcha.createImage(code);
        session.setAttribute("code", code);
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

}
