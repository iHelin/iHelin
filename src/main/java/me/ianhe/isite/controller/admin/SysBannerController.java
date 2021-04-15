package me.ianhe.isite.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.ianhe.isite.entity.BannerEntity;
import me.ianhe.isite.service.BannerService;
import me.ianhe.isite.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author iHelin ihelin@outlook.com
 * @since 2021-03-16 09:42:06
 */
@RestController
@RequestMapping("sys/banner")
public class SysBannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('mall:banner:list')")
    public R list(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long limit, String key) {
        QueryWrapper<BannerEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(key)) {
            queryWrapper.eq("id", key);
        }
        queryWrapper.orderByAsc("banner_sort");
        IPage<BannerEntity> data = bannerService.page(new Page<>(page, limit), queryWrapper);
        return R.ok(data);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('mall:banner:info')")
    public R info(@PathVariable("id") Integer id) {
        BannerEntity banner = bannerService.getById(id);
        return R.ok().put("data", banner);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('mall:banner:save')")
    public R save(@RequestBody BannerEntity banner) {
        bannerService.save(banner);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('mall:banner:update')")
    public R update(@RequestBody BannerEntity banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('mall:banner:delete')")
    public R delete(@RequestBody List<Integer> ids) {
        bannerService.removeByIds(ids);
        return R.ok();
    }

}
