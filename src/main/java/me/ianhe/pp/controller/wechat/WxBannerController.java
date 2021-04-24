package me.ianhe.pp.controller.wechat;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.ianhe.pp.entity.BannerEntity;
import me.ianhe.pp.service.BannerService;
import me.ianhe.pp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author iHelin ihelin@outlook.com
 * @since 2021-03-16 09:42:06
 */
@RestController
@RequestMapping("wechat/banner")
public class WxBannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/list")
    public R listBanner(@RequestParam(defaultValue = "index") String type) {
        QueryWrapper<BannerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        queryWrapper.eq("enabled", true);
        queryWrapper.orderByAsc("banner_sort");
        return R.ok(bannerService.list(queryWrapper));
    }
}
