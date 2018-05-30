package me.ianhe.isite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ianhe.isite.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IsiteApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAdd() throws JsonProcessingException {
        Article article = new Article();
        article.setTitle("title");
        article.setReadNum(1L);
        article.setAuthor("Ian");
        article.setContent("葫芦娃");
        redisTemplate.opsForValue().set("d", "d");
        redisTemplate.opsForValue().set("e", objectMapper.writeValueAsString(article));
        redisTemplate.opsForValue().set("f", "fffff", 20, TimeUnit.SECONDS);
    }

}