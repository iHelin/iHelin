package me.ianhe.isite.dao;

import me.ianhe.isite.entity.Score;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * MyScoreMapper
 *
 * @author iHelin
 * @since 2018/7/9 12:38
 */
public interface ScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Score record);

    int insertSelective(Score record);

    @Cacheable("score")
    Score selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Score record);

    @CacheEvict("score")
    int updateByPrimaryKey(Score record);

    Long getTotalScore();

    List<Score> selectByCondition(RowBounds rowBounds);
}