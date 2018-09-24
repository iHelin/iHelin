package me.ianhe.isite.controller;

import me.ianhe.isite.config.SystemProperties;
import me.ianhe.isite.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 分数
 *
 * @author iHelin
 * @create 2017-02-15 19:18
 */
@RestController
public class ScoreController extends BaseController {

    @Autowired
    private SystemProperties systemProperties;

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
