package com.tul.manage.security.service.impl;

import apps.commons.util.page.PageFactory;
import apps.commons.util.page.PageInfo;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.dao.ApiLogDao;
import com.tul.manage.security.entity.ApiLog;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IApiLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-06-21
 */
@Service
@DS("Admin")
public class ApiLogServiceImpl extends BaseServiceImpl<ApiLogDao, ApiLog> implements IApiLogService {

    @Override
    public PageInfo<ApiLog> getApiLogList(String param, String type, String responseState, LocalDateTime startTime, LocalDateTime endTime) {
        //根据条件查询apiLog日志列表的方法
        IPage<ApiLog> apiLogList = baseMapper.getApiLogList(PageFactory.getDefaultPage(), param, type, responseState, startTime, endTime);
        return new PageInfo<>(apiLogList);
    }

}