package me.ianhe.isite.listener;

import me.ianhe.isite.dao.CommonRedisDao;
import me.ianhe.isite.utils.Constant;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

/**
 * @author iHelin
 * @since 2017/8/18 09:04
 */
@Component
public class ArticleMessageListener {

    @Autowired
    private CommonRedisDao commonRedisDao;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @JmsListener(destination = "article")
    public void onMessage(ActiveMQObjectMessage message) {
        logger.debug("消费者接收到文章消息");
        try {
            Integer articleID = (Integer) message.getObject();
            long count = commonRedisDao.incr(Constant.READ_COUNT_KEY + articleID);
            logger.debug("文章id:{} 的阅读量现在是：{}", articleID, count);
        } catch (JMSException e) {
            logger.error("消息接收异常！", e);
        }
    }
}
