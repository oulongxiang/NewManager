package com.tul.manage.security.controller;


import apps.commons.base.BaseController;
import apps.commons.tips.ResponseTip;
import apps.commons.tips.Tip;
import apps.commons.util.page.PageInfo;
import com.tul.manage.security.config.IgnoreOperationLog;
import com.tul.manage.security.dto.DictTypeDTO;
import com.tul.manage.security.entity.Dict;
import com.tul.manage.security.entity.DictType;
import com.tul.manage.security.service.IDictService;
import com.tul.manage.security.service.IDictTypeService;
import com.tul.manage.security.vo.response.DictVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-01-28
 */
@Api(value = "数据字典相关接口", tags = "数据字典接口")
@RestController
@RequestMapping("/admin/dict")
public class DictController extends BaseController {

    @Resource
    private IDictService dictService;

    @Resource
    private IDictTypeService iDictTypeService;

    @ApiOperation("添加或修改数据字典明细数据")
    @PostMapping("/addOrUpdateDict")
    public Tip addOrUpdateDict(@RequestBody Dict dict) {
        dictService.addOrUpdateDict(dict);
        return tip(true);
    }

    @ApiOperation("删除数据字典")
    @DeleteMapping("/delDict")
    public Tip delDict(String dictId) {
        dictService.delDict(dictId);
        return tip(true);
    }

    @IgnoreOperationLog
    @ApiOperation("根据数据字典类型或枚举名获取字典列表")
    @GetMapping("/getDictListToType")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "字典类型或枚举名称", paramType = "query", required = true),
            @ApiImplicitParam(name = "order", value = "排序 true-升序 false-降序", paramType = "query")
    })
    public ResponseTip<Object> getDictListToType(@RequestParam String type,@RequestParam(required = false) Boolean order) {
        return successTip(dictService.getDictListToType(type,order));
    }

    @ApiOperation("根据条件获取字典分页列表")
    @GetMapping("/getDictList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页个数", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "字段类型", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "字典编码", paramType = "query"),
            @ApiImplicitParam(name = "typeExplain", value = "字典类型说明", paramType = "query"),
    })
    public PageInfo<DictVo> getDictList() {
        return dictService.getDictList();
    }


    @ApiOperation("获取数据字典表头信息")
    @GetMapping("/listDictType")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页个数", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "类型", paramType = "query"),
            @ApiImplicitParam(name = "explain", value = "备注", paramType = "query"),
            @ApiImplicitParam(name = "describe", value = "描述", paramType = "query"),
    })
    public PageInfo<DictType> listDictType(Long currentPage, Long pageSize, DictTypeDTO dictTypeDTO ){
        return iDictTypeService.getDictTypeList(currentPage,pageSize,dictTypeDTO);
    }

    @ApiOperation("添加或修改数据字典表头数据")
    @PostMapping("/addOrUpdateDictType")
    public Tip addOrUpdateDictType(@RequestBody DictType dictType) {
        iDictTypeService.addOrUpdateDictType(dictType);
        return tip(true);
    }

    @ApiOperation("删除数据字典表头数据")
    @DeleteMapping("/delDictType")
    public Tip delDictType(String id) {
        iDictTypeService.deleteDictType(id);
        return tip(true);
    }

}

