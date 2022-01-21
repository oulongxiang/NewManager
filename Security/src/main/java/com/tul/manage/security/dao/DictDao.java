package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tul.manage.security.entity.Dict;
import com.tul.manage.security.vo.response.DictVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典表 Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-28
 */
@Mapper
@DS("Admin")
public interface DictDao extends BaseMapper<Dict> {

    /**
     * 获取数据字典分页列表
     * @param page 分页排序信息
     * @param queryParam 条件
     * @return 数据字典分页列表
     */
    IPage<DictVo> getDictList(Page<DictVo> page, @Param("queryParam") Map<String, Object> queryParam);

    /**
     * 根据类型名获取字典列表
     * @param typeName 类型名
     * @param order 排序
     * @return 字典列表
     */
    List<Map<String,Object>> getDictListToType(@Param("typeName")String typeName, @Param("order")Boolean order);

}