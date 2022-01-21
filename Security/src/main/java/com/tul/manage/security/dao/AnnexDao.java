package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tul.manage.security.entity.Annex;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-07-01
 */
@Mapper
@DS("Admin")
public interface AnnexDao extends BaseMapper<Annex> {

}