package me.ianhe.isite.controller;

import me.ianhe.isite.utils.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author iHelin
 * @since 2017/11/14 16:47
 */
@Controller
public class ViewController extends BaseController {

    @GetMapping("ws")
    public String webSocketPage(HttpServletRequest request, Model model) {
        model.addAttribute("serverName", RequestUtil.getDomain(request));
        return "webSocketService";
    }

}
