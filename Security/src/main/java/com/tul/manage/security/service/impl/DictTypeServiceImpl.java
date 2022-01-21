package com.tul.manage.security.service.impl;

import apps.commons.exception.ServiceException;
import apps.commons.util.page.PageInfo;
import apps.commons.util.tool_util.VerifyParams;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tul.manage.security.dao.DictDao;
import com.tul.manage.security.dao.DictTypeDao;
import com.tul.manage.security.dto.DictTypeDTO;
import com.tul.manage.security.entity.Dict;
import com.tul.manage.security.entity.DictType;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IDictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-11
 */
@Service
@DS("Admin")
public class DictTypeServiceImpl extends BaseServiceImpl<DictTypeDao, DictType> implements IDictTypeService {

    @Resource
    DictTypeDao dictTypeDao;

    @Resource
    DictDao dictDao;

    /**
     * 查询数据字典表头信息
     * @param current 当前页
     * @param size 每页容量
     * @param dictTypeDTO 传入查询参数
     * @return
     */
    @Override
    public PageInfo<DictType> getDictTypeList(long current, long size, DictTypeDTO dictTypeDTO) {
        //初始化分页
        Page<DictType> dictTypePage = new Page<>();
        dictTypePage.setCurrent(current);
        dictTypePage.setSize(size);
        //初始化DAO查询参数
        String name = dictTypeDTO.getName();
        String describe = dictTypeDTO.getDescribe();
        String explain = dictTypeDTO.getExplain();
        return new PageInfo<>(dictTypeDao.listDictTypeData(dictTypePage,name,explain,describe));
    }

    /**
     * 新增或者保存表头数据。注意点：1.通过传入ID有无判断调用新增还是保存操作 2.name不能重复需判断
     * @param dictType 传入保存或者新增参数
     */
    @Override
    public void addOrUpdateDictType(DictType dictType) {
        //传入参数为空判定
        VerifyParams.throwEmpty(dictType, "传入对象为空");
        VerifyParams.throwEmpty(dictType.getName(), "字典类型不能为空");
        VerifyParams.throwEmpty(dictType.getExplain(), "字典描述不能为空");
        //对象mark用来与传入对象做比较，如果两个对象的名字一致则不允许新增
        DictType mark = dictTypeDao.selectOne(new QueryWrapper<DictType>().lambda().eq(DictType::getName,
                dictType.getName()));
        //如果ID为NULL或者空,调用新增
        if(dictType.getId() == null || dictType.getId().length() == 0)
        {
            //存在同名规则不允许新增
            if(mark != null)
            {
                throw new ServiceException("数据库已存在同类型字典不允许新增");
            }
            else{
                dictTypeDao.insert(dictType);
            }

        }
        //反之调用保存
        else {
                dictTypeDao.updateById(dictType);
        }
    }

    /**
     * 删除数据需级联删除明细数据
     * @param id
     */
    @Override
    public void deleteDictType(String id) {
        //传入参数为空判定
        VerifyParams.throwEmpty(id, "删除接口，传入参数为空");
        //级联删除明细项数据
        if(dictDao.selectList(new QueryWrapper<Dict>().lambda().eq(Dict::getType,id)).size() > 0)
        {
            dictDao.delete(new QueryWrapper<Dict>().lambda().eq(Dict::getType,id));
        }

        dictTypeDao.deleteById(id);
    }
}