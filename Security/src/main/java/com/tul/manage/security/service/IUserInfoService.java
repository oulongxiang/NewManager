package com.tul.manage.security.service;


import apps.commons.util.page.PageInfo;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.config.LoginBean;
import com.tul.manage.security.entity.UserInfo;
import com.tul.manage.security.vo.response.UserInfoVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理系统用户表 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
public interface IUserInfoService extends IService<UserInfo> {
    /**
     * 用户认证
     *
     * @param loginBean 认证(登录)所需参数
     * @return token
     */
    Map<String, Object> authentication(LoginBean loginBean);

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    UserInfo getUserInfo();

    /**
     * 用户注销
     */
    void logout();

    /**
     * 获取用户分页信息
     *
     * @return 用户分页信息
     */
    PageInfo<UserInfoVo> getUserInfoList();

    /**
     * 根据角色id 获取 用户数据
     *
     * @param roleId 角色id
     * @return 用户数据列表
     */
    List<UserInfoVo> getUserListToRole(String roleId);


    /**
     * 修改密码
     *
     * @param data 所需数据
     */
    void updatePassword(Map<String, Object> data);

    /**
     * 获取指定用户empId的下级关系树
     *
     * @param empId 要获取下级的用户empId
     * @return 下级关系树
     */
    List<Tree<Integer>> getUserInfoTree(Integer empId);


    /**
     * 新增或者修改外部员工
     * @param userInfo 新增的员工参数类
     */
    void addUserInfoOut(UserInfo userInfo);

    /**
     * 删除外部员工
     * @param id 员工id
     */
    void delUserInfoOut(String id);

    /***
     * 获取当前用户用于绑定下级选择框的数据
     * @author zengyu
     * @date 2021年08月13日 14:02
     * @return 当前用户用于绑定下级选择框的数据
     */
    List<Map<String, Object>> bindSubordinateUserSelect();


    /**
     * 根据用户ID获取用户上级ID
     * @param userId
     * @return
     */
    UserInfo getUserLeaderId(String userId);
}
