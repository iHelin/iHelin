package me.ianhe.isite.controller;

import me.ianhe.isite.entity.Poem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iHelin
 * @since 2017/11/10 17:05
 */
@RestController
public class CommonController extends BaseController {

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
//    @GetMapping("kaptcha")
//    public void createKaptcha(HttpServletResponse response, HttpSession session) throws IOException {
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//        response.setContentType("image/jpeg");
//        String code = defaultKaptcha.createText();
//        logger.debug("图形验证码为：{}", code);
//        BufferedImage image = defaultKaptcha.createImage(code);
//        CaptchaCode captchaCode = new CaptchaCode(image, code, 300L);
//        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, captchaCode);
//        ImageIO.write(image, "JPEG", response.getOutputStream());
//    }


}
