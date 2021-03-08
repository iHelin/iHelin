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

    List<SysRole> getUserRoles(Integer userID);

    List<SysRole> getAllRoles();
}