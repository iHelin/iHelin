package me.ianhe.isite.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author iHelin
 * @since 2018/7/25 21:39
 */
@SpringBootTest
public class DingServiceTest {

    @Autowired
    private DingService dingService;

    @Test
    public void sendTextMsg() {
        dingService.sendTextMsg("xxxxx");
    }

}