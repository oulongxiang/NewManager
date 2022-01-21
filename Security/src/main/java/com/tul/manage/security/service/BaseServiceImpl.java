package com.tul.manage.security.service;


import apps.commons.util.tool_util.HttpKit;
import apps.commons.util.tool_util.VerifyParams;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tul.manage.security.config.JwtUtil;
import com.tul.manage.security.config.LoginUserDetails;
import com.tul.manage.security.dao.RoleDao;
import com.tul.manage.security.dao.UserInfoDao;
import com.tul.manage.security.entity.Role;
import com.tul.manage.security.entity.UserInfo;
import com.tul.manage.security.enums.AnnexModuleEnum;
import com.tul.manage.security.enums.RoleEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description: 业务实现基类
 * @author: znegyu
 * @create: 2021-02-06 00:11
 **/
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private IAnnexService annexService;


    /**
     * 判断当前用户是否有某个角色
     *
     * @param roleEnum 角色
     * @return true-有 false-无
     */
    protected Boolean hasRole(RoleEnum roleEnum) {
        if (roleEnum == null) {
            return false;
        }
        String roleCode = "ROLE_" + roleEnum.getValue();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getAuthorities().contains(new SimpleGrantedAuthority(roleCode));
        }
        return false;
    }

    /**
     * 判断当前登录用户是否超级管理员
     *
     * @return true-是 false-否
     */
    public Boolean isAdmin() {
        return hasRole(RoleEnum.ADMIN);
    }

    /***
     * 获取当前登录用户的所有下级列表
     * @author zengyu
     * @date 2021年08月13日 13:59
     * @return  当前登录用户的所有下级列表
     */
    public List<UserInfo> getSubordinateUserList(){
        Integer empId = getUserInfo().getEmpId();
        if(empId==null){
            return new ArrayList<>();
        }else{
            return userInfoDao.getSubordinateUserList(empId);
        }
    }



    /**
     * 获取指定用户Id的所有角色列表
     *
     * @param userId 用户Id
     * @return 指定身份证用户所有角色列表
     */
    protected List<Role> getUserRoleList(String userId) {
        VerifyParams.throwEmpty(userId, "获取用户失败！");
        return roleDao.getRoleListInUserId(userId);
    }


    /**
     * 获取当前登录用户的用户Id
     *
     * @return 当前登录用户的用户Id
     */
    protected String getUserId() {
        String userId = jwtUtil.getUserId();
        if("无".equals(userId)){
            return null;
        }
        return userId;
    }

    /**
     * 清除权限相关的缓存
     */
    public void refreshPermissionCache() {
        redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("UserMoreInfoCacheInAuditSystem*")));
        redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("UserDetailsCacheInAuditSystem*")));
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息
     */
    public UserInfo getUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LoginUserDetails) {
            LoginUserDetails userDetails = (LoginUserDetails) principal;
            return userDetails.getUserInfo();
        }
        return userInfoDao.selectById(getUserId());
    }



    /**
     * 通过清除当前用户缓存(Token等信息,用于用户注销)
     */
    public void refreshUserCache() {
        String userId = getUserId();
        redisTemplate.delete("UserDetailsCacheInAuditSystem::" + userId);
        redisTemplate.delete("JWTCacheInAuditSystem::" + userId);
        redisTemplate.delete("JWTCacheInAuditSystem_LONG::" + userId);
    }

    /**
     * 清除指定用户id缓存(Token等信息,用于用户删除)
     */
    public void refreshUserCacheToUserId(String userId) {
        redisTemplate.delete("UserDetailsCacheInAuditSystem::" + userId);
        redisTemplate.delete("JWTCacheInAuditSystem::" + userId);
        redisTemplate.delete("JWTCacheInAuditSystem_LONG::" + userId);
    }


    /**
     * 清除组织用户树缓存
     */
    public void refreshOrgUserTreeCache() {
        redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("OrgUserTreeInAuditSystem*")));
    }


    /**
     * 清除组织树缓存
     */
    public void refreshOrgTreeCache() {
        redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("OrgTreeInAuditSystem*")));
    }


    /**
     * 清除拥有EmpId的用户组织树缓存
     */
    public void refreshOrgUserEmpIdTreeCache() {
        redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("OrgUserEmpIdTreeInAuditSystem*")));
    }

    /**
     * 清除组织相关缓存
     */
    public void refreshAllOrgTreeCache() {

    }


    /**
     * 通过userId构建 token
     *
     * @param userId 身份证
     * @return token
     */
    public String createToken(String userId){
        return jwtUtil.createToken(userId);
    }


    /**
     * 获取request参数 map
     *
     * @return request参数 map
     */
    protected Map<String, Object> getRequestParam() {
        //构建查询条件
        Map<String, Object> parameterMap = MapUtil.newHashMap();
        Objects.requireNonNull(HttpKit.getRequest()).getParameterMap().forEach((key, value) -> parameterMap.put(key, value[0]));
        return parameterMap;
    }

    /**
     * 上传指定模块的附件
     *
     * @param uploadFile    上传的文件
     * @param annexModule   附件所属模块
     * @param annexModuleId 模块id
     * @param isCompress    如果上传的为图片 是生成压缩图 true-是 false/null -否
     * @param sort          附件排序
     */
    protected void uploadAnnex(MultipartFile uploadFile, AnnexModuleEnum annexModule, String annexModuleId, boolean isCompress, int sort) {
        annexService.uploadAnnexToFile(uploadFile, annexModule, annexModuleId, isCompress, sort);
    }
    /**
     * 删除指定id的附件
     * @param annexId 附件id
     */
    protected void delAnnexFile(String annexId){
        annexService.delAnnexFile(annexId);
    }
}
