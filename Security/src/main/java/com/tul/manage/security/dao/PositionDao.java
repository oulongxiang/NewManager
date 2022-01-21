package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.entity.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-06-26
 */
@Mapper
@DS("Admin")
public interface PositionDao extends BaseMapper<Position> {

    /**
     * 根据条件获取岗位信息列表
     * @param page 分页排序等信息
     * @param requestParam 搜索条件
     * @return 岗位信息列表
     */
    IPage<Position> getPositionList(@Param("page") IPage<?> page, @Param("query") Map<String, Object> requestParam);
}