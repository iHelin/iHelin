package me.ianhe.isite.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.isite.entity.User;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
public interface SysUserMapper extends BaseMapper<User> {

    User loadUserByUsername(String username);
}
