package me.ianhe.isite.controller;

import cn.hutool.core.img.ImgUtil;
import com.google.common.collect.Maps;
import me.ianhe.isite.entity.Article;
import me.ianhe.isite.utils.R;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.OutputStream;
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
    public R validate(@Valid Article article, Errors errors) {
        if (errors.hasErrors()) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                return R.error(fieldError.getDefaultMessage());
            }
        }
        return R.ok();
    }

    @GetMapping("/gen-token")
    public R genToken() {
        String id = "1";
        return R.ok().putData(jwtComponent.createJWT(id));
    }

    @GetMapping("/ttt")
    public R test(@RequestHeader("User-Agent") String userAgent) {
        logger.debug(userAgent);
        return R.ok(userAgent);
    }

    @GetMapping("/test2")
    public R test2(String[] age,
                   @RequestParam Map<String, Object> params) {
        Map<String, Object> maps = Maps.newHashMap();
        maps.put("age", age);
        maps.put("params", params);
        return R.ok(maps);
    }

    @GetMapping("/test3")
    public R test3(Locale locale,
                   TimeZone timeZone,
                   ZoneId zoneId) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("locale", locale);
        maps.put("timeZone", timeZone);
        maps.put("zoneId", zoneId);
        return R.ok(maps);
    }

    @GetMapping("/test4")
    public R test4(Article article) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("article", article);
        return R.ok(maps);
    }

    /**
     * 生成二维码
     *
     * @author iHelin
     * @since 2017/8/31 22:24
     */
    @GetMapping(value = "generate", produces = {"image/png"})
    public void generateQRCode(String content,
                               @RequestParam(defaultValue = "300") Integer width,
                               @RequestParam(defaultValue = "300") Integer height,
                               OutputStream outputStream) {
        qrcodeService.generate(content, width, height, ImgUtil.IMAGE_TYPE_PNG, outputStream);
        logger.info("Success generate qrcode： {}", content);
    }

}
