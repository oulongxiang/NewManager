package com.tul.manage.security.service;


import apps.commons.util.page.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.entity.SystemLog;
import com.tul.manage.security.vo.response.SystemLogVo;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志表 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-29
 */
public interface ISystemLogService extends IService<SystemLog> {

    /**
     * 根据条件获取错误日志列表
     * @param operatorName 操作人员姓名
     * @param beginDate 开始时间
     * @param endDate 结束时间
     *   @param errType 日志类型
     * @return 错误日志列表
     */
    PageInfo<SystemLogVo> getSystemLogList(String operatorName, Integer errType,LocalDateTime beginDate, LocalDateTime endDate);


    /**
     * 获取操作日志信息列表
     * @param operatorType 操作类型
     * @param operatorName 操作人员姓名
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return 操作日志信息列表
     */
    PageInfo<SystemLogVo> getSqlOperationList(String operatorType,String operatorName, LocalDateTime beginDate, LocalDateTime endDate);

}
