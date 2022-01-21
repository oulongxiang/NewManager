package com.tul.manage.security.service.impl;

import apps.commons.exception.ServiceException;
import apps.commons.util.page.PageFactory;
import apps.commons.util.page.PageInfo;
import apps.commons.util.tool_util.VerifyParams;
import cn.hutool.core.util.IdUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.dao.PositionDao;
import com.tul.manage.security.dao.UserInfoDao;
import com.tul.manage.security.entity.Position;
import com.tul.manage.security.entity.UserInfo;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IPositionService;
import com.tul.manage.security.vo.response.PositionVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-06-26
 */
@Service
@DS("Admin")
public class PositionServiceImpl extends BaseServiceImpl<PositionDao, Position> implements IPositionService {

    @Resource
    private IPositionService iPositionService;
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public  void addOrUpdatePosition(PositionVo positionvo){
        //判断组织机构的id是否存在，不存在就是添加方法，存在就是修改方法
        if(VerifyParams.isEmpty(positionvo.getId())){
        Position position = new Position();
            Position posNams =getOne(new QueryWrapper<Position>().lambda().eq(Position::getPostName,positionvo.getPostName()));
            if(posNams!=null){
                throw new ServiceException("你所添加的岗位名称重复，不允许添加！");
            }
            //生成唯一的岗位id
            String positionId= IdUtil.simpleUUID();
            position.setPostId(positionId);
            position.setPostName(positionvo.getPostName());
            position.setDescription(positionvo.getDescription());
            iPositionService.saveOrUpdate(position);
        }else{
            //修改方法，先根据id查询对应的组织机构
            Position positions=getOne(new QueryWrapper<Position>().lambda().eq(Position::getId,positionvo.getId()));
            List<Position> orgName = list(new QueryWrapper<Position>().lambda().eq(Position::getPostName,positionvo.getPostName()).ne(Position::getId,positionvo.getId()));
            if(orgName.size()>=1 ){
                throw new ServiceException("你所修改的岗位名称重复，不允许修改！");
            }
            //把需要修改的值放在查询出来的组织机构
            positions.setPostName(positionvo.getPostName());
            positions.setDescription(positionvo.getDescription());
            //进行修改
            iPositionService.saveOrUpdate(positions);
        }
        refreshAllOrgTreeCache();
    }

    @Override
    public  void delPosition(String PosId){
        VerifyParams.checkEmpty(PosId, "删除选项的id不能为空");
        //获取是否有使用此岗位的用户
        Position organization=getOne(new QueryWrapper<Position>().lambda().eq(Position::getId,PosId));
        List<UserInfo> outUser=userInfoDao.selectList(new QueryWrapper<UserInfo>().lambda().eq(UserInfo::getOrgId,organization.getPostId()));
        if(outUser.size()>0){
            throw new ServiceException("你所删除的岗位有用户正在使用，不允许删除！");
        }
        baseMapper.deleteById(PosId);
        refreshAllOrgTreeCache();
    }



    @Override
    public PageInfo<Position> getPositionList(){
        //查询条件
        Map<String, Object> requestParam = getRequestParam();
        //查询岗位信息列表
        IPage<Position> positionIPage = baseMapper.getPositionList(PageFactory.getDefaultPage(), requestParam);
        return new PageInfo<>(positionIPage);
    }


    @Override
    public List<Map<String,Object>> getPositionNameList(){
        return baseMapper.selectMaps(new QueryWrapper<Position>().select("DISTINCT post_id,post_name"));
    }

}