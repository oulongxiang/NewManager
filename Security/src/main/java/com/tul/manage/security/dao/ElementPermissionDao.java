package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tul.manage.security.entity.ElementPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 控件操作权限关系表 Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Mapper
@DS("Admin")
public interface ElementPermissionDao extends BaseMapper<ElementPermission> {

}