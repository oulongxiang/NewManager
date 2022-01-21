package com.tul.manage.plan.service.impl;

import apps.commons.util.page.PageFactory;
import apps.commons.util.page.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.plan.entity.AuditPlan;
import com.tul.manage.plan.dao.AuditPlanDao;
import com.tul.manage.plan.service.IAuditPlanService;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
/**
 * 审计计划主表
 *
 * @author Automatic Generation
 * @date 2022-01-10 14:54:41
 */
@Service
@DS("Admin")
public class AuditPlanServiceImpl extends BaseServiceImpl<AuditPlanDao, AuditPlan> implements IAuditPlanService {


    @Override
    public PageInfo<AuditPlan> queryAuditPlanInfoPageList() {
        IPage<AuditPlan> warningJobList = baseMapper.getAuditPlanInfoPageList(PageFactory.getDefaultPage(), getRequestParam());
        return new PageInfo<>(warningJobList);
    }
}
