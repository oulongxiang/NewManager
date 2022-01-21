package com.tul.manage.security.service.impl;

import apps.commons.exception.ServiceException;
import apps.commons.util.page.PageFactory;
import apps.commons.util.tool_util.VerifyParams;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.dao.*;
import com.tul.manage.security.entity.*;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IRoleService;
import com.tul.manage.security.service.IUserRoleService;
import com.tul.manage.security.vo.request.OrgUserRoleVo;
import com.tul.manage.security.vo.request.RoleVo;
import com.tul.manage.security.vo.request.UserRoleVo;
import com.tul.manage.security.vo.response.UserRoleListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Service
@DS("Admin")
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, Role> implements IRoleService {

    /**
     * 超级管理员角色编码
     */
    public static final String ADMIN="admin";
    /**
     * 普通角色编码
     */
    public static final String ORDINARY_USER="OrdinaryUser";
    @Resource
    private RolePermissionDao rolePermissionDao;
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private IUserRoleService userRoleService;

    @Override
    public void addRole(RoleVo role) {
        // 校验
        VerifyParams.throwEmpty(role, "无法获取角色信息");
        VerifyParams.throwEmpty(role.getRoleCode(), "无法获取角色编码");
        VerifyParams.checkStrings(role.getRoleName(), "roleName", 1, 20, false);
        // 检查数据库是否存在该角色名
        Role reRole = getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleName, role.getRoleName()));
        if(reRole!=null){
            throw new ServiceException("角色【{1}】已存在", role.getRoleName());
        }
        reRole=getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleCode,role.getRoleCode()));
        if(reRole!=null){
            throw new ServiceException("角色编码【{1}】已存在", role.getRoleCode());
        }
        Role addRole = new Role();
        addRole.setRoleName(role.getRoleName());
        addRole.setRoleCode(role.getRoleCode());
        addRole.setDescription(role.getDescription());
        //刷新缓存
        refreshPermissionCache();
        save(addRole);
    }

    @Override
    public IPage<Role> getRoleList(String roleName) {
        LambdaQueryWrapper<Role> query = new QueryWrapper<Role>().lambda();
        if (!StrUtil.isEmpty(roleName)) {
            query.like(Role::getRoleName, roleName);
        }
        return page(PageFactory.getDefaultPage(),query);
    }

    @Override
    public void delRole(String id) {
        VerifyParams.throwEmpty(id, "无法获取角色信息");
        Role role = getById(id);
        if (ADMIN.equals(role.getRoleCode())) {
            throw new ServiceException("无法删除超级管理员");
        }
        if (ORDINARY_USER.equals(role.getRoleCode())) {
            throw new ServiceException("无法删除普通角色");
        }
        //删除角色权限关联表数据
        rolePermissionDao.delete(new QueryWrapper<RolePermission>().eq("role_id", id));
        //删除用户角色关联表数据
        userRoleDao.delete(new QueryWrapper<UserRole>().eq("role_id", id));
        //删除角色
        removeById(id);
        //刷新缓存
        refreshPermissionCache();
    }

    @Override
    public void updateRole(Role role) {
        VerifyParams.throwEmpty(role, "无法获取角色信息");
        VerifyParams.throwEmpty(role.getRoleName(), "角色名称不能为空");
        VerifyParams.throwEmpty(role.getRoleCode(), "角色编码不能为空");
        if (ADMIN.equals(role.getRoleCode())) {
            throw new ServiceException("无法修改超级管理员");
        }
        if (ORDINARY_USER.equals(role.getRoleCode())) {
            throw new ServiceException("无法修改普通角色");
        }
        Role roleRe = getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleName, role.getRoleName()));
        if(roleRe!=null && role.getRoleName().equals(roleRe.getRoleName()) && !role.getId().equals(roleRe.getId())){
            throw new ServiceException("角色名重复");
        }
        roleRe = getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleCode, role.getRoleCode()));
        if(roleRe!=null && role.getRoleCode().equals(roleRe.getRoleCode()) && !role.getId().equals(roleRe.getId())){
            throw new ServiceException("角色编码重复");
        }
        updateById(role);
        //刷新缓存
        refreshPermissionCache();
    }

    @Override
    public void addUserToRole(UserRoleVo userRoleVo) {
        VerifyParams.throwEmpty(userRoleVo, "无法获取添加的用户");
        VerifyParams.throwEmpty(userRoleVo.getRoleId(), "无法获取角色信息");
        VerifyParams.throwEmpty(userRoleVo.getUserId(), "无法获取添加的用户");
        UserInfo userInfo =userInfoDao.selectById(userRoleVo.getUserId());
        List<UserRole> userRoles = userRoleDao.selectList(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, userInfo.getId()));
        userRoles.forEach(item -> {
            if (userRoleVo.getRoleId().equals(item.getRoleId())) {
                throw new ServiceException("该用户已有此角色!");
            }
        });
        //添加用户到角色
        UserRole userRole = new UserRole();
        userRole.setUserId(userInfo.getId());
        userRole.setRoleId(userRoleVo.getRoleId());
        userRoleDao.insert(userRole);
        //刷新缓存
        refreshPermissionCache();
    }

    @Override
    public void addOrgUserListToRole(OrgUserRoleVo orgUserRoleVo) {
        VerifyParams.throwEmpty(orgUserRoleVo.getRoleId(), "无法获取角色信息");
        Role role = getById(orgUserRoleVo.getRoleId());
        VerifyParams.throwEmpty(role, "无法获取角色信息");

        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();

        //获取用户
        List<UserInfo> userInfoList = userInfoDao.selectList(new LambdaQueryWrapper<>());
        VerifyParams.throwEmpty(userInfoList, "该组织下无用户");
        List<UserRole> userRoleList = userRoleDao.selectList(new QueryWrapper<UserRole>().lambda().eq(UserRole::getRoleId, orgUserRoleVo.getRoleId()));
        Map<String, String> userRoleMap = userRoleList.stream().collect(Collectors.toMap(UserRole::getUserId, UserRole::getRoleId));
        List<UserRole> addData=new ArrayList<>();
        for (UserInfo userInfo : userInfoList) {
            if(!userRoleMap.containsKey(userInfo.getId())){
                UserRole userRole = new UserRole();
                userRole.setUserId(userInfo.getId());
                userRole.setRoleId(orgUserRoleVo.getRoleId());
                addData.add(userRole);
            }
        }
        userRoleService.saveBatch(addData);
        //刷新缓存
        refreshPermissionCache();
    }


    @Override
    public void delUserRole(UserRoleVo userRoleVo) {
        VerifyParams.throwEmpty(userRoleVo, "无法获取此角色要删除的用户");
        VerifyParams.throwEmpty(userRoleVo.getRoleId(), "无法获取角色信息");
        VerifyParams.throwEmpty(userRoleVo.getUserId(), "无法获取此角色要删除的用户");
        UserInfo userInfo =userInfoDao.selectById(userRoleVo.getUserId());
        //删除用户角色关联表数据
        userRoleDao.delete(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, userInfo.getId()).eq(UserRole::getRoleId, userRoleVo.getRoleId()));
        //刷新缓存
        refreshPermissionCache();
    }

    @Override
    public List<UserRoleListVo> getRoleListToUser(String userId) {
        VerifyParams.throwEmpty(userId, "无法获取用户信息");
        return baseMapper.getRoleListToUser(userId);
    }

    @Override
    public List<Map<String,Object>> bindRole(){
        return baseMapper.selectMaps(new QueryWrapper<Role>().select("id,role_name").lambda().eq(Role::getDeleted,0));
    }


    @Override
    public List<UserRoleListVo> getAllRoleList() {
        return baseMapper.getAllRoleList();
    }
}