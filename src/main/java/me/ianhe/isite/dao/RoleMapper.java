package me.ianhe.isite.dao;

import me.ianhe.isite.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
public interface RoleMapper {

    List<Role> roles();

    int addNewRole(@Param("role") String role, @Param("roleZh") String roleZh);

    int deleteRoleById(Long rid);
}
