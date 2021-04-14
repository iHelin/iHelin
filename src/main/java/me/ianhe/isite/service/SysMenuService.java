package me.ianhe.isite.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import me.ianhe.isite.dao.SysMenuDao;
import me.ianhe.isite.entity.SysMenuEntity;
import me.ianhe.isite.entity.SysRoleMenuEntity;
import me.ianhe.isite.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;


@Service
public class SysMenuService extends ServiceImpl<SysMenuDao, SysMenuEntity> {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    /**
     * 根据父菜单id，查询子菜单
     *
     * @param parentId
     * @param menuIdList
     * @return
     */
    public List<SysMenuEntity> getMenusByParentId(Integer parentId, List<Integer> menuIdList) {
        List<SysMenuEntity> menuList = getMenusByParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }


    public List<SysMenuEntity> getMenusByParentId(Integer parentId) {
        return baseMapper.getMenusByParentId(parentId);
    }


    public List<SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }


    public List<SysMenuEntity> getUserMenuList(Integer userId) {
        //超级管理员，拥有最高权限
        if (Constant.SUPER_ADMIN_ID.equals(userId)) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Integer> menuIdList = sysUserService.queryMenuIdByUserId(userId);
        return getAllMenuList(menuIdList);
    }

    @Transactional
    public void delete(Integer menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        QueryWrapper<SysRoleMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id", menuId);
        sysRoleMenuService.remove(queryWrapper);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuEntity> getAllMenuList(List<Integer> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = getMenusByParentId(0, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Integer> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<>();

        for (SysMenuEntity entity : menuList) {
            //目录
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(getMenusByParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }

    /**
     * 获取用户权限列表
     *
     * @param userId
     * @return
     */
    public Set<String> getUserPermissions(Integer userId) {
        List<String> permissions;
        //系统管理员，拥有最高权限
        if (Constant.SUPER_ADMIN_ID.equals(userId)) {
            permissions = baseMapper.getAllPerms();
        } else {
            permissions = sysUserService.queryPermissionsByUserId(userId);
        }
        //用户权限列表
        Set<String> permissionSet = new HashSet<>();
        for (String perms : permissions) {
            if (StringUtils.hasText(perms)) {
                permissionSet.addAll(Arrays.asList(perms.trim().split(",")));
            }
        }
        return permissionSet;
    }

    /**
     * 保存菜单
     *
     * @param sysMenuEntity 待保存的菜单
     */
    @Transactional
    public void saveMenu(SysMenuEntity sysMenuEntity) {
        this.save(sysMenuEntity);
        if (sysMenuEntity.getType() == 1 && StringUtils.hasText(sysMenuEntity.getPerms())) {
            //如果是菜单，即二级菜单
            List<SysMenuEntity> subButtonList = Lists.newArrayList();
            SysMenuEntity addMenu = new SysMenuEntity();
            addMenu.setType(2);
            addMenu.setName("新增");
            addMenu.setParentId(sysMenuEntity.getMenuId());
            addMenu.setPerms(sysMenuEntity.getPerms() + ":save");
            addMenu.setOrderNum(0);
            subButtonList.add(addMenu);
            SysMenuEntity updateMenu = new SysMenuEntity();
            updateMenu.setType(2);
            updateMenu.setName("修改");
            updateMenu.setParentId(sysMenuEntity.getMenuId());
            updateMenu.setPerms(sysMenuEntity.getPerms() + ":update," + sysMenuEntity.getPerms() + ":info");
            updateMenu.setOrderNum(0);
            subButtonList.add(updateMenu);
            SysMenuEntity deleteMenu = new SysMenuEntity();
            deleteMenu.setType(2);
            deleteMenu.setName("删除");
            deleteMenu.setParentId(sysMenuEntity.getMenuId());
            deleteMenu.setPerms(sysMenuEntity.getPerms() + ":delete");
            deleteMenu.setOrderNum(0);
            subButtonList.add(deleteMenu);
            SysMenuEntity listMenu = new SysMenuEntity();
            listMenu.setType(2);
            listMenu.setName("查看");
            listMenu.setParentId(sysMenuEntity.getMenuId());
            listMenu.setPerms(sysMenuEntity.getPerms() + ":list," + sysMenuEntity.getPerms() + ":info");
            listMenu.setOrderNum(0);
            subButtonList.add(listMenu);
            this.saveBatch(subButtonList);
        }
    }
}
