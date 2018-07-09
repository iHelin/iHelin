package me.ianhe.isite.controller;

import me.ianhe.isite.dao.AdviceMapper;
import me.ianhe.isite.entity.Advice;
import me.ianhe.isite.entity.Poem;
import me.ianhe.isite.model.CaptchaCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/11/10 17:05
 */
@RestController
public class CommonController extends BaseController {

    @Autowired
    private AdviceMapper adviceMapper;

    public static final String CAPTCHA_CODE_KEY = "code";

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
    public void createKaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = defaultKaptcha.createText();
        logger.debug("图形验证码为：{}", code);
        BufferedImage image = defaultKaptcha.createImage(code);
        CaptchaCode captchaCode = new CaptchaCode(image, code, 300L);
        session.setAttribute(CAPTCHA_CODE_KEY, captchaCode);
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    /**
     * 获取建议
     *
     * @param advice
     * @return
     */
    @PostMapping("/advices")
    public Map<String, Object> addAdvice(Advice advice) {
        advice.setCreateTime(new Date());
        adviceMapper.insert(advice);
        asyncService.asyncSendEmail(advice.getEmail(), "感谢您的反馈", "反馈内容：" + advice.getMessage());
        return success();
    }

}
