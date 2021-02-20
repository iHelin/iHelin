package me.ianhe.isite.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.isite.entity.Role;
import me.ianhe.isite.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
public interface SysUserMapper extends BaseMapper<User> {

    User loadUserByUsername(String username);

    List<Role> getRolesByUserId(Long id);

    int userReg(@Param("username") String username, @Param("password") String password);

    List<User> getUsersByKeywords(@Param("keywords") String keywords);

    int deleteRoleByUserId(Long uid);

    int addRolesForUser(@Param("uid") Long uid, @Param("rids") Long[] rids);

    User getUserById(Long uid);

    int deleteUser(Long uid);

    List<User> getAllUser(@Param("currentId") Long currentId);
}
