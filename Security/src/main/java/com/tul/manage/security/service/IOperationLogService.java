package com.tul.manage.security.service;

import apps.commons.util.page.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.dto.OperationLogDTO;
import com.tul.manage.security.entity.OperationLog;

/**
 * <p>
 * 操作日志表 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-25
 */
public interface IOperationLogService extends IService<OperationLog> {

    /***
     * 获取操作日志
     * @author zengyu
     * @date 2021年08月25日 08:57
     * @return  操作日志分页列表
     */
    PageInfo<OperationLogDTO> getOperationLogList();

}
