package com.tul.manage.security.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.entity.Element;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 控件元素表 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
public interface IElementService extends IService<Element> {
    /**
     * 根据菜单id获取控件元素列表
     *
     * @param menuId 菜单id
     * @return 控件元素列表
     */
    List<Element> getElementToMenuId(String menuId);

    /**
     * 添加菜单控件元素
     *
     * @param element 所需参数
     */
    void saveElement(Map<String, Object> element);


    /**
     * 删除控件元素
     * @param id 要删除的控件元素id
     */
    void delMenuElement(String id);

}
