package me.ianhe.pp.aspect;

import com.google.common.collect.Maps;
import me.ianhe.pp.entity.Score;
import me.ianhe.pp.service.DingService;
import me.ianhe.pp.service.EmailService;
import me.ianhe.pp.service.ScoreService;
import me.ianhe.pp.service.TemplateService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 加分提醒
 *
 * @author iHelin
 * @since 2017/5/31 16:51
 */
@Aspect
@Component
public class ScoreAfterAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private EmailService emailService;

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
    @AfterReturning("execution(* me.ianhe.pp.controller.ScoreController.addScore(..))")
    public void afterAddScore(JoinPoint joinPoint) {
        Score score = (Score) joinPoint.getArgs()[0];
        Long total = scoreService.getMyTotalScore();
        String msg;
        int goal = 5201314;
        if (total >= goal) {
            msg = "恭喜你们达标啦！！！";
        } else {
            msg = String.format("今天加了%d分，理由是：%s，现在一共有%d分，还没达到目标，继续努力呦！", score.getValue(),
                score.getReason(), total);
        }
        logger.debug(msg);
        dingService.sendTextMsg(msg);

        Map<String, Object> res = Maps.newHashMap();
        res.put("score", score);
        res.put("total", total);
        String mailContent = templateService.applyTemplate("score.ftl", res);
        String title = "加分提醒:今天加了" + score.getValue() + "分";
        String sendTo = "ahaqhelin@163.com";
        emailService.sendSimpleMail(sendTo, title, mailContent);
    }

    @Around("execution( * me.ianhe.pp.controller.TestController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.debug("around start...");
        Object proceed = proceedingJoinPoint.proceed();
        logger.debug("around end...");
        return proceed;
    }

}
