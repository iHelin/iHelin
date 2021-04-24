package me.ianhe.pp.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author iHelin
 * @since 2018/5/12 13:21
 */
@SpringBootTest
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
        MutablePair<String, File> pair = new MutablePair<>();
        pair.setLeft("arithmetic.sh");
        pair.setRight(new File("/Users/iHelin/Documents/dev/shell/arithmetic"));
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