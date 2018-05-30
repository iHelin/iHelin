package me.ianhe.isite.model;

import com.google.common.collect.Lists;
import me.ianhe.isite.entity.SysRole;
import me.ianhe.isite.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author iHelin
 * @since 2018/4/27 22:40
 */
public class SecurityUser extends User implements UserDetails {

    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = Lists.newArrayList();
        List<SysRole> roles = this.getSysRoles();
        if (roles != null) {
            for (SysRole role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
