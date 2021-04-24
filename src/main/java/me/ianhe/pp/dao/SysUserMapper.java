package me.ianhe.pp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.pp.entity.SysUserEntity;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryPermissionsByUserId(Integer userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Integer> queryMenuIdByUserId(Integer userId);

}
