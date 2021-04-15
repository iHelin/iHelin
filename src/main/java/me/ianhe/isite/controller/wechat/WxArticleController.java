package me.ianhe.isite.controller.wechat;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.ianhe.isite.entity.ArticleEntity;
import me.ianhe.isite.service.ArticleService;
import me.ianhe.isite.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wechat/article")
public class WxArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(String type) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title");
        queryWrapper.eq("enabled", true);
        queryWrapper.eq("type", type);
        List<ArticleEntity> data = articleService.list(queryWrapper);
        return R.ok(data);
    }

    /**
     * 信息
     */
    @GetMapping("/info")
    public R info(Integer id, String type) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq("enabled", true);
        queryWrapper.eq(StringUtils.hasText(type), "type", type);
        ArticleEntity notice = articleService.getOne(queryWrapper);
        if (notice != null) {
            return R.ok(notice);
        } else {
            return R.error("文章不存在");
        }
    }
}
