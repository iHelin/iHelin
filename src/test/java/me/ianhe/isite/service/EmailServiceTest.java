package me.ianhe.isite.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author iHelin
 * @since 2018/5/12 13:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendSimpleMail() {
        emailService.sendSimpleMail("ahaqhelin@163.com", "普通邮件测试", "hello");
    }

    @Test
    public void sendAttachmentsMail() {
        List<Pair<String, File>> pairList = Lists.newArrayList();
        MutablePair<String, File> pair = new MutablePair();
        pair.setLeft("login.vue");
        pair.setRight(new File("/Users/iHelin/Downloads/login.vue"));
        pairList.add(pair);
        emailService.sendAttachmentsMail("ahaqhelin@163.com", "带附件的邮件测试", "hello", pairList);
    }

    @Test
    public void sendTemplateMail() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("content", "content");
        map.put("param", "嘿嘿");
        emailService.sendTemplateMail("ahaqhelin@163.com", "模板邮件测试", "mail_test.ftl", map);
    }
}