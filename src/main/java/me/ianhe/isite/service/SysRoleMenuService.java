package me.ianhe.isite.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.ianhe.isite.dao.SysRoleMenuDao;
import me.ianhe.isite.entity.SysRoleMenuEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


/**
 * 角色与菜单对应关系
 *
 * @author iHelin ihelin@outlook.com
 */
@Service
public class SysRoleMenuService extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> {


    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Integer roleId, List<Integer> menuIdList) {
        //先删除角色与菜单关系
        deleteBatch(Collections.singletonList(roleId));
        if (menuIdList.size() == 0) {
            return;
        }
        //保存角色与菜单关系
        for (Integer menuId : menuIdList) {
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);

            this.save(sysRoleMenuEntity);
        }
    }


    public List<Integer> queryMenuIdList(Integer roleId) {
        return baseMapper.queryMenuIdList(roleId);
    }


    public int deleteBatch(List<Integer> roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }

}
