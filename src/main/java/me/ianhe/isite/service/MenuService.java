package me.ianhe.isite.service;

import me.ianhe.isite.dao.SysMenuMapper;
import me.ianhe.isite.entity.Menu;
import me.ianhe.isite.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
@Service
@Transactional
public class MenuService {

    @Autowired
    private SysMenuMapper menuMapper;

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
