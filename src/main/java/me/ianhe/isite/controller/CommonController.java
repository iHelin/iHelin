package me.ianhe.isite.controller;

import me.ianhe.isite.entity.User;
import me.ianhe.isite.model.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author iHelin
 * @since 2017/11/10 17:05
 */
@RestController
public class CommonController extends BaseController {
    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @PostMapping("/login")
    public R login(@RequestBody Map<String, Object> body) {
        String username = (String) body.get("username");
        String password = (String) body.get("password");
        User user = userService.loadUserByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return R.error("用户名或密码不正确");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String token = jwtComponent.createJWT(user.getId().toString());
        return R.ok().putData(token);
    }

    @GetMapping("/me")
    public R loginInfo(UsernamePasswordAuthenticationToken token) {
//        User user = SystemUtils.getCurrentUser();
        return R.ok(token.getPrincipal());
    }

    @PostMapping("/logout")
    public R logout() {
        return R.ok("注销成功！");
    }

}
