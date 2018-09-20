package me.ianhe.isite.controller;

import me.ianhe.isite.entity.Article;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
        return success();
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

}
