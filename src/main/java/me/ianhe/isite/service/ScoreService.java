package me.ianhe.isite.service;

import com.github.pagehelper.PageInfo;
import me.ianhe.isite.dao.ScoreMapper;
import me.ianhe.isite.entity.Score;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author iHelin
 * @create 2017-02-15 19:19
 */
@Service
public class ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    public Score getById(Integer id) {
        return scoreMapper.selectByPrimaryKey(id);
    }

    public Long getMyTotalScore() {
        return scoreMapper.getTotalScore() == null ? 0 : scoreMapper.getTotalScore();
    }

    public int addRecord(Score ms) {
        return scoreMapper.insert(ms);
    }

    public PageInfo<Score> selectByCondition(int pageNum, int pageSize) {
        return new PageInfo<>(scoreMapper.selectByCondition(new RowBounds(pageNum, pageSize)));
    }

}
