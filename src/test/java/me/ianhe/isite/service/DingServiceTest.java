package me.ianhe.isite.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author iHelin
 * @since 2018/7/25 21:39
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DingServiceTest {

    @Autowired
    private DingService dingService;

    @Test
    public void sendTextMsg() {
        dingService.sendTextMsg("xxxxx");
    }

    @Test
    public void sendFeedCardMsg() {
    }
}