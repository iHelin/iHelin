package me.ianhe.isite.dao;

import me.ianhe.isite.entity.Menu;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
public interface SysMenuMapper {

    List<Menu> getAllMenu();

    List<Menu> getMenusByUserId(Long uid);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);
}
