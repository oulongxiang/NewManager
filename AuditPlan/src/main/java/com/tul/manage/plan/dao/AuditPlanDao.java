package com.tul.manage.plan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tul.manage.plan.entity.AuditPlan;
import com.tul.manage.warning.vo.response.WarningJobVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 审计计划主表
 *
 * @author Automatic Generation
 * @date 2022-01-10 14:54:41
 */
@Mapper
@DS("Admin")
public interface AuditPlanDao extends BaseMapper<AuditPlan> {

    IPage<AuditPlan> getAuditPlanInfoPageList(IPage<?> page, @Param("queryParamMap") Map<String,Object> queryParamMap);
}
