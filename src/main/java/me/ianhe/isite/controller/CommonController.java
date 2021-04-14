package me.ianhe.isite.controller;

import me.ianhe.isite.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iHelin
 * @since 2017/11/10 17:05
 */
@RestController
public class CommonController extends BaseController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/logout")
    public R logout() {
        return R.ok("注销成功！");
    }

}
