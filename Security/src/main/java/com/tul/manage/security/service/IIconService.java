package com.tul.manage.security.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.entity.Icon;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图标表(基于阿里图标库) 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-29
 */
public interface IIconService extends IService<Icon> {

    /**
     * 获取图标列表
     *
     * @return 图标列表
     */
    List<Map<String, Object>> bindIconSelect();

}
