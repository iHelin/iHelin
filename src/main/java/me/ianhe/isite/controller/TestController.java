package me.ianhe.isite.controller;

import com.google.common.collect.Maps;
import me.ianhe.isite.aspect.CheckLogin;
import me.ianhe.isite.entity.Article;
import me.ianhe.isite.model.R;
import me.ianhe.isite.utils.JwtUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.ZoneId;
import java.util.*;

/**
 * @author iHelin
 * @since 2017/12/20 15:12
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @PostMapping("/validate")
    public Object validate(@Valid Article article, Errors errors) {
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                return fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return "ok";
    }

    @GetMapping("/gen-token")
    public R genToken() {
        Map<String, Object> claims = Maps.newHashMap();
        claims.put("id", 10);
        claims.put("nickname", "seven");
        claims.put("avatarUrl", "https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoJDxnFicPEe49VEklCjAKxHIyibFnhJzLNP3pWBOowqtnibJqE01TV25Qz9cbdY19T4PMg4brD4BMqg/132");
        return R.ok().putData(JwtUtil.createJWT(claims, "seven"));
    }

    @CheckLogin
    @GetMapping("/ttt")
    public String test(@RequestHeader("User-Agent") String userAgent, HttpServletRequest request) {
        logger.debug(userAgent);
        return "hhhh";
    }

    @GetMapping("/test2")
    public Map<String, Object> test2(String[] age,
                                     @RequestParam Map<String, Object> params) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("age", age);
        maps.put("params", params);
        return maps;
    }

    @GetMapping("/test3")
    public Map<String, Object> test3(Locale locale,
                                     TimeZone timeZone,
                                     ZoneId zoneId) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("locale", locale);
        maps.put("timeZone", timeZone);
        maps.put("zoneId", zoneId);
        return maps;
    }

    @GetMapping("/test4")
    public Map<String, Object> test4(Article article) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("article", article);
        return maps;
    }

}
