package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.entity.SystemLog;
import com.tul.manage.security.vo.response.SystemLogVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志表 Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-29
 */
@Mapper
@DS("Admin")
public interface SystemLogDao extends BaseMapper<SystemLog> {

    /**
     * 获取错误日志分页信息
     * @param page 分页信息
     * @param operatorName 操作人员姓名
     * @param beginDate -开始时间
     * @param errType 日志类型
     * @param endDate -结束时间
     * @return 错误日志分页信息
     */
    IPage<SystemLogVo> getSystemLogList(IPage<?> page,
                                        @Param("errType") Integer errType,
                                        @Param("operatorName") String operatorName,
                                        @Param("beginDate") LocalDateTime beginDate,
                                        @Param("endDate") LocalDateTime endDate);
    /**
     * 根据sql类型获取对应的操作sql日志分页信息
     * @param page -分页信息
     * @param operatorType 操作类型
     * @param operatorName 操作人员姓名
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return 操作sql日志分页信息
     */
    IPage<SystemLogVo> getSqlOperationList(IPage<?> page,
                                       @Param("operatorType") String operatorType,
                                       @Param("operatorName") String operatorName,
                                       @Param("beginDate") LocalDateTime beginDate,
                                       @Param("endDate") LocalDateTime endDate);


    /**
     * 用于定时任务,删除3个月前的日志
     */
    void taskDelSysLog();


}