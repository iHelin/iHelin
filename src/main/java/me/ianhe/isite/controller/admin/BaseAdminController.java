package me.ianhe.isite.controller.admin;

import me.ianhe.isite.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * BaseAdminController
 *
 * @author iHelin
 * @since 2017/10/17 15:27
 */
@RequestMapping("/admin")
public abstract class BaseAdminController extends BaseController {

    private static final String NAMESPACE = "admin";

}
