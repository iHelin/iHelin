package me.ianhe.isite.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.isite.entity.Score;

/**
 * ScoreMapper
 *
 * @author iHelin
 * @since 2018/7/9 12:38
 */
public interface ScoreMapper extends BaseMapper<Score> {

    Long getTotalScore();

}