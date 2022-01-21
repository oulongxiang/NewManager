package com.tul.generator.controller;

import apps.commons.base.BaseController;
import apps.commons.tips.ResponseTip;
import apps.commons.util.page.PageInfo;
import com.tul.generator.support.DbGenUtil;
import com.tul.manage.security.config.IgnoreOperationLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *  代码生成器API
 * @author zengyu
 * @date 2021-09-01 14:10
 **/
@IgnoreOperationLog
@Api(value = "代码生成器接口", tags = "代码生成器接口")
@RestController
@RequestMapping("/admin/gen")
public class GenCodeController extends BaseController {

    @ApiOperation(value = "获取所有数据源名", notes = "获取所有数据源名", httpMethod = "GET")
    @GetMapping("/getDbNameList")
    public ResponseTip<List<String>> getDbNameList() {
        return successTip(DbGenUtil.getDbNameList());
    }

    @ApiOperation(value = "获取指定数据源的数据表", notes = "获取指定数据源的数据表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dbName", value = "数据源名", paramType = "query", required = true),
            @ApiImplicitParam(name = "tableName", value = "表名(模糊查)", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", paramType = "query")
    })
    @GetMapping("/getTableList")
    public PageInfo<Map<String,String>> getTableList(String dbName, String tableName) {
        return DbGenUtil.getTableList(dbName,tableName);
    }

    @ApiOperation(value = "获取指定数据表的列信息", notes = "获取指定数据表的列信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dbName", value = "数据源名", paramType = "query", required = true),
            @ApiImplicitParam(name = "tableName", value = "表名", paramType = "query", required = true)
    })
    @GetMapping("/getColumnList")
    public ResponseTip<List<Map<String, String>>> getColumnList(String dbName,String tableName){
        return successTip(DbGenUtil.getColumnList(dbName,tableName));
    }

    @ApiOperation(value = "预览代码", notes = "预览代码", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dbName", value = "数据源名", paramType = "query", required = true),
            @ApiImplicitParam(name = "tableName", value = "表名", paramType = "query", required = true),
            @ApiImplicitParam(name = "author", value = "作者", paramType = "query", required = false),
            @ApiImplicitParam(name = "packageName", value = "包名", paramType = "query", required = false),
            @ApiImplicitParam(name = "moduleName", value = "模块名", paramType = "query", required = false),
            @ApiImplicitParam(name = "tablePrefix", value = "表前缀", paramType = "query", required = false),
            @ApiImplicitParam(name = "comments", value = "注释", paramType = "query", required = false),
            @ApiImplicitParam(name = "style", value = "风格", paramType = "query", required = false),
    })
    @GetMapping("/previewCode")
    public ResponseTip<Map<String,String>> previewCode(String dbName,String tableName,String author,String packageName,String moduleName,String tablePrefix,String comments,String style) {
        return successTip(DbGenUtil.previewCode(dbName,tableName,author,packageName,moduleName,tablePrefix,comments,style));
    }

    @ApiOperation(value = "下载代码", notes = "下载代码", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dbName", value = "数据源名", paramType = "query", required = true),
            @ApiImplicitParam(name = "tableName", value = "表名", paramType = "query", required = true),
            @ApiImplicitParam(name = "author", value = "作者", paramType = "query", required = false),
            @ApiImplicitParam(name = "packageName", value = "包名", paramType = "query", required = false),
            @ApiImplicitParam(name = "moduleName", value = "模块名", paramType = "query", required = false),
            @ApiImplicitParam(name = "tablePrefix", value = "表前缀", paramType = "query", required = false),
            @ApiImplicitParam(name = "comments", value = "注释", paramType = "query", required = false),
            @ApiImplicitParam(name = "style", value = "风格", paramType = "query", required = false),
    })
    @GetMapping("/downloadCode")
    public void downloadCode(String dbName,String tableName,String author,String packageName,String moduleName,String tablePrefix,String comments,String style){
        DbGenUtil.downloadCode(dbName,tableName,author,packageName,moduleName,tablePrefix,comments,style);
    }

    @ApiOperation(value = "批量下载代码", notes = "下载代码", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dbName", value = "数据源名", paramType = "query", required = true),
            @ApiImplicitParam(name = "tableNames", value = "表名列表,逗号隔开", paramType = "query", required = true),
            @ApiImplicitParam(name = "author", value = "作者", paramType = "query", required = false),
            @ApiImplicitParam(name = "packageName", value = "包名", paramType = "query", required = false),
            @ApiImplicitParam(name = "moduleName", value = "模块名", paramType = "query", required = false),
            @ApiImplicitParam(name = "tablePrefix", value = "表前缀", paramType = "query", required = false),
            @ApiImplicitParam(name = "comments", value = "注释", paramType = "query", required = false),
            @ApiImplicitParam(name = "style", value = "风格", paramType = "query", required = false),
    })
    @GetMapping("/batchDownloadCode")
    public void batchDownloadCode(String dbName,String tableNames,String author,String packageName,String moduleName,String tablePrefix,String comments,String style){
        DbGenUtil.batchDownloadCode(dbName,Arrays.asList(tableNames.split(",")),author,packageName,moduleName,tablePrefix,comments,style);
    }


}
