package com.tul.manage.security.controller;

import apps.commons.base.BaseController;
import apps.commons.tips.ResponseTip;
import apps.commons.tips.Tip;
import cn.hutool.core.lang.tree.Tree;
import com.tul.manage.security.entity.Element;
import com.tul.manage.security.entity.Menu;
import com.tul.manage.security.service.IElementService;
import com.tul.manage.security.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description: 菜单控制器
 * @author: znegyu
 * @create: 2021-01-20 09:52
 **/
@Api(value = "菜单相关接口", tags = "菜单接口")
@RestController
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {
    @Resource
    private IMenuService menuService;
    @Resource
    private IElementService elementService;

    @ApiOperation("添加菜单")
    @PostMapping("/addMenu")
    public Tip addMenu(@RequestBody Menu menu) {
        return tip(menuService.save(menu));
    }

    @ApiOperation("获取菜单列表")
    @GetMapping("/queryMenu")
    public ResponseTip<List<Tree<String>>> queryMenu(String menuType) {
        return successTip(menuService.getMenuList(menuType));
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("/delMenu")
    public Tip delMenu(String id) {
        menuService.delMenuById(id);
        return tip(true);
    }

    @ApiOperation("修改菜单")
    @PostMapping("/updateMenu")
    public Tip editMenu(@RequestBody Menu menu) {
        if (menu.getDescription() == null) {
            menu.setDescription("");
        }
        if (menu.getMenuUrl() == null) {
            menu.setMenuUrl("");
        }
        return tip(menuService.updateById(menu));
    }

    @ApiOperation("获取菜单元素列表")
    @GetMapping("/queryElement")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单id", paramType = "query", required = true)
    })
    public ResponseTip<List<Element>> queryElement(@RequestParam String menuId) {
        return successTip(elementService.getElementToMenuId(menuId));
    }

    @ApiOperation("添加菜单控件元素")
    @PostMapping("/addMenuElement")
    public Tip addMenuElement(@RequestBody Map<String, Object> element) {
        elementService.saveElement(element);
        return tip(true);
    }

    @ApiOperation("修改菜单控件元素")
    @PostMapping("/updateMenuElement")
    public Tip updateMenuElement(@RequestBody Element element) {
        if (element.getDescription() == null) {
            element.setDescription("");
        }
        return tip(elementService.updateById(element));
    }

    @ApiOperation("删除控件元素")
    @DeleteMapping("/delMenuElement")
    public Tip delMenuElement(String id) {
        elementService.delMenuElement(id);
        return tip(true);
    }

}
