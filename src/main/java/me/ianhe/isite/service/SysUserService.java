package me.ianhe.isite.service;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.ianhe.isite.dao.SysUserMapper;
import me.ianhe.isite.entity.SysUserEntity;
import me.ianhe.isite.exception.RRException;
import me.ianhe.isite.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserService extends ServiceImpl<SysUserMapper, SysUserEntity> implements UserDetailsService {

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询用户的所有权限
     *
     * @param userId
     * @return
     */
    public List<String> queryPermissionsByUserId(Integer userId) {
        return baseMapper.queryPermissionsByUserId(userId);
    }

    /**
     * 查询用户的所有菜单ID
     *
     * @param userId
     * @return
     */
    public List<Integer> queryMenuIdByUserId(Integer userId) {
        return baseMapper.queryMenuIdByUserId(userId);
    }

    /**
     * 通过用户名获取用户及权限
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SysUserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity sysUserEntity = this.getOne(new QueryWrapper<SysUserEntity>().eq("username", username));
        if (sysUserEntity != null) {
            List<GrantedAuthority> authorities = getSysUserAuthority(sysUserEntity.getId());
            sysUserEntity.setAuthorities(authorities);
        }
        return sysUserEntity;
    }

    /**
     * 通过id获取用户及权限
     *
     * @param userId
     * @return
     */
    public SysUserEntity loadUserById(String userId) {
        SysUserEntity sysUserEntity = this.getById(userId);
        if (sysUserEntity != null) {
            List<GrantedAuthority> authorities = getSysUserAuthority(sysUserEntity.getId());
            sysUserEntity.setAuthorities(authorities);
        }
        return sysUserEntity;
    }

    /**
     * 保存用户
     *
     * @param user
     */
    @Transactional
    public void saveUser(SysUserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.save(user);
        //检查角色是否越权
        checkRole(user);
        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
    }


    @Transactional
    public void updateUser(SysUserEntity user) {
        if (StringUtils.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        this.updateById(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
    }

    public SysUserEntity login(String nickname, String avatarUrl, String openId, String sessionKey,
                               Integer gender, String country, String province, String city, String language) {
        SysUserEntity user = this.getOne(new QueryWrapper<SysUserEntity>().eq("wx_open_id", openId));
        if (user == null) {
            SysUserEntity newUser = new SysUserEntity();
            newUser.setWxOpenId(openId);
            newUser.setNickname(nickname);
            newUser.setAvatarUrl(avatarUrl);
            newUser.setSessionKey(sessionKey);
            newUser.setGender(gender);
            newUser.setCountry(country);
            newUser.setProvince(province);
            newUser.setCity(city);
            newUser.setLanguage(language);
            newUser.setCreateTime(DateTime.now());
            newUser.setUpdateTime(DateTime.now());
            this.save(newUser);
            return newUser;
        } else {
            user.setAvatarUrl(avatarUrl);
            user.setNickname(nickname);
            user.setSessionKey(sessionKey);
            user.setGender(gender);
            user.setCountry(country);
            user.setProvince(province);
            user.setCity(city);
            user.setLanguage(language);
            this.updateById(user);
            return user;
        }
    }

    /**
     * 获取用户权限，并转成spring security对应的权限
     *
     * @param userId
     * @return
     */
    private List<GrantedAuthority> getSysUserAuthority(Integer userId) {
        Set<String> userPermissions = sysMenuService.getUserPermissions(userId);
        return userPermissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    /**
     * 修改密码
     *
     * @param userId         修改密码的用户id
     * @param oldRawPassword 原密码、旧密码
     * @param newRawPassword 新的明文密码：需要加密存储
     * @return
     */
    public boolean updatePassword(Integer userId, String oldRawPassword, String newRawPassword) {
        SysUserEntity sysUserEntity = this.getById(userId);
        if (passwordEncoder.matches(oldRawPassword, sysUserEntity.getPassword())) {
            SysUserEntity userEntity = new SysUserEntity();
            String encodedNewPassword = passwordEncoder.encode(newRawPassword);
            userEntity.setPassword(encodedNewPassword);
            return this.update(userEntity,
                    new UpdateWrapper<SysUserEntity>().eq("id", userId));
        } else {
            return false;
        }
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserEntity user) {
        if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
            return;
        }
        //如果是超级管理员，则不需要判断用户的角色是否自己创建
        if (Constant.SUPER_ADMIN_ID.equals(user.getCreateUserId())) {
            return;
        }

        //查询用户创建的角色列表
        List<Integer> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new RRException("新增用户所选角色，不是本人创建");
        }
    }
}
