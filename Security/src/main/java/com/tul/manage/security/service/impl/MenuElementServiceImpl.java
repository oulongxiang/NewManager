package com.tul.manage.security.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tul.manage.security.dao.MenuElementDao;
import com.tul.manage.security.entity.MenuElement;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IMenuElementService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单元素关系表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Service
@DS("Admin")
public class MenuElementServiceImpl extends BaseServiceImpl<MenuElementDao, MenuElement> implements IMenuElementService {

}