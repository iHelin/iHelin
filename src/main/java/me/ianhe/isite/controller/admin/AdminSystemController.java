package me.ianhe.isite.controller.admin;

import me.ianhe.isite.model.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Properties;

/**
 * @author iHelin
 * @since 2017/11/14 15:50
 */
@RestController
@RequestMapping("/admin/commons")
public class AdminSystemController extends BaseAdminController {

    /**
     * 系统属性
     *
     * @author iHelin
     * @since 2017/11/14 15:52
     */
    @GetMapping("props")
    public Map<String, Object> props() {
        Properties props = System.getProperties();
        return R.ok().putData(props);
    }

}
