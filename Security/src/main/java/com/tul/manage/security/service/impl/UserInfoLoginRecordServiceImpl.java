package com.tul.manage.security.service.impl;

import apps.commons.util.page.PageFactory;
import apps.commons.util.page.PageInfo;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.tul.manage.security.dao.UserInfoLoginRecordDao;
import com.tul.manage.security.entity.UserInfoLoginRecord;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IUserInfoLoginRecordService;
import com.tul.manage.security.vo.response.LoginLogVo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 登录记录表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-09
 */
@Service
@DS("Admin")
public class UserInfoLoginRecordServiceImpl extends BaseServiceImpl<UserInfoLoginRecordDao, UserInfoLoginRecord> implements IUserInfoLoginRecordService {

    @Override
    public PageInfo<LoginLogVo> getLoginLogList() {
        return new PageInfo<>(baseMapper.getLoginLogList(PageFactory.getDefaultPage(),getRequestParam()));
    }
}