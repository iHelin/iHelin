package me.ianhe.isite.controller;

import me.ianhe.isite.entity.Article;
import me.ianhe.isite.model.R;
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

    @GetMapping("/eat")
    public Map<String, Object> eat() {
        taskService.runEveryDay11();
        return R.ok();
    }

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
