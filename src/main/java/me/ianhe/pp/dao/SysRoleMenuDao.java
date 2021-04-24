package me.ianhe.pp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.pp.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author iHelin ihelin@outlook.com
 */
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Integer> queryMenuIdList(Integer roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(List<Integer> roleIds);
}
