package me.ianhe.isite;

import me.ianhe.isite.dao.ArticleMapper;
import me.ianhe.isite.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author iHelin
 * @since 2018/5/9 11:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void test() {
        Article article = articleMapper.selectByPrimaryKey(11);
        redisTemplate.opsForValue().set("aaa", article);
    }

}
