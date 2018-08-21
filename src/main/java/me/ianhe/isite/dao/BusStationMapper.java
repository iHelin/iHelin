package me.ianhe.isite.dao;


import me.ianhe.isite.entity.BusStation;

import java.util.List;

/**
 * BusStationMapper
 *
 * @author iHelin
 * @since 2018/7/9 12:37
 */
public interface BusStationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusStation record);

    int insertSelective(BusStation record);

    BusStation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusStation record);

    int updateByPrimaryKey(BusStation record);

    int insertBatch(List<BusStation> poemList);
}