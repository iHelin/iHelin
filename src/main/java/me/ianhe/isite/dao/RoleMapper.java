package me.ianhe.isite.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.isite.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> roles();

    int addNewRole(@Param("role") String role, @Param("roleZh") String roleZh);

    int deleteRoleById(Long rid);
}
