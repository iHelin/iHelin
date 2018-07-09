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
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }

    public int hrReg(String username, String password) {
        //如果用户名存在，返回错误
        if (userMapper.loadUserByUsername(username) != null) {
            return -1;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        return userMapper.hrReg(username, encode);
    }

    public List<User> getUsersByKeywords(String keywords) {
        return userMapper.getUsersByKeywords(keywords);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public int updateUserRoles(Long uid, Long[] rids) {
        int i = userMapper.deleteRoleByUserId(uid);
        return userMapper.addRolesForUser(uid, rids);
    }

    public User getUserById(Long uid) {
        return userMapper.getUserById(uid);
    }

    public int deleteUser(Long uid) {
        return userMapper.deleteUser(uid);
    }

    public List<User> getAllUserExceptAdmin() {
        return userMapper.getAllUser(SystemUtils.getCurrentUser().getId());
    }

    public List<User> getAllUser() {
        return userMapper.getAllUser(null);
    }
}
