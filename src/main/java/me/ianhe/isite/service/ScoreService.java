package me.ianhe.isite.service;

import me.ianhe.isite.dao.MyScoreMapper;
import me.ianhe.isite.entity.MyScore;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author iHelin
 * @create 2017-02-15 19:19
 */
@Service
public class ScoreService {

    @Autowired
    private MyScoreMapper myScoreMapper;

    public MyScore getById(Integer id) {
        return myScoreMapper.selectByPrimaryKey(id);
    }

    public long getMyTotalScore() {
        return myScoreMapper.getTotalScore();
    }

    public int addRecord(MyScore ms) {
        return myScoreMapper.insert(ms);
    }

    public List<MyScore> selectByCondition(int offset, int size) {
        return myScoreMapper.selectByCondition(new RowBounds(offset, size));
    }

}
