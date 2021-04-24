package me.ianhe.pp.controller;

import me.ianhe.pp.entity.Score;
import me.ianhe.pp.utils.R;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 分数
 *
 * @author iHelin
 * @since 2017-02-15 19:18
 */
@RestController
public class ScoreController extends BaseController {

    /**
     * 加分操作
     *
     * @param score
     * @return
     */
    @PostMapping("/scores")
    public Map<String, Object> addScore(@RequestBody Score score) {
        score.setCreateTime(new Date());
        score.setUpdateTime(new Date());
        scoreService.save(score);
        return R.ok();
    }

    @GetMapping("/scores/total")
    public R getTotalScore() {
        return R.ok(scoreService.getMyTotalScore());
    }

    @GetMapping("/scores/{id:\\d+}")
    public R getScore(@PathVariable Integer id) {
        Assert.notNull(id, "score id can not be null.");
        return R.ok(scoreService.getById(id));
    }

    @GetMapping("/scores")
    public R getScores(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNum,
                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
        return R.ok(scoreService.selectByCondition(pageNum, pageSize));
    }

}
