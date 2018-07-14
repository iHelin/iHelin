package me.ianhe.isite.service;

import me.ianhe.isite.dao.SysMenuMapper;
import me.ianhe.isite.entity.Menu;
import me.ianhe.isite.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Cacheable("menu")
    public List<Menu> getAllMenu() {
        return menuMapper.getAllMenu();
    }

    public List<Menu> getMenusByUserId() {
        return menuMapper.getMenusByUserId(SystemUtils.getCurrentUser().getId());
    }

    public List<Menu> menuTree() {
        return menuMapper.menuTree();
    }

    public List<Long> getMenusByRid(Long rid) {
        return menuMapper.getMenusByRid(rid);
    }
}
