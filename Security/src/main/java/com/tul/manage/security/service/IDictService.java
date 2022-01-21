package com.tul.manage.security.service;


import apps.commons.util.page.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.entity.Dict;
import com.tul.manage.security.vo.response.DictVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-28
 */
public interface IDictService extends IService<Dict> {

    /**
     * 添加或修改字典
     * @param dict 所需数据
     */
    void addOrUpdateDict(Dict dict);

    /**
     * 删除字典
     * @param dictId 字典id
     */
    void delDict(String dictId);

    /**
     * 根据数据字典类型获取字典列表
     *
     * @param type 字典类型
     * @param order 排序 -true 升序 -false 降序
     * @return 字典列表
     */
    List<Map<String, Object>> getDictListToType(String type,Boolean order);

    /**
     * 获取数据字典分页列表
     *
     * @return
     */
    PageInfo<DictVo> getDictList();


    /**
     * 新增列表中绑定数据字典类型
     * @return 字典类型列表
     */
    List<Map<String,Object>> getDictAdd();
}
