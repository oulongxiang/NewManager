package com.tul.manage.security.service;

import apps.commons.util.page.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.entity.ApiLog;

import java.time.LocalDateTime;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-06-21
 */
public interface IApiLogService extends IService<ApiLog> {

    /**
     * 根据条件获取apiLog日志列表
     *
     * @param param         json参数
     * @param type          api类型
     * @param responseState 是否成功
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return apiLog日志列表
     */
    PageInfo<ApiLog> getApiLogList(String param, String type, String responseState, LocalDateTime startTime, LocalDateTime endTime);


}
