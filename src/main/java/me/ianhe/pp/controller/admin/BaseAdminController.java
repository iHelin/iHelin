package me.ianhe.pp.controller.admin;

import me.ianhe.pp.controller.BaseController;
import me.ianhe.pp.entity.SysUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * BaseAdminController
 *
 * @author iHelin
 * @since 2017/10/17 15:27
 */
@RequestMapping("/admin")
public abstract class BaseAdminController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
