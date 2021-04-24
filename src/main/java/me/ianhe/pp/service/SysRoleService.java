package me.ianhe.pp.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.ianhe.pp.dao.SysRoleDao;
import me.ianhe.pp.entity.SysRoleEntity;
import me.ianhe.pp.exception.RRException;
import me.ianhe.pp.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author iHelin ihelin@outlook.com
 */
@Service("sysRoleService")
public class SysRoleService extends ServiceImpl<SysRoleDao, SysRoleEntity> {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleEntity role) {
        role.setCreateTime(new Date());
        this.save(role);

        //检查权限是否越权
        checkPrems(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }


    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity role) {
        this.updateById(role);

        //检查权限是否越权
        checkPrems(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }


    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Integer> roleIds) {
        //删除角色
        this.removeByIds(roleIds);

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }


    public List<Integer> queryRoleIdList(Integer createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(SysRoleEntity role) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (Constant.SUPER_ADMIN_ID.equals(role.getCreateUserId())) {
            return;
        }

        //查询用户所拥有的菜单列表
        List<Integer> menuIdList = sysUserService.queryMenuIdByUserId(role.getCreateUserId());

        //判断是否越权
        if (!menuIdList.containsAll(role.getMenuIdList())) {
            throw new RRException("新增角色的权限，已超出你的权限范围");
        }
    }
}
