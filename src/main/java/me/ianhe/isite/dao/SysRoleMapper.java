package me.ianhe.isite.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.isite.entity.SysRole;

import java.util.List;

/**
 * SysRoleMapper
 *
 * @author iHelin
 * @since 2018/7/9 12:38
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> getUserRoles(Integer userID);

    List<SysRole> getAllRoles();
}