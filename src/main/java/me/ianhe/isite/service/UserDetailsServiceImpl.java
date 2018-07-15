package me.ianhe.isite.service;

import me.ianhe.isite.dao.SysUserMapper;
import me.ianhe.isite.entity.User;
import me.ianhe.isite.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = sysUserMapper.loadUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }

    public int userReg(String username, String password) {
        //如果用户名存在，返回错误
        if (sysUserMapper.loadUserByUsername(username) != null) {
            return -1;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        return sysUserMapper.userReg(username, encode);
    }

    public List<User> getUsersByKeywords(String keywords) {
        return sysUserMapper.getUsersByKeywords(keywords);
    }

    public int updateUser(User user) {
        return sysUserMapper.updateUser(user);
    }

    public int updateUserRoles(Long uid, Long[] rids) {
        int i = sysUserMapper.deleteRoleByUserId(uid);
        return sysUserMapper.addRolesForUser(uid, rids);
    }

    public User getUserById(Long uid) {
        return sysUserMapper.getUserById(uid);
    }

    public int deleteUser(Long uid) {
        return sysUserMapper.deleteUser(uid);
    }

    public List<User> getAllUserExceptAdmin() {
        return sysUserMapper.getAllUser(SystemUtils.getCurrentUser().getId());
    }

    public List<User> getAllUser() {
        return sysUserMapper.getAllUser(null);
    }
}
