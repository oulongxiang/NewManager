package com.tul.manage.security.service.impl;

import apps.commons.util.page.PageFactory;
import apps.commons.util.page.PageInfo;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.dao.SystemLogDao;
import com.tul.manage.security.entity.SystemLog;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.ISystemLogService;
import com.tul.manage.security.vo.response.SystemLogVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-29
 */
@Service
@DS("Admin")
public class SystemLogServiceImpl extends BaseServiceImpl<SystemLogDao, SystemLog> implements ISystemLogService {

    @Override
    public PageInfo<SystemLogVo> getSystemLogList(String operatorName, Integer errType, LocalDateTime beginDate, LocalDateTime endDate) {

        IPage<SystemLogVo> systemLogLists = baseMapper.getSystemLogList(PageFactory.getDefaultPage(),errType,operatorName,beginDate,endDate);
        return new PageInfo<>(systemLogLists);
    }

    @Override
    public PageInfo<SystemLogVo> getSqlOperationList(String operatorType, String operatorName, LocalDateTime beginDate, LocalDateTime endDate) {
        IPage<SystemLogVo> systemLogLists = baseMapper.getSqlOperationList(PageFactory.getDefaultPage(),operatorType,operatorName,beginDate,endDate);
        return new PageInfo<>(systemLogLists);
    }
}