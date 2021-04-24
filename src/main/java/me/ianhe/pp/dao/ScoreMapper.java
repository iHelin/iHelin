package me.ianhe.pp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.pp.entity.Score;

/**
 * ScoreMapper
 *
 * @author iHelin
 * @since 2018/7/9 12:38
 */
public interface ScoreMapper extends BaseMapper<Score> {

    Long getTotalScore();

}