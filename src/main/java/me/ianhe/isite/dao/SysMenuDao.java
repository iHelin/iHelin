package me.ianhe.isite.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.isite.entity.SysMenuEntity;

import java.util.List;

/**
 * 菜单管理
 *
 * @author iHelin ihelin@outlook.com
 */
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

    /**
     * 根据父菜单id，查询子菜单
     *
     * @param parentId 父菜单ID
     */
    List<SysMenuEntity> getMenusByParentId(Integer parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 获取所有权限字符串合集
     *
     * @return
     */
    List<String> getAllPerms();

}
