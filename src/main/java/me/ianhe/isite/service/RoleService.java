package me.ianhe.isite.service;

import me.ianhe.isite.dao.RoleMapper;
import me.ianhe.isite.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> roles() {
        return roleMapper.roles();
    }

    public int addNewRole(String role, String roleZh) {
        String prefix = "ROLE_";
        if (!role.startsWith(prefix)) {
            role = prefix + role;
        }
        return roleMapper.addNewRole(role, roleZh);
    }

    public int deleteRoleById(Long rid) {
        return roleMapper.deleteRoleById(rid);
    }
}
