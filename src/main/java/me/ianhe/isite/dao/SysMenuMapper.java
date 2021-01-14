package me.ianhe.isite.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.ianhe.isite.entity.Menu;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/27 20:42
 */
public interface SysMenuMapper extends BaseMapper<Menu> {

    //    @Cacheable("menu")
    List<Menu> getAllMenu();

    List<Menu> getMenusByUserId(Long uid);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);
}
