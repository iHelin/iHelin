package me.ianhe.isite.dao;

import me.ianhe.isite.entity.Menu;

import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
public interface SysMenuMapper {

    List<Menu> getAllMenu();

    List<Menu> getMenusByUserId(Long uid);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);
}
