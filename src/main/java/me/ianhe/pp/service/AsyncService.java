package me.ianhe.pp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author iHelin
 * @since 2018/5/30 20:10
 */
@Service
public class AsyncService {

    @Autowired
    private EmailService emailService;

    @Async
    public void asyncSendEmail(String sendTo, String title, String content) {
        emailService.sendSimpleMail(sendTo, title, content);
    }
}
