package me.ianhe.pp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.pp.entity.SysUserRoleEntity;

import java.util.List;

/**
 * 用户与角色对应关系
 *
 * @author iHelin ihelin@outlook.com
 */
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Integer> queryRoleIdList(Integer userId);


    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(List<Integer> roleIds);
}
