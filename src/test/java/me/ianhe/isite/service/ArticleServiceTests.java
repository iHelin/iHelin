package me.ianhe.isite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ianhe.isite.dao.ArticleMapper;
import me.ianhe.isite.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author iHelin
 * @since 2018/5/28 19:34
 */
@SpringBootTest
class ArticleServiceTests {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findByPage() throws JsonProcessingException {
        IPage<Article> articles = articleService.findByPage(null, 1, 10);
        System.out.println(objectMapper.writeValueAsString(articles));
    }

    @Test
    void testInsert() {
        Article article = new Article();
        article.setAuthor("iHelin");
        article.setTitle("title5");
        article.setCreateTime(new Date());
        boolean save = articleService.save(article);
        System.out.println(save);
    }

    @Test
    void testInsert2() {
        Article article = new Article();
        article.setAuthor("iHelin");
        article.setTitle("title3");
        article.setCreateTime(new Date());
        articleMapper.insert(article);
    }


}