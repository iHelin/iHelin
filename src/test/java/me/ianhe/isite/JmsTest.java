package me.ianhe.isite;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author iHelin
 * @since 2018/5/9 11:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JmsTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void test() {
        Assert.assertNotNull(jmsTemplate);
    }

}
