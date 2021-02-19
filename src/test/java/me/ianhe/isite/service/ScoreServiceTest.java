package me.ianhe.isite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ianhe.isite.entity.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author iHelin
 * @since 2018/10/11 10:53
 */
@SpringBootTest
public class ScoreServiceTest {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void selectByCondition() throws JsonProcessingException {
        IPage<Score> scores = scoreService.selectByCondition(1, 10);
        System.out.println(objectMapper.writeValueAsString(scores));
    }
}