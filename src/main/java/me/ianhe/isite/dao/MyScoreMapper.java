package me.ianhe.isite.dao;

import me.ianhe.isite.entity.MyScore;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * MyScoreMapper
 *
 * @author linhe2
 * @since 2018/7/9 12:38
 */
public interface MyScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MyScore record);

    int insertSelective(MyScore record);

    MyScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MyScore record);

    int updateByPrimaryKeyWithBLOBs(MyScore record);

    int updateByPrimaryKey(MyScore record);

    long getTotalScore();

    List<MyScore> selectByCondition(RowBounds rowBounds);
}