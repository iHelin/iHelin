package me.ianhe.isite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import me.ianhe.isite.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author iHelin
 * @since 2018/5/28 19:34
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listByCondition() throws JsonProcessingException {
        List<Article> articles = articleService.listByCondition(null, 1, 10);
        System.out.println(objectMapper.writeValueAsString(articles));
    }

    @Test
    public void findByPage() throws JsonProcessingException {
        PageInfo<Article> articles = articleService.findByPage(null, 1, 10);
        System.out.println(objectMapper.writeValueAsString(articles));
    }
}