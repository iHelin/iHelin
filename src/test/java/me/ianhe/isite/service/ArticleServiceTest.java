package me.ianhe.isite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ianhe.isite.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void findByPage() throws JsonProcessingException {
        IPage<Article> articles = articleService.findByPage(null, 1, 10);
        System.out.println(objectMapper.writeValueAsString(articles));
    }

}