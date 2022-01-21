package com.tul.manage.security.service.impl;

import apps.commons.exception.ServiceException;
import apps.commons.util.enums.EnumUtil;
import apps.commons.util.page.PageFactory;
import apps.commons.util.page.PageInfo;
import apps.commons.util.tool_util.HttpKit;
import apps.commons.util.tool_util.VerifyParams;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tul.manage.security.dao.DictDao;
import com.tul.manage.security.entity.Dict;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IDictService;
import com.tul.manage.security.vo.response.DictVo;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-28
 */
@Service
@DS("Admin")
public class DictServiceImpl extends BaseServiceImpl<DictDao, Dict> implements IDictService {

    @Resource
    DictDao dictDao;

    @Override
    public void addOrUpdateDict(Dict dict) {
        VerifyParams.throwEmpty(dict, "字典信息为空");
        VerifyParams.throwEmpty(dict.getType(), "字典类型不能为空");
        VerifyParams.throwEmpty(dict.getCode(), "字典代码不能为空");
        VerifyParams.throwEmpty(dict.getValue(), "字典实际值不能为空");
        //取到输入编码
        String markCode = dict.getCode();
        if(dictDao.selectList(new QueryWrapper<Dict>().lambda().eq(Dict::getCode,markCode)
                .eq(Dict::getType,dict.getType())).size() >= 1 && dict.getId() == null )
        {
            throw new ServiceException("字典编码不允许重复");
        }
        saveOrUpdate(dict);


    }

    @Override
    public void delDict(String dictId) {
        VerifyParams.throwEmpty(dictId, "字典信息为空");
        removeById(dictId);
    }

    @Override
    public List<Map<String, Object>> getDictListToType(String type,Boolean order) {
        VerifyParams.throwEmpty(type, "字典类型不能为空");
        if (!StrUtil.isBlank(type)) {
            List<Map<String, Object>> result = new ArrayList<>();
            List<Map<String, Object>> maps =baseMapper.getDictListToType(type,order);
            if(!IterUtil.isEmpty(maps)){
                maps.forEach(map -> result.add(MapUtil.builder(new HashMap<String, Object>())
                        .put("id", map.get("id"))
                        .put("code", isNum(map.get("code").toString()) ? Integer.valueOf(map.get("code").toString()) : map.get("code").toString())
                        .put("value", map.get("value")).build()));
                return result;
            }
            //无法通过数据表获取字典时，通过枚举获取
            List<Map<String,Class<?>>> enumMapList=new ArrayList<>();
            Reflections reflections = new Reflections("com.tul.manage.*");
            Set<Class<? extends IEnum>> monitorClasses = reflections.getSubTypesOf(IEnum.class);
            for (Class<? extends IEnum> monitorClass : monitorClasses) {
                Map<String,Class<?>> map=MapUtil.newHashMap();
                map.put(monitorClass.getSimpleName(),monitorClass);
                enumMapList.add(map);
            }
            for (Map<String,Class<?>> item : enumMapList) {
                if(item.containsKey(type)){
                    Class<?> enumClass = item.get(type);
                    return EnumUtil.getEnumMapList(enumClass);
                }
            }
            return result;
        }
        throw new ServiceException("字典数据获取失败!");
    }

    /**
     * 判断是否能将string转换为int
     *
     * @param str
     * @return
     */
    private boolean isNum(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public PageInfo<DictVo> getDictList() {
        Map<String, Object> parameterMap = MapUtil.newHashMap();
        Objects.requireNonNull(HttpKit.getRequest()).getParameterMap().forEach((key, value) -> {
            parameterMap.put(key, value[0]);
        });
        IPage<DictVo> dictList = getBaseMapper().getDictList(PageFactory.getDefaultPage(), parameterMap);
        return new PageInfo<>(dictList);
    }




    @Override
    public  List<Map<String,Object>> getDictAdd(){
        return baseMapper.selectMaps(new QueryWrapper<Dict>().select("distinct type,type_explain"));
    }
}