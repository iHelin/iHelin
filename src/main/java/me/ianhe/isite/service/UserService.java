package me.ianhe.isite.service;

import me.ianhe.isite.dao.UserMapper;
import me.ianhe.isite.entity.User;
import me.ianhe.isite.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不对");
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

    public List<User> getHrsByKeywords(String keywords) {
        return userMapper.getHrsByKeywords(keywords);
    }

    public int updateHr(User hr) {
        return userMapper.updateHr(hr);
    }

    public int updateHrRoles(Long hrId, Long[] rids) {
        int i = userMapper.deleteRoleByHrId(hrId);
        return userMapper.addRolesForHr(hrId, rids);
    }

    public User getHrById(Long hrId) {
        return userMapper.getHrById(hrId);
    }

    public int deleteHr(Long hrId) {
        return userMapper.deleteHr(hrId);
    }

    public List<User> getAllHrExceptAdmin() {
        return userMapper.getAllHr(SecurityUtils.getCurrentHr().getId());
    }

    public List<User> getAllHr() {
        return userMapper.getAllHr(null);
    }
}
