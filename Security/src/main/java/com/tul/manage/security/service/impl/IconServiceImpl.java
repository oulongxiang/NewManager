package com.tul.manage.security.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.tul.manage.security.dao.IconDao;
import com.tul.manage.security.entity.Icon;
import com.tul.manage.security.enums.IconTypeEnum;
import com.tul.manage.security.service.BaseServiceImpl;
import com.tul.manage.security.service.IIconService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图标表(基于阿里图标库) 服务实现类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-29
 */
@Service
@DS("Admin")
public class IconServiceImpl extends BaseServiceImpl<IconDao, Icon> implements IIconService {

    @Cacheable(value = "IconInAuditSystem")
    @Override
    public List<Map<String, Object>> bindIconSelect() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Icon> list = list();
        for (IconTypeEnum iconType : IconTypeEnum.values()) {
            Map<String, Object> temp = MapUtil.newHashMap();
            List<Object> icons = new ArrayList<>();
            temp.put("label", iconType.toString());
            for (Icon icon : list) {
                if (icon.getIconType().equals(iconType)) {
                    icons.add(MapUtil.builder(new HashMap<String, Object>(2))
                            .put("label", icon.getIconName())
                            .put("value", icon.getIconCode()).build());
                }
            }
            temp.put("list", icons);
            result.add(temp);
        }
        return result;
    }
}