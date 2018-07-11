package me.ianhe.isite.controller;

import me.ianhe.isite.entity.Score;
import me.ianhe.isite.utils.JsonUtil;
import me.ianhe.isite.utils.WechatUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("scores/login")
    public String login(String code) {
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
    @PostMapping("scores")
    public Map<String, Object> addScore(Score myScore) {
        myScore.setCreateTime(new Date());
        scoreService.addRecord(myScore);
        return success();
    }

    @GetMapping("scores/total")
    public Long getTotalScore() {
        return scoreService.getMyTotalScore();
    }

    @GetMapping("scores/{id:\\d+}")
    public Score getScore(@PathVariable Integer id) {
        Assert.notNull(id, "score id can not be null.");
        return scoreService.getById(id);
    }

    @GetMapping("scores")
    public List<Score> getScores(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = DEFAULT_PAGE_LENGTH) Integer pageLength) {
        return scoreService.selectByCondition(pageNum, pageLength);
    }

}
