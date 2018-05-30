package me.ianhe.isite.service.impl;

import me.ianhe.isite.service.AsyncService;
import me.ianhe.isite.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author iHelin
 * @since 2018/5/30 20:10
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private EmailService emailService;

    @Async
    @Override
    public void asyncSendEmail(String sendTo, String title, String content) {
        emailService.sendSimpleMail(sendTo, title, content);
    }
}
