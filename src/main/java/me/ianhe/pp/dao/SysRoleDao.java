package me.ianhe.pp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.pp.entity.SysRoleEntity;

import java.util.List;

/**
 * 角色管理
 *
 * @author iHelin ihelin@outlook.com
 */
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Integer> queryRoleIdList(Integer createUserId);
}
