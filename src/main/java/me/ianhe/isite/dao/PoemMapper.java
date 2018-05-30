package me.ianhe.isite.dao;


import me.ianhe.isite.entity.Poem;

import java.util.List;

public interface PoemMapper {
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