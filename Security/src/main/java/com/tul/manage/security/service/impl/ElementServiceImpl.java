package com.tul.manage.security.service.impl;

import apps.commons.exception.ServiceException;
import apps.commons.util.tool_util.VerifyParams;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tul.manage.security.dao.ElementDao;
import com.tul.manage.security.dao.MenuElementDao;
import com.tul.manage.security.entity.Element;
import com.tul.manage.security.entity.MenuElement;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IElementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 控件元素表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Service
@DS("Admin")
public class ElementServiceImpl extends BaseServiceImpl<ElementDao, Element> implements IElementService {

    @Resource
    private MenuElementDao menuElementDao;

    @Override
    public List<Element> getElementToMenuId(String menuId) {
        VerifyParams.throwEmpty(menuId, "无法获取菜单信息");
        return baseMapper.getElementToMenuId(menuId);
    }

    @DS("Admin")
    @DSTransactional
    @Override
    public void saveElement(Map<String, Object> element) {
        VerifyParams.throwEmpty(element, "无法获取元素信息");
        //判断是否重复
        if (baseMapper.isRepeat(element.get("menuId").toString(), element.get("elementCode").toString())) {
            throw new ServiceException("元素编码不能重复!");
        }
        Element addElement = new Element();
        addElement.setElementName(element.get("elementName").toString());
        addElement.setElementCode(element.get("elementCode").toString());
        addElement.setElementIcon(element.containsKey("elementIcon") ? element.get("elementIcon").toString() : null);
        addElement.setApiUri(element.containsKey("apiUri") ? element.get("apiUri").toString() : null);
        addElement.setDescription(element.containsKey("description") ? element.get("description").toString() : null);
        baseMapper.insert(addElement);
        MenuElement menuElement = new MenuElement();
        menuElement.setElementId(addElement.getId());
        menuElement.setMenuId(element.get("menuId").toString());
        menuElementDao.insert(menuElement);
        //刷新缓存
        refreshPermissionCache();
    }

    @DS("Admin")
    @DSTransactional
    @Override
    public void delMenuElement(String id) {
        VerifyParams.throwEmpty(id, "无法获取元素信息");
        //删除元素菜单关联表
        menuElementDao.delete(new QueryWrapper<MenuElement>().lambda().eq(MenuElement::getElementId, id));
        //删除元素表
        baseMapper.deleteById(id);
        //刷新缓存
        refreshPermissionCache();
    }
}