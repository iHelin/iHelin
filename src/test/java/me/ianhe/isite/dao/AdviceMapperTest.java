package me.ianhe.isite.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author iHelin
 * @since 2018-12-22 15:36
 */
@SpringBootTest
public class AdviceMapperTest {

    @Autowired
    private AdviceMapper adviceMapper;

    public void setUp() throws Exception {
        System.out.println("setup");
    }

    @Test
    public void test() {
    }
}