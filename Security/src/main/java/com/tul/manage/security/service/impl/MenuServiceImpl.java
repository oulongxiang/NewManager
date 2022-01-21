package com.tul.manage.security.service.impl;

import apps.commons.exception.ServiceException;
import apps.commons.util.tool_util.VerifyParams;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tul.manage.security.dao.*;
import com.tul.manage.security.entity.ElementPermission;
import com.tul.manage.security.entity.Menu;
import com.tul.manage.security.entity.MenuElement;
import com.tul.manage.security.enums.MenuTypeEnum;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Service
@DS("Admin")
public class MenuServiceImpl extends BaseServiceImpl<MenuDao, Menu> implements IMenuService {
    @Resource
    private MenuDao menuDao;
    @Resource
    private ElementDao elementDao;
    @Resource
    private ElementPermissionDao elementPermissionDao;
    @Resource
    private MenuElementDao menuElementDao;
    @Resource
    private RoleDao roleDao;
    @Override
    public List<Tree<String>> getLeftMenuList() {
        String userId =  getUserId();
        Boolean isAdmin = roleDao.isAdminToUserId(userId);
        if (isAdmin) {
            userId = null;
        }
        List<Menu> menuList = menuDao.getMenuList(userId, null);
        List<Map<String, Object>> userElements = elementDao.getUserElement(userId);
        //生成菜单树
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 最大递归深度,目前项目最多二级菜单,这里设置深度为3
        treeNodeConfig.setDeep(3);
        List<Tree<String>> treeNodes = TreeUtil.build(menuList, null, treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getMenuParentId());
                    tree.putExtra("label", treeNode.getMenuName());
                    tree.putExtra("path", treeNode.getMenuPath());
                    tree.putExtra("component", treeNode.getMenuUrl());
                    tree.putExtra("menuType", treeNode.getMenuType());
                    tree.putExtra("icon", treeNode.getIcon());
                    tree.putExtra("elementPermission", null);
                });
        //元素控件权限设置
        for (Tree<String> treeNode : treeNodes) {
            List<Map<String,String>> permission=new ArrayList<>();
            Map<String,Object> meta=MapUtil.newHashMap();
            MenuTypeEnum menuType =  (MenuTypeEnum)treeNode.get("menuType");
            if(menuType.equals(MenuTypeEnum.MOBILE)){
                meta.put("isMobile",true);
            }else{
                meta.put("isMobile",false);
            }
            setElementPower(userElements, treeNode, permission);
            meta.put("permission",permission);
            treeNode.putExtra("meta",meta);
            if (treeNode.getChildren() != null && treeNode.getChildren().size() != 0) {
                for (Tree<String> children : treeNode.getChildren()) {
                    MenuTypeEnum menuChildrenType =  (MenuTypeEnum)children.get("menuType");
                    List<Map<String,String>> permissionChildren=new ArrayList<>();
                    Map<String,Object> metaChildren=MapUtil.newHashMap();
                    if(menuChildrenType.equals(MenuTypeEnum.MOBILE)){
                        metaChildren.put("isMobile",true);
                    }else{
                        metaChildren.put("isMobile",false);
                    }
                    setElementPower(userElements, children, permissionChildren);
                    metaChildren.put("permission",permissionChildren);
                    children.putExtra("meta", metaChildren);
                }
            }
        }
        return treeNodes;
    }

    private void setElementPower(List<Map<String, Object>> userElements, Tree<String> children, List<Map<String, String>> ep) {
        for (Map<String, Object> userElement : userElements) {
            if (children.getId().equals(userElement.get("menuId").toString())) {
                ep.add(MapUtil.builder(new HashMap<String, String>()).put("code", userElement.get("elementCode").toString())
                        .put("name", userElement.get("elementName").toString()).build());
            }
        }
    }

    @Override
    public List<Map<String, Object>> getTopMenuList() {
        return new ArrayList<>();
//        return Arrays.asList(
//                MapUtil.builder(new HashMap<String, Object>())
//                        .put("label", "首页")
//                        .put("path", "/wel/index")
//                        .put("icon", "el-icon-document")
//                        .put("parentId", 0).build());
    }

    @Override
    public List<Tree<String>> getMenuList(String menuType) {
        LambdaQueryWrapper<Menu> queryWrapper = new QueryWrapper<Menu>()
                .orderByAsc("menu_parent_id,sort").lambda();
        if(menuType!=null){
            queryWrapper.eq(Menu::getMenuType, menuType);
        }
        List<Menu> menuList = list(queryWrapper);
        //生成菜单树
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 最大递归深度,目前项目最多二级菜单,这里设置深度为3
        treeNodeConfig.setDeep(3);
        return TreeUtil.build(menuList, null, treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getMenuParentId());
                    tree.putExtra("menuName", treeNode.getMenuName());
                    tree.putExtra("menuPath", treeNode.getMenuPath());
                    tree.putExtra("menuUrl", treeNode.getMenuUrl());
                    tree.putExtra("icon", treeNode.getIcon());
                    tree.putExtra("sort", treeNode.getSort());
                    tree.putExtra("description", treeNode.getDescription());
                    tree.putExtra("menuParentId", treeNode.getMenuParentId());
                });
    }

    @DS("Admin")
    @DSTransactional
    @Override
    public void delMenuById(String id) {
        VerifyParams.throwEmpty(id, "无法获取菜单信息");
        List<Menu> menuParentList = menuDao.selectList(new QueryWrapper<Menu>().lambda().eq(Menu::getMenuParentId, id));
        if (!IterUtil.isEmpty(menuParentList)) {
            throw new ServiceException("请先删除子菜单后再删除");
        }
        //先删除所有菜单关联的元素
        List<MenuElement> menuElements = menuElementDao.selectList(new QueryWrapper<MenuElement>().lambda().eq(MenuElement::getMenuId, id));
        for (MenuElement menuElement : menuElements) {
            elementDao.deleteById(menuElement.getElementId());
            //删除元素权限关联关系
            elementPermissionDao.delete(new QueryWrapper<ElementPermission>().lambda().eq(ElementPermission::getElementId, menuElement.getElementId()));
        }
        menuElementDao.delete(new QueryWrapper<MenuElement>().lambda().eq(MenuElement::getMenuId, id));
        menuDao.deleteById(id);
        //刷新缓存
        refreshPermissionCache();
    }
}