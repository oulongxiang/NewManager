package com.tul.manage.security.service;

import apps.commons.util.page.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.entity.Position;
import com.tul.manage.security.vo.response.PositionVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-06-26
 */
public interface IPositionService extends IService<Position> {

    /**
     * 添加类型为外部的岗位
     * @param positionvo 添加的实体类
     */
    void addOrUpdatePosition(PositionVo positionvo);


    /**
     * 删除类型为OUT的岗位
     * @param id
     */
    void delPosition(String id);


    /**
     * 根据查询岗位信息列表哦
     * @return 岗位信息列表
     */
    PageInfo<Position> getPositionList();


    /**
     * 查询所有的岗位列表
     * @return
     */
    List<Map<String,Object>> getPositionNameList();
}
