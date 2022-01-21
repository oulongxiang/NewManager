package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tul.manage.security.entity.Icon;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 图标表(基于阿里图标库) Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-29
 */
@Mapper
@DS("Admin")
public interface IconDao extends BaseMapper<Icon> {

}