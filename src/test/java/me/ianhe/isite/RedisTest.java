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
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void test() {
        Article article1 = articleMapper.selectByPrimaryKey(11);
        Article article2 = articleMapper.selectByPrimaryKey(11);
        System.out.println(article1);
        System.out.println(article2);
        redisTemplate.opsForValue().set("aaa", article1);
    }

}
