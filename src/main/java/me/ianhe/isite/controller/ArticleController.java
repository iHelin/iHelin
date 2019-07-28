package me.ianhe.isite.controller;

import com.github.pagehelper.PageInfo;
import me.ianhe.isite.entity.Article;
import me.ianhe.isite.utils.Constant;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author iHelin
 * @since 2017/8/18 09:10
 */
@RestController
@RequestMapping("/articles")
public class ArticleController extends BaseController {

//    @Autowired
//    @Qualifier("article")
//    private Destination destination;

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
            Long readCount = commonRedisDao.getLong(Constant.READ_COUNT_KEY + id);
//            producerService.sendMessage(destination, id);
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
    public PageInfo<Article> getArticles(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNum,
                                         @RequestParam(defaultValue = DEFAULT_PAGE_LENGTH) Integer pageLength) {
        return articleService.findByPage(null, pageNum, pageLength);
    }

}
