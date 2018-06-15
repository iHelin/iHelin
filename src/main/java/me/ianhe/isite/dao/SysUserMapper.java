package me.ianhe.isite.dao;

import me.ianhe.isite.entity.Role;
import me.ianhe.isite.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
public interface SysUserMapper {

    User loadUserByUsername(String username);

    List<Role> getRolesByUserId(Long id);

    int hrReg(@Param("username") String username, @Param("password") String password);

    List<User> getUsersByKeywords(@Param("keywords") String keywords);

    int updateUser(User hr);

    int deleteRoleByUserId(Long uid);

    int addRolesForUser(@Param("uid") Long uid, @Param("rids") Long[] rids);

    User getUserById(Long uid);

    int deleteUser(Long uid);

    List<User> getAllUser(@Param("currentId") Long currentId);
}
