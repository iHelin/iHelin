package me.ianhe.isite.controller;

import me.ianhe.isite.entity.MyScore;
import me.ianhe.isite.utils.JsonUtil;
import me.ianhe.isite.utils.WechatUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 分数
 *
 * @author iHelin
 * @create 2017-02-15 19:18
 */
@RestController
public class ScoreController extends BaseController {

    @Value("${seven.appid}")
    private String appid;
    @Value("${seven.secret}")
    private String appSecret;


    @GetMapping("score/login")
    public String login(String code) {
        //https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + appid + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        logger.info("url is : {}", url);
        String res = WechatUtil.doGetStr(url);
        logger.info("res is :{}", res);
        Map<String, Object> resMap = JsonUtil.parseMap(res);
        String openid = (String) resMap.get("openid");
        String sessionKey = (String) resMap.get("session_key");
        commonRedisDao.setTimeout(sessionKey, openid + sessionKey, 7200L, TimeUnit.SECONDS);
        return sessionKey;
    }

    /**
     * 加分操作
     *
     * @param myScore
     * @return
     */
    @PostMapping("score")
    public String addScore(MyScore myScore) {
        myScore.setAddDate(new Date());
        scoreService.addRecord(myScore);
        return success();
    }

    @GetMapping("score/all")
    public String getTotalScore() {
        long totalScore = scoreService.getMyTotalScore();
        return success(totalScore);
    }

    @GetMapping("score/{id}")
    public String getScore(@PathVariable Integer id) {
        MyScore myScore = scoreService.getById(id);
        return success(myScore);
    }

    @GetMapping("scores")
    public String getScores(Integer pageNum, Integer pageLength) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageLength = pageLength == null ? DEFAULT_PAGE_LENGTH : pageLength;
        List<MyScore> scores = scoreService.selectByCondition(pageNum, pageLength);
        return success(scores);
    }

}
