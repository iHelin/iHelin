package me.ianhe.isite.controller.admin;

import com.google.common.collect.Maps;
import me.ianhe.isite.entity.SysMenuEntity;
import me.ianhe.isite.exception.RRException;
import me.ianhe.isite.service.SysMenuService;
import me.ianhe.isite.utils.Constant;
import me.ianhe.isite.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统菜单
 *
 * @author iHelin ihelin@outlook.com
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseAdminController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public R nav() {
        Integer userId = getUser().getId();
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(userId);
        Set<String> permissions = sysMenuService.getUserPermissions(userId);
        Map<Object, Object> resultMap = Maps.newHashMap();
        resultMap.put("menuList", menuList);
        resultMap.put("permissions", permissions);
        return R.ok().putData(resultMap);
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public List<SysMenuEntity> list() {
        List<SysMenuEntity> menuList = sysMenuService.list();
        for (SysMenuEntity sysMenuEntity : menuList) {
            SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }

        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @PreAuthorize("hasAuthority('sys:menu:select')")
    public R select() {
        //查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0);
        root.setName("一级菜单");
        root.setParentId(-1);
        root.setOpen(true);
        menuList.add(root);

        return R.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    @PreAuthorize("hasAuthority('sys:menu:info')")
    public R info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity menu = sysMenuService.getById(menuId);
        return R.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public R save(@RequestBody SysMenuEntity menu) {
        //数据校验
        verifyForm(menu);
        sysMenuService.saveMenu(menu);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public R update(@RequestBody SysMenuEntity menu) {
        //数据校验
        verifyForm(menu);
        sysMenuService.updateById(menu);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{menuId}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public R delete(@PathVariable("menuId") Integer menuId) {
        if (menuId <= 31) {
            return R.error("系统菜单，不能删除");
        }
        //判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = sysMenuService.getMenusByParentId(menuId);
        if (menuList.size() > 0) {
            return R.error("请先删除子菜单或按钮");
        }
        sysMenuService.delete(menuId);
        return R.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuEntity menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new RRException("菜单名称不能为空");
        }
        if (menu.getParentId() == null) {
            throw new RRException("上级菜单不能为空");
        }
        //菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new RRException("菜单URL不能为空");
            }
        }
        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new RRException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new RRException("上级菜单只能为菜单类型");
            }
        }
    }
}
