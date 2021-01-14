package me.ianhe.isite.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.isite.entity.Poem;

import java.util.List;

/**
 * PoemMapper
 *
 * @author iHelin
 * @since 2018/7/9 12:38
 */
public interface PoemMapper extends BaseMapper<Poem> {
    int deleteByPrimaryKey(Integer id);

    int insert(Poem record);

    int insertSelective(Poem record);

    Poem selectByPrimaryKey(Integer id);

    Poem getByRandom();

    int updateByPrimaryKeySelective(Poem record);

    int updateByPrimaryKeyWithBLOBs(Poem record);

    int updateByPrimaryKey(Poem record);

    int insertBatch(List<Poem> poemList);
}