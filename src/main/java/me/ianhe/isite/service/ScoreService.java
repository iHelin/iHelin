package me.ianhe.isite.service;

import me.ianhe.isite.dao.ScoreMapper;
import me.ianhe.isite.entity.Score;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author iHelin
 * @create 2017-02-15 19:19
 */
@Service
public class ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Cacheable("score")
    public Score getById(Integer id) {
        return scoreMapper.selectByPrimaryKey(id);
    }

    public Long getMyTotalScore() {
        return scoreMapper.getTotalScore();
    }

    public int addRecord(Score ms) {
        return scoreMapper.insert(ms);
    }

    public List<Score> selectByCondition(int offset, int size) {
        return scoreMapper.selectByCondition(new RowBounds(offset, size));
    }

}
