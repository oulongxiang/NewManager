package com.tul.manage.security.service;


import apps.commons.util.page.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.entity.UserInfoLoginRecord;
import com.tul.manage.security.vo.response.LoginLogVo;

/**
 * <p>
 * 登录记录表 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-09
 */
public interface IUserInfoLoginRecordService extends IService<UserInfoLoginRecord> {
    /***
     * 获取登录日志
     * @author zengyu
     * @date 2021年08月13日 08:57
     * @return  登录日志分页列表
     */
    PageInfo<LoginLogVo> getLoginLogList();

}
