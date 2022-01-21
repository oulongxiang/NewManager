package com.tul.manage.security.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tul.manage.security.dao.UserRoleDao;
import com.tul.manage.security.entity.UserRole;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Service
@DS("Admin")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleDao, UserRole> implements IUserRoleService {

}