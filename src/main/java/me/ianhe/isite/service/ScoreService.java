package me.ianhe.isite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.ianhe.isite.dao.ScoreMapper;
import me.ianhe.isite.entity.Score;
import org.springframework.stereotype.Service;

/**
 * @author iHelin
 * @since 2017-02-15 19:19
 */
@Service
public class ScoreService extends ServiceImpl<ScoreMapper, Score> {

    public Long getMyTotalScore() {
        Long totalScore = baseMapper.getTotalScore();
        return totalScore == null ? 0 : totalScore;
    }

    public IPage<Score> selectByCondition(int pageNum, int pageSize) {
        return this.page(new Page<>(pageNum, pageSize));
    }

}
