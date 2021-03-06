package me.ianhe.pp.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.ianhe.pp.entity.ArticleEntity;
import me.ianhe.pp.service.ArticleService;
import me.ianhe.pp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author iHelin ihelin@outlook.com
 * @since 2021-03-29 17:10:58
 */
@RestController
@RequestMapping("sys/article")
public class SysArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(defaultValue = "1") Long page,
                  @RequestParam(defaultValue = "10") Long limit,
                  String type) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(type), "type", type);
        IPage<ArticleEntity> data = articleService.page(new Page<>(page, limit), queryWrapper);
        return R.ok(data);
    }


    /**
     * 信息
     */
    @GetMapping("/info")
    public R info(Integer id, String type) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq("type", type);
        ArticleEntity articleEntity = articleService.getOne(queryWrapper);
        return R.ok().put("data", articleEntity);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    public R save(@RequestBody ArticleEntity articleEntity) {
        articleService.save(articleEntity);
        return R.ok();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody ArticleEntity articleEntity) {
        articleService.saveOrUpdate(articleEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody List<Integer> ids) {
        articleService.removeByIds(ids);
        return R.ok();
    }

}
