package me.ianhe.isite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.ianhe.isite.dao.ScoreMapper;
import me.ianhe.isite.entity.Score;
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

    public IPage<Score> selectByCondition(int pageNum, int pageSize) {
        return scoreMapper.selectPage(new Page<>(pageNum, pageSize), null);
    }

}
