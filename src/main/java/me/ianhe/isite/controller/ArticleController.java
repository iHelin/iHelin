package me.ianhe.isite.controller;

import com.github.pagehelper.PageInfo;
import me.ianhe.isite.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;

/**
 * @author iHelin
 * @since 2017/8/18 09:10
 */
@RestController
@RequestMapping("/articles")
public class ArticleController extends BaseController {

    public static final String READ_COUNT_KEY = "article:readCount:";

    @Autowired
    @Qualifier("article")
    private Destination destination;

    /**
     * 按id获取文章
     *
     * @param id 文章id
     * @return
     */
    @GetMapping("/{id:\\d+}")
    public Article getArticle(@PathVariable Integer id) {
        Assert.notNull(id, "Article id can not be null.");
        Article article = articleService.selectArticleById(id);
        if (article != null) {
            Long readCount = commonRedisDao.getLong(READ_COUNT_KEY + id);
            producerService.sendMessage(destination, id);
            article.setReadNum(readCount);
        }
        return article;
    }

    /**
     * 获取文章列表
     *
     * @param pageNum
     * @param pageLength
     * @return
     */
    @GetMapping
    public PageInfo<Article> getArticles(@RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = DEFAULT_PAGE_LENGTH) Integer pageLength) {
        return articleService.findByPage(null, pageNum, pageLength);
    }

}
