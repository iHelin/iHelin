package me.ianhe.isite.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.ianhe.isite.entity.Article;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author iHelin
 * @since 2017/8/18 09:10
 */
@RestController
@RequestMapping("/articles")
public class ArticleController extends BaseController {

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
     * 获取文章列表
     *
     * @param pageNum
     * @param pageLength
     * @return
     */
    @GetMapping
    public IPage<Article> getArticles(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNum,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageLength) {
        return articleService.findByPage(null, pageNum, pageLength);
    }

}
