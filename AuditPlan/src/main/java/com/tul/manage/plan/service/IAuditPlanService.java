package com.tul.manage.plan.service;

import apps.commons.util.page.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.plan.entity.AuditPlan;

/**
 * 审计计划主表
 *
 * @author Automatic Generation
 * @date 2022-01-10 14:54:41
 */
public interface IAuditPlanService extends IService<AuditPlan> {

    /**
     * 获取审计计划信息
     *
     * @return 预警作业信息
     * @author wuyaobin
     * @date 2021/8/4
     */
    PageInfo<AuditPlan> queryAuditPlanInfoPageList();

}
