package me.ianhe.isite.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.ianhe.isite.entity.SysUserEntity;
import me.ianhe.isite.pojo.PasswordForm;
import me.ianhe.isite.pojo.validator.AddGroup;
import me.ianhe.isite.pojo.validator.UpdateGroup;
import me.ianhe.isite.service.SysUserRoleService;
import me.ianhe.isite.service.SysUserService;
import me.ianhe.isite.utils.Constant;
import me.ianhe.isite.utils.R;
import me.ianhe.isite.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统用户
 *
 * @author iHelin ihelin@outlook.com
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseAdminController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public R list(@RequestParam(defaultValue = "1") Long page,
                  @RequestParam(defaultValue = "10") Long limit,
                  String username) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(username), "username", username);
        //只有超级管理员，才能查看所有用户列表
        Integer userId = getUser().getId();
        if (!Constant.SUPER_ADMIN_ID.equals(userId)) {
            queryWrapper.eq("create_user_id", userId);
        }
        IPage<SysUserEntity> data = sysUserService.page(
                new Page<>(page, limit),
                queryWrapper
        );
        return R.ok().put("data", data);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @PostMapping("/password")
    public R password(@RequestBody PasswordForm form) {

        String oldPassword = form.getPassword();
        String newPassword = form.getNewPassword();

        //更新密码
        boolean flag = sysUserService.updatePassword(getUser().getId(), oldPassword, newPassword);
        if (!flag) {
            return R.error("原密码不正确");
        }
        return R.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public R info(@PathVariable("userId") Integer userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Integer> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public R save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        user.setCreateUserId(getUser().getId());
        sysUserService.saveUser(user);
        return R.ok();
    }

    /**
     * 修改用户
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public R update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        user.setCreateUserId(getUser().getId());
        sysUserService.updateUser(user);
        return R.ok();
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public R delete(@RequestBody List<Integer> userIds) {
        if (userIds.contains(Constant.SUPER_ADMIN_ID)) {
            return R.error("超级管理员不能删除");
        }
        if (userIds.contains(getUser().getId())) {
            return R.error("当前用户不能删除");
        }
        sysUserService.removeByIds(userIds);
        return R.ok();
    }
}
