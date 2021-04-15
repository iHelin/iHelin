package me.ianhe.isite.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.ianhe.isite.entity.ArticleEntity;
import me.ianhe.isite.service.ArticleService;
import me.ianhe.isite.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author iHelin ihelin@outlook.com
 * @since 2021-03-29 17:10:58
 */
@RestController
@RequestMapping("sys/notice")
public class SysNoticeController {

    @Autowired
    private ArticleService articleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('mall:notice:list')")
    public R list(@RequestParam(defaultValue = "1") Long page,
                  @RequestParam(defaultValue = "10") Long limit,
                  String key) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(key), "id", key);
        queryWrapper.eq("type", "notice");
        IPage<ArticleEntity> data = articleService.page(new Page<>(page, limit), queryWrapper);
        return R.ok(data);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('mall:notice:info')")
    public R info(@PathVariable("id") Integer id) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", "notice");
        ArticleEntity notice = articleService.getOne(queryWrapper);
        return R.ok().put("data", notice);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('mall:notice:save')")
    public R save(@RequestBody ArticleEntity noticeEntity) {
        noticeEntity.setType("notice");
        articleService.save(noticeEntity);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('mall:notice:update')")
    public R update(@RequestBody ArticleEntity noticeEntity) {
        noticeEntity.setType("notice");
        articleService.updateById(noticeEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('mall:notice:delete')")
    public R delete(@RequestBody List<Integer> ids) {
        articleService.removeByIds(ids);
        return R.ok();
    }

}
