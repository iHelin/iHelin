package me.ianhe.isite.service.impl;

import me.ianhe.isite.exception.SystemException;
import me.ianhe.isite.service.EmailService;
import me.ianhe.isite.service.TemplateService;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author iHelin
 * @since 2018/5/12 13:14
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateService templateService;
    @Value("${spring.mail.username}")
    private String emailFrom;

    @Override
    public void sendSimpleMail(String sendTo, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(sendTo);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
        logger.debug("success send mail.");
    }

    @Override
    public void sendAttachmentsMail(String sendTo, String title, String content, List<Pair<String, File>> attachments) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailFrom);
            helper.setTo(sendTo);
            helper.setSubject(title);
            helper.setText(content);
            for (Pair<String, File> pair : attachments) {
                helper.addAttachment(pair.getLeft(), new FileSystemResource(pair.getRight()));
            }
        } catch (Exception e) {
            throw new SystemException(e);
        }
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendTemplateMail(String sendTo, String title, String templateName, Map<String, Object> content, List<Pair<String, File>> attachments) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailFrom);
            helper.setTo(sendTo);
            helper.setSubject(title);
            String text = templateService.applyTemplate(templateName, content);
            helper.setText(text, true);
            if (attachments != null) {
                for (Pair<String, File> pair : attachments) {
                    helper.addAttachment(pair.getLeft(), new FileSystemResource(pair.getRight()));
                }
            }
        } catch (Exception e) {
            throw new SystemException(e);
        }
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendTemplateMail(String sendTo, String title, String templateName, Map<String, Object> content) {
        sendTemplateMail(sendTo, title, templateName, content, null);
    }
}
