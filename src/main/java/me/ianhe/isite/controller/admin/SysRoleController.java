package me.ianhe.isite.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.ianhe.isite.entity.SysRoleEntity;
import me.ianhe.isite.service.SysRoleMenuService;
import me.ianhe.isite.service.SysRoleService;
import me.ianhe.isite.utils.Constant;
import me.ianhe.isite.utils.R;
import me.ianhe.isite.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author iHelin ihelin@outlook.com
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseAdminController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 角色列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public R list(@RequestParam(defaultValue = "1") Long page,
                  @RequestParam(defaultValue = "10") Long limit,
                  String roleName) {
        QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleName), "role_name", roleName);
        Integer userId = getUser().getId();
        if (!Constant.SUPER_ADMIN_ID.equals(userId)) {
            //如果不是超级管理员，则只查询自己创建的角色列表
            queryWrapper.eq("create_user_id", userId);
        }
        IPage<SysRoleEntity> data = sysRoleService.page(
                new Page<>(page, limit),
                queryWrapper
        );
        return R.ok().put("data", data);
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @PreAuthorize("hasAuthority('sys:role:select')")
    public R select() {
        Map<String, Object> map = new HashMap<>();
        Integer userId = getUser().getId();
        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (!Constant.SUPER_ADMIN_ID.equals(userId)) {
            map.put("create_user_id", userId);
        }
        List<SysRoleEntity> list = sysRoleService.listByMap(map);
        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:info')")
    public R info(@PathVariable("roleId") Integer roleId) {
        SysRoleEntity role = sysRoleService.getById(roleId);

        //查询角色对应的菜单
        List<Integer> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:role:save')")
    public R save(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);
        Integer userId = getUser().getId();
        role.setCreateUserId(userId);
        sysRoleService.saveRole(role);

        return R.ok();
    }

    /**
     * 修改角色
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public R update(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);
        Integer userId = getUser().getId();
        role.setCreateUserId(userId);
        sysRoleService.update(role);
        return R.ok();
    }

    /**
     * 删除角色
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public R delete(@RequestBody List<Integer> roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return R.ok();
    }
}
