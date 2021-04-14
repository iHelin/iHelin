package me.ianhe.isite.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.ianhe.isite.dao.SysUserMapper;
import me.ianhe.isite.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public SysUserEntity login(String nickname, String avatarUrl, String openId, String sessionKey) {
        SysUserEntity user = this.getOne(new QueryWrapper<SysUserEntity>().eq("wx_open_id", openId));
        if (user == null) {
            SysUserEntity newUser = new SysUserEntity();
            newUser.setWxOpenId(openId);
            newUser.setAvatarUrl(avatarUrl);
            newUser.setNickname(nickname);
            newUser.setSessionKey(sessionKey);
            newUser.setCreateTime(LocalDateTime.now());
            newUser.setUpdateTime(LocalDateTime.now());
            this.save(newUser);
            return newUser;
        } else {
            user.setAvatarUrl(avatarUrl);
            user.setNickname(nickname);
            user.setSessionKey(sessionKey);
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

}
