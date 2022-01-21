package com.tul.manage.security.service.impl;

import apps.commons.util.page.PageFactory;
import apps.commons.util.page.PageInfo;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.tul.manage.security.dao.OperationLogDao;
import com.tul.manage.security.dto.OperationLogDTO;
import com.tul.manage.security.entity.OperationLog;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IOperationLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-25
 */
@Service
@DS("Admin")
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLogDao, OperationLog> implements IOperationLogService {
    @Override
    public PageInfo<OperationLogDTO> getOperationLogList() {
        return new PageInfo<>(baseMapper.getOperationLogList(PageFactory.getDefaultPage(),getRequestParam()));
    }
}