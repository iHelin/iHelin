package me.ianhe.isite.listener;

import me.ianhe.isite.model.MailModel;
import me.ianhe.isite.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * 邮件发送
 *
 * @author iHelin
 * @since 2017/6/23 09:51
 */
@Component
public class MailMessageListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmailService emailService;

    @JmsListener(destination = "email")
    public void onMessage(ObjectMessage message) {
        logger.debug("消费者接收到一条邮件消息。");
        try {
            MailModel mail = (MailModel) message.getObject();
            emailService.sendSimpleMail(mail.getToAddress(), mail.getSubject(), mail.getContent());
        } catch (JMSException e) {
            logger.error("消息接收异常！", e);
        }
    }
}
