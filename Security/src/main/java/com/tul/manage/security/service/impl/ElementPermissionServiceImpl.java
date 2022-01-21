package com.tul.manage.security.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tul.manage.security.dao.ElementPermissionDao;
import com.tul.manage.security.entity.ElementPermission;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IElementPermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 控件操作权限关系表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Service
@DS("Admin")
public class ElementPermissionServiceImpl extends BaseServiceImpl<ElementPermissionDao, ElementPermission> implements IElementPermissionService {

}