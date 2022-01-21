package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.entity.UserInfoLoginRecord;
import com.tul.manage.security.vo.response.LoginLogVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 登录记录表 Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-09
 */
@Mapper
@DS("Admin")
public interface UserInfoLoginRecordDao extends BaseMapper<UserInfoLoginRecord> {
    /**
     * 获取登录日志列表
     * @author zengyu
     * @date 2021年08月13日 09:00
     * @param page 分页排序信息
     * @param queryParams 查询条件集合
     * @return 分页排序后的登录日志列表
     */
    IPage<LoginLogVo> getLoginLogList(IPage<?> page, @Param("queryParams") Map<String, Object> queryParams);

}