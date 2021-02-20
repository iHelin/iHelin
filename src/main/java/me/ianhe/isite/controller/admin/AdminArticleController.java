package me.ianhe.isite.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.ianhe.isite.entity.Article;
import me.ianhe.isite.model.R;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 文章管理
 *
 * @author iHelin
 * @since 2017/8/28 13:26
 */
@RestController
@RequestMapping("/admin/articles")
public class AdminArticleController extends BaseAdminController {

    /**
     * 新增文章
     *
     * @author iHelin
     * @since 2017/8/28 13:26
     */
    @PostMapping
    public Map<String, Object> addArticle(Article article) {
        articleService.addArticle(article);
        return R.ok();
    }

    /**
     * 获取文章列表
     *
     * @author iHelin
     * @since 2017/12/21 10:02
     */
    @GetMapping
    public IPage<Article> getArticles(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNum,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
        return articleService.findByPage(null, pageNum, pageSize);
    }

    /**
     * 按id获取文章
     *
     * @param id 文章id
     * @return
     */
    @GetMapping("/{id:\\d+}")
    public Article getArticle(@PathVariable Integer id) {
        Assert.notNull(id, "Article id can not be null.");
        return articleService.selectArticleById(id);
    }

    /**
     * 编辑文章
     *
     * @author iHelin
     * @since 2017/8/28 13:28
     */
    @PutMapping(value = "/{id:\\d+}")
    public Map<String, Object> editArticle(@PathVariable Integer id, Article article) {
        if (article == null || id == null) {
            return R.error("文章不存在");
        }
        Article newArticle = articleService.selectArticleById(id);
        newArticle.setTitle(HtmlUtils.htmlEscape(article.getTitle(), StandardCharsets.UTF_8.name()));
        newArticle.setSummary(HtmlUtils.htmlEscape(article.getSummary(), StandardCharsets.UTF_8.name()));
        newArticle.setContent(article.getContent());
        articleService.editArticle(newArticle);
        return R.ok();
    }

    /**
     * 删除文章
     *
     * @author iHelin
     * @since 2017/8/28 13:29
     */
    @DeleteMapping("/{id:\\d+}")
    public Map<String, Object> deleteProduct(@PathVariable Integer id) {
        articleService.deleteById(id);
        return R.ok();
    }

}
