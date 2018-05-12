package me.ianhe.isite.aspect;

import com.google.common.collect.Maps;
import me.ianhe.isite.entity.MyScore;
import me.ianhe.isite.model.MailModel;
import me.ianhe.isite.service.DingService;
import me.ianhe.isite.service.JmsProducerService;
import me.ianhe.isite.service.ScoreService;
import me.ianhe.isite.service.TemplateService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.jms.Destination;
import java.util.Map;

/**
 * 加分提醒
 *
 * @author iHelin
 * @since 2017/5/31 16:51
 */
@Aspect
public class ScoreAfterAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private JmsProducerService producerService;

    @Autowired
    @Qualifier("mail")
    private Destination destination;

    @Autowired
    private DingService dingService;

    @Autowired
    private TemplateService templateService;

    /**
     * 加分成功后再发送提醒
     *
     * @author iHelin
     * @since 2017/6/1 10:35
     */
    @AfterReturning(value = "execution(* addRecord(..))")
    public void afterAddScore(JoinPoint joinPoint) {
        MyScore myScore = (MyScore) joinPoint.getArgs()[0];
        long total = scoreService.getMyTotalScore();
        String msg;
        int goal = 5201314;
        if (total >= goal) {
            msg = "恭喜你们达标啦！！！";
        } else {
            msg = String.format("今天又加了%d分，理由是：%s，现在一共有%d分，加油，你们要继续努力呦！", myScore.getScore(),
                    myScore.getReason(), total);
        }
        logger.debug(msg);
        dingService.sendTextMsg(msg);

        Map<String, Object> res = Maps.newHashMap();
        res.put("score", myScore);
        res.put("total", total);
        String mailContent = templateService.applyTemplate("score.ftl", res);
        String title = "加分提醒:今天加了" + myScore.getScore() + "分";
        MailModel mail = new MailModel("ahaqhelin@163.com;1018954240@qq.com", "葫芦娃", title, mailContent);
        producerService.sendMessage(destination, mail);
    }

}
