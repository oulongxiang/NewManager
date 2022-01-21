package com.tul.manage.security.service.impl;

import apps.commons.exception.ServiceException;
import apps.commons.util.enums.BizExceptionEnum;
import apps.commons.util.page.PageFactory;
import apps.commons.util.page.PageInfo;
import apps.commons.util.tool_util.VerifyParams;
import apps.commons.util.wrapper.map.MapListWrapper;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.ImmutableMap;
import com.tul.manage.security.config.LoginBean;
import com.tul.manage.security.dao.RoleDao;
import com.tul.manage.security.dao.UserInfoDao;
import com.tul.manage.security.dao.UserRoleDao;
import com.tul.manage.security.entity.Role;
import com.tul.manage.security.entity.UserInfo;
import com.tul.manage.security.entity.UserRole;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IUserInfoService;
import com.tul.manage.security.vo.response.UserInfoVo;
import com.tul.manage.warning.dto.TerritoryOrgDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 管理系统用户表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Service
@DS("Admin")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoDao, UserInfo> implements IUserInfoService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private IUserInfoService iUserInfoService;
    @Resource
    private UserRoleDao userRoleDao;


    @Override
    public Map<String, Object> authentication(LoginBean loginBean) {
        VerifyParams.throwEmpty(loginBean, "登录信息为空");
        VerifyParams.throwEmpty(loginBean.getAccountNumber(), "账号不能为空");
        VerifyParams.throwEmpty(loginBean.getLoginPassword(), "密码不能为空");
        //通过账号密码构建令牌
        UserInfoVo userInfoVo=baseMapper.getUserInfoVoToAccount(loginBean.getAccountNumber());
        if (userInfoVo == null) {
            throw new ServiceException(BizExceptionEnum.TOKEN_USEPWD_ERROR);
        }
        UserInfo userInfo=new UserInfo();
        BeanUtil.copyProperties(userInfoVo,userInfo);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userInfoVo.getId(), loginBean.getLoginPassword());
        //security通过UserDetails进行认证
        authenticationManager.authenticate(authenticationToken);
        //无异常,证明认证成功,创建令牌给前端，并增加登录次数
        LocalDateTime localDateTime=userInfo.getLoginTime();
        userInfo.setLoginTime(LocalDateTime.now());
        userInfo.setLoginCount(userInfo.getLoginCount()+1);
        updateById(userInfo);
        userInfo.setLoginTime(localDateTime);
        String token = createToken(userInfo.getId());
        return ImmutableMap.of("token", token,"userInfo",userInfoVo);
    }

    @Override
    public  void addUserInfoOut(UserInfo userInfo){
        VerifyParams.throwEmpty(userInfo.getUserName(), "用户名不能为空");
        VerifyParams.throwEmpty(userInfo.getIdCard(), "身份证号不能为空");
        VerifyParams.throwEmpty(userInfo.getMobile(), "手机号码不能为空");
        VerifyParams.throwEmpty(userInfo.getOrgId(), "组织区域不能为空");
        VerifyParams.throwEmpty(userInfo.getGender(), "性别不能为空");
        boolean valid = IdcardUtil.isValidCard(userInfo.getIdCard());
        //判断是否存在id来判断添加或修改
         if(userInfo.getId()==null) {
             //判断身份证号是否合法
             if (!valid) {
                 throw new ServiceException("请使用合法的身份证号");
             } else {
             //取出数据库里的有参数的手机号码
             int mobileCount = iUserInfoService.count(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getMobile, userInfo.getMobile()));
             //取出数据库的有参数的身份证号
             int idCardCount = iUserInfoService.count(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getIdCard,userInfo.getIdCard()));
             //判断数据库是否已有此手机号码
             if (mobileCount>=1) {
                 throw new ServiceException("请不要使用别人的手机号码");
             }
             //判断数据库是否已有此身份证号
             if (idCardCount>=1) {
                 throw new ServiceException("请不要使用别人的身份证号");
             }
             //取出数据库的最大的empId
                 UserInfo userInfoList = iUserInfoService.getOne(new QueryWrapper<UserInfo>().select("max(emp_id) as emp_id"));
                 userInfo.setAccountNumber(userInfo.getMobile());
                 userInfo.setLoginPassword(SecureUtil.md5("tul3933"));
                 userInfo.setIsLock(false);
                 userInfo.setLoginCount(0);
                 userInfo.setEmpId(userInfoList.getEmpId() + 1);
                 userInfo.setEmpIdHierarchy(getHierarchy(userInfo));
                 userInfo.setCreateUserId(getUserId());
                 iUserInfoService.save(userInfo);

             Role ordinaryUserRole = roleDao.selectOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleCode, RoleServiceImpl.ORDINARY_USER));
             if(ordinaryUserRole!=null){
                 UserRole userRole=new UserRole();
                 userRole.setRoleId(ordinaryUserRole.getId());
                 userRole.setUserId(userInfo.getId());
                 userRoleDao.insert(userRole);
             }
             }
         }else {
             //判断身份证号是否合法
             if (!valid) {
                 throw new ServiceException("请使用合法的身份证号");
             } else {
             //取出数据库里的有参数的手机号码不包含自己数据的列表
             int mobileCount = iUserInfoService.count(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getMobile,userInfo.getMobile()).ne(UserInfo::getId,userInfo.getId()));
             //取出数据库的有参数的身份证号不包含自己数据的列表
            int idCardCount = iUserInfoService.count(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getIdCard,userInfo.getIdCard()).ne(UserInfo::getId,userInfo.getId()));
             //判断数据库是否已有此手机号码
             if (mobileCount>=1) {
                 throw new ServiceException("请不要使用别人的手机号码");
             }
             //判断数据库是否已有此身份证号
             if (idCardCount>=1) {
                 throw new ServiceException("请不要使用别人的身份证号");
             }
                 userInfo.setAccountNumber(userInfo.getMobile());
                 userInfo.setUpdateUserId(getUserId());
                 iUserInfoService.saveOrUpdate(userInfo);
                 LambdaUpdateWrapper<UserInfo> wrapper = new LambdaUpdateWrapper<>();
                 if(userInfo.getLeaderEmpId()==null){
                     wrapper.set(UserInfo::getLeaderEmpId,null);
                 }
                 wrapper.eq(UserInfo::getId,userInfo.getId());
                 userInfo.setEmpIdHierarchy(getHierarchy(userInfo));
                 iUserInfoService.saveOrUpdate(userInfo,wrapper);
             }
         }
        refreshAllOrgTreeCache();
    }


    @Override
    public  void delUserInfoOut(String id){
        VerifyParams.checkEmpty(id, "删除选项的id不能为空");
        List<UserRole> userInfo=userRoleDao.selectList(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId,id));
        List<String> userRoleIdList = userInfo.stream().map(UserRole::getId).collect(Collectors.toList());
        userRoleDao.deleteBatchIds(userRoleIdList);
        baseMapper.deleteById(id);
        refreshUserCacheToUserId(id);
        refreshAllOrgTreeCache();
    }


    /**
     * 根据KK(智慧联邦)登录账号获取用户信息
     *
     * @param ssoCode KK(智慧联邦)登录账号
     * @return 用户信息
     */
    private UserInfoVo getUserInfoToSsoCode(String ssoCode) {
        VerifyParams.throwEmpty(ssoCode, "登录信息为空");
        UserInfoVo userInfoVo = baseMapper.getUserInfoVoToSsoCode(ssoCode);
        if (userInfoVo == null) {
            throw new ServiceException("8005", "无权限登录!,请联系管理员");
        }
        return userInfoVo;
    }

    @Override
    public PageInfo<UserInfoVo> getUserInfoList() {
        Map<String, Object> requestParam = getRequestParam();
        //取出选择的角色变成数组
        String roleNamesList = requestParam.containsKey("roleNames") ? requestParam.get("roleNames").toString() : null;
        String territory= requestParam.containsKey("territory") ? requestParam.get("territory").toString() : null;
        //判断数组是否为空
        if(roleNamesList!=null){
            List<String> roleNameList = Arrays.asList(roleNamesList.split(","));
            requestParam.put("roleNameList",roleNameList);
        }
        if(territory!=null){
            List<String> territoryList = Arrays.asList(territory.split(","));
            requestParam.put("territory",territoryList);
        }
        IPage<UserInfoVo> userInfoList = userInfoDao.getUserInfoList(PageFactory.getDefaultPage(), requestParam);
        PageInfo<UserInfoVo> userInfoVoPageInfo = new PageInfo<>(userInfoList);
        if(IterUtil.isEmpty(userInfoVoPageInfo.getData())){
            return userInfoVoPageInfo;
        }
        List<Map<String, String>> userRole = roleDao.getUserRole();

        for (UserInfoVo datum : userInfoVoPageInfo.getData()) {
            List<String> roleNames = new ArrayList<>();
            for (Map<String, String> stringStringMap : userRole) {
                if (datum.getId().equals(stringStringMap.get("userId"))) {
                    roleNames.add(stringStringMap.get("roleName"));
                }
            }
            datum.setRoleNames(roleNames.size() == 0 ? Collections.singletonList("无角色") : roleNames);
        }
        return userInfoVoPageInfo;
    }

    @Override
    public List<UserInfoVo> getUserListToRole(String roleId) {
        VerifyParams.throwEmpty(roleId, "无法获取角色信息");
        return baseMapper.getUserListToRole(roleId);
    }

    @Override
    public void updatePassword(Map<String, Object> data) {
        if (data == null) {
            throw new ServiceException("无法获取输入数据!");
        }
        if (data.get("password") == null) {
            throw new ServiceException("请输入原密码!");
        }
        if (data.get("newPassword") == null) {
            throw new ServiceException("请输入新密码!");
        }
        String password = data.get("password").toString();
        String newPassword = data.get("newPassword").toString();
        UserInfo userInfo = getById(getUserId());
        if (!password.equals(userInfo.getLoginPassword())) {
            throw new ServiceException("原密码错误!");
        }
        userInfo.setLoginPassword(newPassword);
        baseMapper.updateById(userInfo);
    }

    @Override
    public List<Tree<Integer>> getUserInfoTree(Integer empId) {
        //树配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 最大递归深度
        treeNodeConfig.setDeep(4);
        return TreeUtil.build(list(), empId, treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getEmpId());
                    tree.setParentId(treeNode.getLeaderEmpId());
                    tree.putExtra("userInfo", treeNode);
                });
    }


    @Override
    public void logout() {
        refreshUserCache();
    }

    /**
     * 获取用户上下级层级关系
     */
    private String getHierarchy(UserInfo userInfo){
        if(userInfo.getLeaderEmpId()==null){
            return userInfo.getEmpId().toString();
        }
        UserInfo leaderUser = getOne(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getEmpId, userInfo.getLeaderEmpId()));
        if(leaderUser==null){
            return userInfo.getEmpId().toString();
        }
        List<Integer> leaderHierarchy = Arrays.stream(leaderUser.getEmpIdHierarchy().split(",")).map(Integer::parseInt).collect(Collectors.toList());
        leaderHierarchy.add(userInfo.getEmpId());
        return leaderHierarchy.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    @Override
    public List<Map<String, Object>> bindSubordinateUserSelect() {
        List<UserInfo> subordinateUserList = getSubordinateUserList();
        return new MapListWrapper<>(subordinateUserList)
                .put(UserInfo::getId, "value")
                .put(UserInfo::getUserName, "label")
                .build();
    }

    /**
     * 根据用户ID获取用户上级ID
     * @param userId
     * @return
     */
    @Override
    public UserInfo getUserLeaderId(String userId) {
        VerifyParams.throwEmpty(userId, "userId为空");
        return userInfoDao.getUserLeaderId(userId);
    }
}