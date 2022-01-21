package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tul.manage.security.entity.Menu;
import com.tul.manage.security.enums.MenuTypeEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Mapper
@DS("Admin")
public interface MenuDao extends BaseMapper<Menu> {
    /**
     * 根据用户Id和菜单类型证获取菜单列表
     *
     * @param userId -用户Id
     * @param menuType -菜单类型
     * @return 菜单列表
     */
    List<Menu> getMenuList(@Param("userId") String userId, @Param("menuType") MenuTypeEnum menuType);

    /**
     * 根据角色id获取菜单列表
     *
     * @param roleId -角色id
     * @return 菜单列表
     */
    List<Menu> getMenuListToRoleId(String roleId);

}