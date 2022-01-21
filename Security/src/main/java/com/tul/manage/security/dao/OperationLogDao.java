package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.dto.OperationLogDTO;
import com.tul.manage.security.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 操作日志表 Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-25
 */
@Mapper
@DS("Admin")
public interface OperationLogDao extends BaseMapper<OperationLog>{
    /**
     * 获取操作日志列表
     * @author zengyu
     * @date 2021年08月25日 09:00
     * @param page 分页排序信息
     * @param queryParams 查询条件集合
     * @return 分页排序后的操作日志列表
     */
    IPage<OperationLogDTO> getOperationLogList(IPage<?> page, @Param("queryParams") Map<String, Object> queryParams);

    /**
     * 用于定时任务,删除3个月前的操作日志
     */
    void taskDelSysLog();

}