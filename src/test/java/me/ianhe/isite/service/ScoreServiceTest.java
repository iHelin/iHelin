package me.ianhe.isite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ianhe.isite.entity.Score;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author iHelin
 * @since 2018/10/11 10:53
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ScoreServiceTest {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void selectByCondition() throws JsonProcessingException {
        List<Score> scores = scoreService.selectByCondition(1, 10);
        System.out.println(objectMapper.writeValueAsString(scores));
    }
}