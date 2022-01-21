package com.tul.manage.security.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tul.manage.security.dao.RolePermissionDao;
import com.tul.manage.security.entity.RolePermission;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IRolePermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限关系表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Service
@DS("Admin")
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermissionDao, RolePermission> implements IRolePermissionService {

}