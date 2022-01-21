package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tul.manage.security.entity.Element;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 控件元素表 Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Mapper
@DS("Admin")
public interface ElementDao extends BaseMapper<Element> {
    /**
     * 根据用户Id获取用户可操作元素
     *
     * @param userId 用户Id
     * @return 待处理权限列表
     */
    List<Map<String, Object>> getUserElement(String userId);

    /**
     * 根据菜单id获取控件元素列表
     *
     * @param menuId 菜单id
     * @return 控件元素列表
     */
    List<Element> getElementToMenuId(String menuId);

    /**
     * 根据角色id获取其可见菜单有权限的控件元素列表
     *
     * @param roleId -角色id
     * @return 控件元素相关信息
     */
    List<Map<String, String>> getElementListToRoleId(String roleId);

    /**
     * 根据角色id获取其可见菜单所有的控件元素列表
     *
     * @param roleId -角色id
     * @return 控件元素相关信息
     */
    List<Map<String, String>> getElementAllListToRoleId(String roleId);

    /**
     * 判断菜单元素是否重复
     *
     * @param menuId      菜单id
     * @param elementCode 元素code
     * @return true-重复 false-不重复
     */
    Boolean isRepeat(@Param("menuId") String menuId, @Param("elementCode") String elementCode);

}