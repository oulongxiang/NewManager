package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.entity.DictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 字典类型表 Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-11
 */
@Mapper
@DS("Admin")
public interface DictTypeDao extends BaseMapper<DictType> {
    IPage<DictType> listDictTypeData(IPage<?> page, @Param("name") String name, @Param("explain") String explain,
    @Param("describe") String describe);

}