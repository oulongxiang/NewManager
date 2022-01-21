package com.tul.manage.security.service;


import apps.commons.util.page.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.dto.DictTypeDTO;
import com.tul.manage.security.entity.DictType;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-11
 */
public interface IDictTypeService extends IService<DictType> {
    /**
     *  查询字典表头数据
     * @return
     */
    PageInfo<DictType> getDictTypeList(long current, long size, DictTypeDTO dictTypeDTO);

    /**
     * 新增或者保存字典表头数据
     * @param dictType
     */
    void addOrUpdateDictType(DictType dictType);

    /**
     *
     */
    void deleteDictType(String id);
}
