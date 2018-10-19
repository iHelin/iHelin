package me.ianhe.isite.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * @author iHelin
 * @since 2017/6/23 09:56
 */
@Service
public class JmsProducerService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final JmsMessagingTemplate jmsMessagingTemplate;

    public JmsProducerService(JmsMessagingTemplate jmsMessagingTemplate) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    /**
     * 发送消息
     *
     * @author iHelin
     * @since 2017/8/31 11:18
     */
    public void sendMessage(Destination destination, final Object message) {
        logger.debug("-----生产者发送消息:{}", message);
        jmsMessagingTemplate.convertAndSend(destination, message);
    }

}
