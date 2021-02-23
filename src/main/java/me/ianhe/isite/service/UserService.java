package me.ianhe.isite.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.ianhe.isite.dao.SysUserMapper;
import me.ianhe.isite.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService extends ServiceImpl<SysUserMapper, User> implements UserDetailsService {

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = baseMapper.selectById(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }

//    public int userReg(String username, String password) {
//        //如果用户名存在，返回错误
//        if (baseMapper.loadUserByUsername(username) != null) {
//            return -1;
//        }
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encode = encoder.encode(password);
//        return baseMapper.userReg(username, encode);
//    }

    public User login(Map<String, String> body, String openId, String sessionKey) {
        String avatarUrl = body.get("avatarUrl");
        String nickname = body.get("nickname");
        User user = this.getOne(new QueryWrapper<User>().eq("wx_open_id", openId));
        if (user == null) {
            User newUser = new User();
            newUser.setWxOpenId(openId);
            newUser.setAvatarUrl(avatarUrl);
            newUser.setNickname(nickname);
            newUser.setSessionKey(sessionKey);
            newUser.setCreateTime(LocalDateTime.now());
            newUser.setUpdateTime(LocalDateTime.now());
            this.save(newUser);
            return newUser;
        }
        return user;
    }

}
