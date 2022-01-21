package com.tul.manage.security.service;


import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 获取当前用户授权的前端左侧菜单树
     * @return 当前用户授权的前端左侧菜单树
     */
    List<Tree<String>> getLeftMenuList();

    /**获取当前用户授权的前端顶端菜单树
     *
     * @return 当前用户授权的前端顶端菜单树
     */
    List<Map<String, Object>> getTopMenuList();

    /**
     * 获取菜单列表,用于系统中的菜单设置
     * @param menuType 菜单类型
     * @return 菜单列表
     */
    List<Tree<String>> getMenuList(String menuType);

    /**
     * 删除菜单
     * @param id 菜单id
     */
    void delMenuById(String id);

}
