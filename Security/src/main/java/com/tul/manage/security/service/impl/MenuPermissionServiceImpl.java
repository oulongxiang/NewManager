package com.tul.manage.security.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tul.manage.security.dao.MenuPermissionDao;
import com.tul.manage.security.entity.MenuPermission;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IMenuPermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单权限关系表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Service
@DS("Admin")
public class MenuPermissionServiceImpl extends BaseServiceImpl<MenuPermissionDao, MenuPermission> implements IMenuPermissionService {

}