package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tul.manage.security.entity.UserInfo;
import com.tul.manage.security.vo.request.OrgUserTreeVo;
import com.tul.manage.security.vo.response.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 金鹊管理系统用户表 Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Mapper
@DS("Admin")
public interface UserInfoDao extends BaseMapper<UserInfo> {

    /**
     * 获取分页的用户信息列表
     *
     * @param page  -分页参数
     * @param queryParam -查询条件
     * @return 分页数据
     */
    IPage<UserInfoVo> getUserInfoList(Page<UserInfoVo> page, @Param("queryParam") Map<String, Object> queryParam);

    /**
     * 获取用户角色关系
     *
     * @return 用户角色关系map 列表
     */
    List<Map<String, String>> getUserRole();

    /**
     * 根据角色id获取用户列表
     * @param roleId 角色id
     * @return 用户列表
     */
    List<UserInfoVo> getUserListToRole(String roleId);

    /**
     * 获取构建组织用户树所需数据
     * @return 构建组织用户树所需数据
     */
    List<OrgUserTreeVo> getOrgUser();

    /**
     * 通过账号获取用户vo
     * @param accountNumber 账号
     * @return 用户vo
     */
    UserInfoVo getUserInfoVoToAccount(String accountNumber);

    /**
     * 通过ssoCode获取用户vo
     * @param ssoCode KK账号
     * @return 用户vo
     */
    UserInfoVo getUserInfoVoToSsoCode(String ssoCode);

    /**
     * 获取指定empId的所有下级列表
     * @param empId 要获取下级的empId
     * @return 下级列表
     */
    List<UserInfo> getSubordinateUserList(@Param("empId") Integer empId );

    /**
     * 根据用户ID获取用户上级ID
     * @param userId
     * @return
     */
    UserInfo getUserLeaderId(@Param("userId") String userId);
}