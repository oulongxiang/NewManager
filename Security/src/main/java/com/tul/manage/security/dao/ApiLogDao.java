package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.entity.ApiLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-06-21
 */
@Mapper
@DS("Admin")
public interface ApiLogDao extends BaseMapper<ApiLog> {

    /**
     * 获取apilog日志方法
     *
     * @param page          页面参数
     * @param param         json参数
     * @param type          api类型
     * @param responseState 是否成功
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return apiLog列表
     */
    IPage<ApiLog> getApiLogList(@Param("page") IPage<?> page, @Param("param") String param, @Param("type") String type, @Param("responseState") String responseState, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}