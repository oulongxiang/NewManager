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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tul.manage.security.dao.*;
import com.tul.manage.security.entity.*;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IPermissionService;
import com.tul.manage.security.vo.request.RoleMenuVo;
import com.tul.manage.security.vo.request.RolePermissionVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Service
@DS("Admin")
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDao, Permission> implements IPermissionService {
    @Resource
    private MenuDao menuDao;
    @Resource
    private ElementDao elementDao;
    @Resource
    private RolePermissionDao rolePermissionDao;
    @Resource
    private ElementPermissionDao elementPermissionDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private MenuPermissionDao menuPermissionDao;
    @Resource
    private MenuElementDao menuElementDao;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public List<Tree<String>> getPermissionListToRoleId(String roleId) {
        VerifyParams.throwEmpty(roleId, "无法获取角色信息");
        //该角色可见的菜单列表
        List<Menu> menuList = menuDao.getMenuListToRoleId(roleId);
        //该角色拥有的元素控件权限
        List<Map<String, String>> roleElements = elementDao.getElementListToRoleId(roleId);
        //获取角色可见菜单中的所有元素控件
        List<Map<String, String>> roleAllElements = elementDao.getElementAllListToRoleId(roleId);
        //生成菜单树
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 最大递归深度,目前项目最多二级菜单,这里设置深度为3
        treeNodeConfig.setDeep(3);
        List<Tree<String>> treeNodes = TreeUtil.build(menuList, null, treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getMenuParentId());
                    tree.putExtra("menuName", treeNode.getMenuName());
                    tree.putExtra("elementPermission", null);
                });
        //元素控件权限设置
        for (Tree<String> treeNode : treeNodes) {
            List<String> parentCheckbox = new ArrayList<>();
            List<Map<String, Object>> parentEp = new ArrayList<>();
            for (Map<String, String> s : roleAllElements) {
                if (treeNode.getId().equals(s.get("menuId"))) {
                    parentEp.add(MapUtil.builder(new HashMap<String, Object>()).put("code", s.get("elementCode"))
                            .put("label", s.get("elementName"))
                            .put("value", s.get("elementId"))
                            .build());
                    for (Map<String, String> s1 : roleElements) {
                        if (s.get("elementId").equals(s1.get("elementId"))) {
                            parentCheckbox.add(s1.get("elementId"));
                        }
                    }
                }
                treeNode.putExtra("elements", MapUtil.builder(new HashMap<String, Object>()).put("permission", parentEp)
                        .put("checkbox", parentCheckbox).build());
            }
            if (treeNode.getChildren() != null && treeNode.getChildren().size() != 0) {
                for (Tree<String> children : treeNode.getChildren()) {
                    List<Map<String, Object>> ep = new ArrayList<>();
                    List<String> checkbox = new ArrayList<>();
                    for (Map<String, String> userElement : roleAllElements) {
                        if (children.getId().equals(userElement.get("menuId"))) {
                            ep.add(MapUtil.builder(new HashMap<String, Object>()).put("code", userElement.get("elementCode"))
                                    .put("label", userElement.get("elementName"))
                                    .put("value", userElement.get("elementId"))
                                    .build());
                            for (Map<String, String> s : roleElements) {
                                if (userElement.get("elementId").equals(s.get("elementId"))) {
                                    checkbox.add(userElement.get("elementId"));
                                }
                            }
                        }
                    }
                    children.putExtra("elements", MapUtil.builder(new HashMap<String, Object>()).put("permission", ep)
                            .put("checkbox", checkbox).build());
                }
            }
        }
        return treeNodes;
    }

    @Override
    public void updateRolePermission(RolePermissionVo rolePermissionVo) {
        VerifyParams.throwEmpty(rolePermissionVo, "无法获取角色信息");
        VerifyParams.throwEmpty(rolePermissionVo.getRoleId(), "无法获取角色信息");
        if(IterUtil.isEmpty(rolePermissionVo.getSelectElementIds())){
            return;
        }
        //根据角色id 获取权限id
        RolePermission rolePermission = rolePermissionDao.selectOne(new QueryWrapper<RolePermission>().lambda()
                .eq(RolePermission::getRoleId, rolePermissionVo.getRoleId()));
        //删除未勾选的权限
        elementPermissionDao.delete(new QueryWrapper<ElementPermission>().lambda()
                .eq(ElementPermission::getPermissionId, rolePermission.getPermissionId())
                .notIn(ElementPermission::getElementId, rolePermissionVo.getSelectElementIds()));
        //添加勾选的权限
        //筛选出之前已有的,且在勾选列表的权限
        List<ElementPermission> hasElementPermissions = elementPermissionDao.selectList(new QueryWrapper<ElementPermission>().lambda()
                .eq(ElementPermission::getPermissionId, rolePermission.getPermissionId()).
                 in(ElementPermission::getElementId, rolePermissionVo.getSelectElementIds()));
        //筛选出需要新添加的权限列表
        List<String> needAdd = new ArrayList<>(rolePermissionVo.getSelectElementIds());
        for (String selectElementId : rolePermissionVo.getSelectElementIds()) {
            for (ElementPermission hasElementPermission : hasElementPermissions) {
                if (selectElementId.equals(hasElementPermission.getElementId())) {
                    needAdd.remove(hasElementPermission.getElementId());
                }
            }
        }
        //添加新权限
        for (String elementId : needAdd) {
            ElementPermission elementPermission = new ElementPermission();
            elementPermission.setElementId(elementId);
            elementPermission.setPermissionId(rolePermission.getPermissionId());
            elementPermissionDao.insert(elementPermission);
        }
        refreshPermissionCache();
    }

    @DS("Admin")
    @DSTransactional
    @Override
    public void addRolePermission(RoleMenuVo roleMenuVo) {
        VerifyParams.throwEmpty(roleMenuVo, "无法获取角色信息");
        VerifyParams.throwEmpty(roleMenuVo.getRoleId(), "无法获取角色信息");
        VerifyParams.throwEmpty(roleMenuVo.getMenuId(), "无法获取菜单信息");
        //判断该角色是否已有该权限
        if (permissionDao.hasPermissionToRoleMenu(roleMenuVo.getRoleId(), roleMenuVo.getMenuId())) {
            throw new ServiceException("该角色已有此权限,无需再次添加!");
        }
        //判断该角色权限是否为空
        RolePermission rolePermission = rolePermissionDao.selectOne(new QueryWrapper<RolePermission>().lambda()
                .eq(RolePermission::getRoleId, roleMenuVo.getRoleId()));
        Role role = roleDao.selectById(roleMenuVo.getRoleId());
        Permission permission = new Permission();
        if (rolePermission == null) {
            //为空时,新建权限
            permission.setDescription(role.getRoleName() + "的权限");
            save(permission);
            RolePermission addrolePermission = new RolePermission();
            addrolePermission.setRoleId(role.getId());
            addrolePermission.setPermissionId(permission.getId());
            rolePermissionDao.insert(addrolePermission);
        } else {
            permission = getById(rolePermission.getPermissionId());
        }
        //设置权限关联关系
        MenuPermission menuPermission = new MenuPermission();
        menuPermission.setPermissionId(permission.getId());
        menuPermission.setMenuId(roleMenuVo.getMenuId());
        menuPermissionDao.insert(menuPermission);
        refreshPermissionCache();
    }

    @DS("Admin")
    @DSTransactional
    @Override
    public void delRolePermission(RoleMenuVo roleMenuVo) {
        VerifyParams.throwEmpty(roleMenuVo, "无法获取角色信息");
        VerifyParams.throwEmpty(roleMenuVo.getRoleId(), "无法获取角色信息");
        VerifyParams.throwEmpty(roleMenuVo.getMenuId(), "无法获取菜单信息");
        //判断是否有子菜单
        Menu menu = menuDao.selectById(roleMenuVo.getMenuId());
        List<Menu> menuChildrenList = menuDao.selectList(new QueryWrapper<Menu>().lambda().eq(Menu::getMenuParentId, menu.getId()));
        if (!IterUtil.isEmpty(menuChildrenList)) {
            //删除角色子菜单元素和子菜单
            for (Menu menuChildren : menuChildrenList) {
                delRoleMenu(menuChildren.getId(), roleMenuVo.getRoleId());
            }
        } else {
            delRoleMenu(roleMenuVo.getMenuId(), roleMenuVo.getRoleId());
        }
        refreshPermissionCache();
    }

    @DS("Admin")
    @DSTransactional
    private void delRoleMenu(String menuId, String roleId) {
        VerifyParams.throwEmpty(menuId, "无法获取菜单信息");
        VerifyParams.throwEmpty(roleId, "无法获取角色信息");
        //删除菜单元素关联数据
        RolePermission rolePermission = rolePermissionDao.selectOne(new QueryWrapper<RolePermission>().lambda()
                .eq(RolePermission::getRoleId, roleId));
        List<MenuElement> menuElements = menuElementDao.selectList(new QueryWrapper<MenuElement>().lambda()
                .eq(MenuElement::getMenuId, menuId));
        for (MenuElement menuElement : menuElements) {
            elementPermissionDao.delete(new QueryWrapper<ElementPermission>().lambda()
                    .eq(ElementPermission::getElementId, menuElement.getElementId())
                    .eq(ElementPermission::getPermissionId, rolePermission.getPermissionId()));
        }
        //删除菜单权限关联数据
        menuPermissionDao.delete(new QueryWrapper<MenuPermission>().lambda()
                .eq(MenuPermission::getPermissionId, rolePermission.getPermissionId())
                .eq(MenuPermission::getMenuId, menuId));
    }
}