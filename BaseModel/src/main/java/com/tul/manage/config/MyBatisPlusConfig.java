package com.tul.manage.config;

import apps.commons.util.RequestDataHelper;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 对象功能 MyBatisPlus配置
 * 开发人员：曾煜
 * 创建时间：2020/8/11 23:16
 * </pre>
 *
 * @author zengyu
 */
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.tul.generator.controller"})
@Configuration
@Slf4j
public class MyBatisPlusConfig implements MetaObjectHandler {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //設置請求的頁面大於最大頁後操作，true調回到首頁，false繼續請求，default為false
        paginationInterceptor.setOverflow(true);
        //最大單頁限制數量，default為500，-1表示不受限制
        paginationInterceptor.setLimit(1000);
        return paginationInterceptor;
    }


    /**
     * 自动填充创建时间和逻辑删除
     *
     * @param metaObject - metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "isDelete", Boolean.class, false);
        this.strictInsertFill(metaObject, "isDeleted", Boolean.class, false);
        this.strictInsertFill(metaObject, "deleted", Boolean.class, false);
    }

    /**
     * 自动填充修改时间
     *
     * @param metaObject - metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Bean
    public MybatisInterceptor idInterceptor() {
        return new MybatisInterceptor();
    }


}
