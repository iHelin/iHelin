package me.ianhe.isite.controller;

import me.ianhe.isite.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import java.util.List;

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

    @GetMapping("/{id:\\d+}")
    public Article getArticle(@PathVariable Integer id) {
        Article article = articleService.selectArticleById(id);
        if (article != null) {
            producerService.sendMessage(destination, id);
        }
        Long readCount = commonRedisDao.getLong(READ_COUNT_KEY + id);
        return article;
    }

    @GetMapping
    public List<Article> getArticles(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageLength) {
        return articleService.listByCondition(null, pageNum, pageLength);
    }

}
