package me.ianhe.isite.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author iHelin
 * @since 2017/8/31 21:48
 */
@Controller
public class AdminPageController extends BaseAdminController {

    @Value("${qiniu.prefix}")
    private String prefix;

    /**
     * 首页
     *
     * @author iHelin
     * @since 2017/11/14 16:44
     */
    @GetMapping(value = {"", "index"})
    public String index() {
        return ftl("index");
    }

    /**
     * 后台登录页
     *
     * @author iHelin
     * @since 2017/8/31 22:24
     */
    @GetMapping("login")
    public String loginPage() {
        return ftl("login");
    }

    /**
     * 二维码页
     *
     * @author iHelin
     * @since 2017/8/31 22:24
     */
    @GetMapping("qrcode")
    public String qrcode() {
        return ftl("qrcode");
    }

    /**
     * 用户页
     *
     * @author iHelin
     * @since 2017/11/14 15:54
     */
    @GetMapping("user")
    public String user() {
        return ftl("user");
    }

    /**
     * 文章页
     *
     * @author iHelin
     * @since 2017/11/14 15:54
     */
    @GetMapping("article")
    public String article() {
        return ftl("article");
    }

    /**
     * 文件管理页
     *
     * @author iHelin
     * @since 2017/11/14 15:54
     */
    @GetMapping("file")
    public String file(Model model) {
        model.addAttribute("prefix", prefix);
        return ftl("file");
    }

    /**
     * 系统属性页
     *
     * @author iHelin
     * @since 2017/11/14 15:22
     */
    @GetMapping("property")
    public String property() {
        return ftl("property");
    }

    /**
     * 请求路径页
     *
     * @author iHelin
     * @since 2017/11/14 15:22
     */
    @GetMapping("mapping")
    public String mapping() {
        return ftl("mapping");
    }

}
