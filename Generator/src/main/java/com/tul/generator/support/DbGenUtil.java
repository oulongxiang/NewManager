package com.tul.generator.support;

import apps.commons.util.page.PageFactory;
import apps.commons.util.page.PageInfo;
import apps.commons.util.tool_util.HttpKit;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tul.generator.entity.GenConfig;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 *  数据库查询工具类
 * @author zengyu
 * @date 2021-09-01 09:34
 **/
public class DbGenUtil {

    /***
     * 生成代码
     * @author zengyu
     * @date 2021年09月01日 14:06
     * @param dbConfName 读取的配置名
     * @param tableName 表名
     * @param packageName 包名
     * @param author 作者
     * @param moduleName  模块名
     * @param tablePrefix  表前缀
     * @param comments  注释
     * @param style  风格
     * @return  根据模板生成的代码
     */
    public static Map<String, String> previewCode(String dbConfName,String tableName,String author,String packageName,String moduleName,String tablePrefix,String comments,String style){
        if(StrUtil.isBlank(dbConfName) || StrUtil.isBlank(tableName)){
            throw new CheckedException("获取数据表失败");
        }
        DatabaseConfigBean databaseConfigBean =getDatabaseConfig(dbConfName);
        GenConfig genConfig=new GenConfig();
        genConfig.setAuthor(StrUtil.isBlank(author)?databaseConfigBean.getAuthor():author);
        genConfig.setPackageName(StrUtil.isBlank(packageName)?databaseConfigBean.getParent():packageName);
        genConfig.setModuleName(StrUtil.isBlank(moduleName)?databaseConfigBean.getModuleName():moduleName);
        genConfig.setTablePrefix(StrUtil.isBlank(tablePrefix)?databaseConfigBean.getTablePrefix():tablePrefix);
        genConfig.setComments(StrUtil.isBlank(comments)?databaseConfigBean.getComments():comments);
        genConfig.setStyle(style);
        genConfig.setDataSourceName(databaseConfigBean.getDataSourceName());
        genConfig.setLogicDelete(databaseConfigBean.getLogicDelete());
        genConfig.setLogicDeleteFieldName(databaseConfigBean.getLogicDeleteFieldName());
        genConfig.setCreateTimeAuto(databaseConfigBean.getCreateTimeAuto());
        genConfig.setCreateTimeFieldName(databaseConfigBean.getCreateTimeFieldName());
        genConfig.setUpdateTimeAuto(databaseConfigBean.getUpdateTimeAuto());
        genConfig.setUpdateTimeFieldName(databaseConfigBean.getUpdateTimeFieldName());
        // 查询表信息
        Map<String, String> table =  queryTable(databaseConfigBean, tableName,false,null).get(0);
        // 查询列信息
        List<Map<String, String>> columns = queryColumns(databaseConfigBean, tableName);
        // 生成代码
        return CodeGenKits.generatorCode(genConfig, table, columns, null, null);
    }

    /***
     * 下载代码 zip
     * @author zengyu
     * @date 2021年09月01日 14:06
     * @param dbConfName 读取的配置名
     * @param tableName 表名
     * @param packageName 包名
     * @param author 作者
     * @param moduleName  模块名
     * @param tablePrefix  表前缀
     * @param comments  注释
     * @param style  风格
     * @return  zip
     */
    public static void downloadCode(String dbConfName,String tableName,String author,String packageName,String moduleName,String tablePrefix,String comments,String style){
        if(StrUtil.isBlank(dbConfName) || StrUtil.isBlank(tableName)){
            throw new CheckedException("获取数据表失败");
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        DatabaseConfigBean databaseConfigBean =getDatabaseConfig(dbConfName);
        GenConfig genConfig=new GenConfig();
        genConfig.setAuthor(StrUtil.isBlank(author)?databaseConfigBean.getAuthor():author);
        genConfig.setDataSourceName(databaseConfigBean.getDataSourceName());
        genConfig.setLogicDelete(databaseConfigBean.getLogicDelete());
        genConfig.setLogicDeleteFieldName(databaseConfigBean.getLogicDeleteFieldName());
        genConfig.setCreateTimeAuto(databaseConfigBean.getCreateTimeAuto());
        genConfig.setPackageName(StrUtil.isBlank(packageName)?databaseConfigBean.getParent():packageName);
        genConfig.setModuleName(StrUtil.isBlank(moduleName)?databaseConfigBean.getModuleName():moduleName);
        genConfig.setCreateTimeFieldName(databaseConfigBean.getCreateTimeFieldName());
        genConfig.setTablePrefix(StrUtil.isBlank(tablePrefix)?databaseConfigBean.getTablePrefix():tablePrefix);
        genConfig.setComments(StrUtil.isBlank(comments)?databaseConfigBean.getComments():comments);
        genConfig.setStyle(style);
        genConfig.setUpdateTimeAuto(databaseConfigBean.getUpdateTimeAuto());
        genConfig.setUpdateTimeFieldName(databaseConfigBean.getUpdateTimeFieldName());
        // 查询表信息
        Map<String, String> table =  queryTable(databaseConfigBean, tableName,false,null).get(0);
        // 查询列信息
        List<Map<String, String>> columns = queryColumns(databaseConfigBean, tableName);
        // 生成代码
        CodeGenKits.generatorCode(genConfig, table, columns, zip, null);
        IoUtil.close(zip);
        byte[] data = outputStream.toByteArray();
        HttpServletResponse response = HttpKit.getResponse();
        assert response != null;
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/octet-stream");
        response.setHeader("Access-Control-Expose-Headers", "fileName");
        String fileName=tableName+".zip";
        try {
            response.setHeader("fileName", URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(data.length));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            bufferedOutputStream.write(data);
            response.flushBuffer();
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 批量下载代码 zip
     * @author zengyu
     * @date 2021年09月01日 14:06
     * @param dbConfName 读取的配置名
     * @param tableNameList 表名集合
     * @param packageName 包名
     * @param author 作者
     * @param moduleName  模块名
     * @param tablePrefix  表前缀
     * @param comments  注释
     * @param style  风格
     * @return  zip
     */
    public static void batchDownloadCode(String dbConfName,List<String> tableNameList,String author,String packageName,String moduleName,String tablePrefix,String comments,String style){
        if(StrUtil.isBlank(dbConfName) || IterUtil.isEmpty(tableNameList)){
            throw new CheckedException("获取数据表失败");
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        DatabaseConfigBean databaseConfigBean =getDatabaseConfig(dbConfName);
        GenConfig genConfig=new GenConfig();
        genConfig.setAuthor(StrUtil.isBlank(author)?databaseConfigBean.getAuthor():author);
        genConfig.setDataSourceName(databaseConfigBean.getDataSourceName());
        genConfig.setLogicDelete(databaseConfigBean.getLogicDelete());
        genConfig.setLogicDeleteFieldName(databaseConfigBean.getLogicDeleteFieldName());
        genConfig.setCreateTimeAuto(databaseConfigBean.getCreateTimeAuto());
        genConfig.setPackageName(StrUtil.isBlank(packageName)?databaseConfigBean.getParent():packageName);
        genConfig.setModuleName(StrUtil.isBlank(moduleName)?databaseConfigBean.getModuleName():moduleName);
        genConfig.setCreateTimeFieldName(databaseConfigBean.getCreateTimeFieldName());
        genConfig.setTablePrefix(StrUtil.isBlank(tablePrefix)?databaseConfigBean.getTablePrefix():tablePrefix);
        genConfig.setComments(StrUtil.isBlank(comments)?databaseConfigBean.getComments():comments);
        genConfig.setStyle(style);
        genConfig.setUpdateTimeAuto(databaseConfigBean.getUpdateTimeAuto());
        genConfig.setUpdateTimeFieldName(databaseConfigBean.getUpdateTimeFieldName());
        for (String tableName : tableNameList) {
            // 查询表信息
            Map<String, String> map = queryTable(databaseConfigBean, tableName,false,null).get(0);
            // 查询列信息
            List<Map<String, String>> columns = queryColumns(databaseConfigBean, tableName);
            // 生成代码
            CodeGenKits.generatorCode(genConfig, map, columns, zip, null);
        }
        IoUtil.close(zip);
        byte[] data = outputStream.toByteArray();
        HttpServletResponse response = HttpKit.getResponse();
        assert response != null;
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/octet-stream");
        response.setHeader("Access-Control-Expose-Headers", "fileName");
        String fileName="code.zip";
        try {
            response.setHeader("fileName", URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(data.length));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            bufferedOutputStream.write(data);
            response.flushBuffer();
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取所有数据源名
     * @author zengyu
     * @date 2021年09月01日 14:27
     * @return 数据源名列表
     */
    public static List<String> getDbNameList(){
        PropertiesConfiguration propertiesConfiguration = null;
        try {
            propertiesConfiguration = new PropertiesConfiguration("config.properties");
            String[] configList = propertiesConfiguration.getStringArray("configList");
            return Arrays.asList(configList);
        } catch (ConfigurationException e) {
            return new ArrayList<>();
        }
    }

    /***
     * 获取指定数据源的数据表
     * @author zengyu
     * @date 2021年09月01日 14:42
     * @param dbName 数据源名
     * @param tableName 表名
     * @return 指定数据源的数据表
     */
    public static PageInfo<Map<String,String>> getTableList(String dbName, String tableName){
        DatabaseConfigBean databaseConfig = getDatabaseConfig(dbName);
        Page<Map<String, String>> page = PageFactory.getDefaultPage();
        List<Map<String, String>> maps = queryTable(databaseConfig, tableName, true,page );
        page.setRecords(maps);
        return new PageInfo<>(page);
    }

    /***
     * 获取指定数据表的列信息
     * @author zengyu
     * @date 2021年09月02日 08:38
     * @param dbName 数据源名
     * @param tableName 表名
     * @return  指定数据源数据表的列信息
     */
    public static List<Map<String, String>> getColumnList(String dbName,String tableName){
        DatabaseConfigBean databaseConfig = getDatabaseConfig(dbName);
        return queryColumns(databaseConfig,tableName);
    }

    /***
     * 读取指定数据库配置
     * @author zengyu
     * @date 2021年09月01日 13:47
     * @param configName 数据库名/配置名
     * @return 数据库对象
     */
    private static DatabaseConfigBean getDatabaseConfig(String configName){
        PropertiesConfiguration propertiesConfiguration;
        DatabaseConfigBean databaseConfigBean = new DatabaseConfigBean();
        try {
            propertiesConfiguration = new PropertiesConfiguration("dynamicDatasource/config-" + configName + ".properties");
            databaseConfigBean.setUrl(propertiesConfiguration.getString("url"));
            databaseConfigBean.setDriverName(propertiesConfiguration.getString("driverName"));
            databaseConfigBean.setDataSourceName(propertiesConfiguration.getString("dataSourceName"));
            databaseConfigBean.setUserName(propertiesConfiguration.getString("userName"));
            databaseConfigBean.setPassword(propertiesConfiguration.getString("password"));
            databaseConfigBean.setDbType(propertiesConfiguration.getString("dbType"));
            databaseConfigBean.setDaoPackageName(propertiesConfiguration.getString("daoPackageName"));
            databaseConfigBean.setServicePackageName(
                    propertiesConfiguration.getString("servicePackageName"));
            databaseConfigBean.setServiceImplPackageName(
                    propertiesConfiguration.getString("serviceImplPackageName"));
            databaseConfigBean.setEntityPackageName(
                    propertiesConfiguration.getString("entityPackageName"));
            databaseConfigBean.setAuthor(propertiesConfiguration.getString("author"));
            databaseConfigBean.setComments(propertiesConfiguration.getString("comments"));
            databaseConfigBean.setTablePrefix(propertiesConfiguration.getString("tablePrefix"));
            databaseConfigBean.setOpenLombok(propertiesConfiguration.getBoolean("openSwagger2"));
            databaseConfigBean.setOpenLombok(propertiesConfiguration.getBoolean("openLombok"));
            databaseConfigBean.setRestController(
                    propertiesConfiguration.getBoolean("restController"));
            databaseConfigBean.setParent(propertiesConfiguration.getString("parent"));
            databaseConfigBean.setModuleName(propertiesConfiguration.getString("moduleName"));
            databaseConfigBean.setMapperName(propertiesConfiguration.getString("mapperName"));
            databaseConfigBean.setLogicDelete(propertiesConfiguration.getBoolean("logicDelete"));
            databaseConfigBean.setLogicDeleteFieldName(
                    propertiesConfiguration.getString("logicDeleteFieldName"));
            databaseConfigBean.setCreateTimeAuto(
                    propertiesConfiguration.getBoolean("createTimeAuto"));
            databaseConfigBean.setCreateTimeFieldName(
                    propertiesConfiguration.getString("createTimeFieldName"));
            databaseConfigBean.setUpdateTimeAuto(
                    propertiesConfiguration.getBoolean("updateTimeAuto"));
            databaseConfigBean.setUpdateTimeFieldName(
                    propertiesConfiguration.getString("updateTimeFieldName"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return databaseConfigBean;
    }

    /***
     * 获取所有数据库配置
     * @author zengyu
     * @date 2021年09月01日 13:48
     * @return 数据库对象列表
     */
    private static List<DatabaseConfigBean> getDatabaseConfigList() {
        List<DatabaseConfigBean> databaseConfigBeanList = new ArrayList<>();
        PropertiesConfiguration propertiesConfiguration;
        try {
            propertiesConfiguration = new PropertiesConfiguration("config.properties");
            String[] configList = propertiesConfiguration.getStringArray("configList");
            List<String> configNameList = new ArrayList<>();
            for (String configName : configList) {
                configNameList.add("dynamicDatasource/config-" + configName + ".properties");
            }
            for (String configName : configNameList) {
                databaseConfigBeanList.add(getDatabaseConfig(configName));
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return databaseConfigBeanList;
    }

    /***
     * 获取指定数据库查询数据表的SQL
     * @author zengyu
     * @date 2021年09月01日 08:50
     * @param tableName 指定表名 如果为null 则查所有
     * @param isLike 是否模糊查
     * @param dbTypeEnum 数据库类型
     * @param page 分页
     * @return 查询数据表的SQL 如果有分页 则 index=0 为普通sql index=1 为查总数sql
     */
    private static List<String> queryTableSql(String tableName,DbTypeEnum dbTypeEnum,Boolean isLike,Page<?> page){
        String sql="";
        String countSql="";
        if(dbTypeEnum==DbTypeEnum.MYSQL){
            sql="select \n" +
                    "table_name as tableName, \n" +
                    "table_comment as tableComment, \n" +
                    "create_time as createTime \n" +
                    "from information_schema.tables\n" +
                    "where table_schema = (select database()) ";
            countSql="select count(1) as num from information_schema.tables where table_schema = (select database()) ";
            if(StrUtil.isNotBlank(tableName) && isLike){
                sql+="and table_name like '%"+tableName+"%'";
                countSql+="and table_name like '%"+tableName+"%'";
            }
            if(StrUtil.isNotBlank(tableName) && !isLike){
                sql+="and table_name = '"+tableName+"'";
                countSql+="and table_name = '"+tableName+"'";
            }
            sql+=" order by create_time desc";
            if(page!=null){
                sql+=" limit "+(page.getSize()*(page.getCurrent()-1)) +","+page.getSize();
            }
        }
        if(dbTypeEnum==DbTypeEnum.SQL_SERVER){
            sql=" WITH selectTemp AS (SELECT TOP 100 PERCENT  ROW_NUMBER() OVER (ORDER BY a.create_date desc) as __row_number__,a.name as tableName, g.value as tableComment ,a.create_date as createTime\n" +
                    "from sys.tables a left join sys.extended_properties g on (a.object_id = g.major_id AND g.minor_id = 0)\n" +
                    "where 1=1 ";
            countSql="select count(1) as num from sys.tables a left join sys.extended_properties g on (a.object_id = g.major_id AND g.minor_id = 0) where 1=1 ";
            if(StrUtil.isNotBlank(tableName) && isLike){
                sql+="and a.name like '%"+tableName+"%'";
                countSql+="and a.name like '%"+tableName+"%'";
            }
            if(StrUtil.isNotBlank(tableName) && !isLike){
                sql+="and a.name = '"+tableName+"'";
                countSql+="and a.name = '"+tableName+"'";
            }
            sql+=" order by a.create_date desc) SELECT * FROM selectTemp ";
            if(page!=null){
                //当前页数
                page.getCurrent();
                //每页个数
                page.getSize();
                sql+="WHERE __row_number__ BETWEEN "+(page.getSize()*(page.getCurrent()-1)+1) +" AND "+page.getSize()*(page.getCurrent()) +" ORDER BY __row_number__";
            }
        }
        return Arrays.asList(sql,countSql);
    }

    /***
     * 获取指定表信息的SQL
     * @author zengyu
     * @date 2021年09月01日 09:07
     * @param tableName 指定表名 必填
     * @param dbTypeEnum 数据库类型
     * @return 表信息的SQL
     */
    private static String queryColumnSql(String tableName,DbTypeEnum dbTypeEnum){
        if(StrUtil.isBlank(tableName)){
            throw new CheckedException("表名不能为空");
        }
        String sql="";
        if(dbTypeEnum==DbTypeEnum.MYSQL){
            sql = "select \n"
                    + "column_name as columnName, \n"
                    + "data_type as dataType, \n"
                    + "column_comment as columnComment, \n"
                    + "column_key as columnKey, \n"
                    + "extra ,\n"
                    + "is_nullable as isNullable,\n"
                    + "column_type as columnType \n"
                    + "from information_schema.columns\n"
                    + "where table_name ='"+tableName+"' and table_schema = (select database()) order by ordinal_position";
        }
        if(dbTypeEnum==DbTypeEnum.SQL_SERVER){
            sql="SELECT \n" +
                    "a.name as columnName,\n" +
                    "b.name as dataType,\n" +
                    "isnull(g.[value],'') as columnComment,\n" +
                    "case when exists(SELECT 1 FROM sysobjects where xtype='PK' and parent_obj=a.id and name in (\n" +
                    "SELECT name FROM sysindexes WHERE indid in( SELECT indid FROM sysindexkeys WHERE id = a.id AND colid=a.colid))) then 'PRI' else '' end as columnKey,\n" +
                    "case when a.colstat=1 then 'auto_increment' else '' end as extra,\n" +
                    "case when a.isnullable=1 then 'YES'else 'NO' end as isNullable,\n" +
                    "b.name + '('+ cast(COLUMNPROPERTY(a.id,a.name,'PRECISION') as varchar(20))+')' as columnType\n" +
                    "    \n" +
                    "FROM \n" +
                    "    syscolumns a\n" +
                    "left join \n" +
                    "    systypes b \n" +
                    "on \n" +
                    "    a.xusertype=b.xusertype\n" +
                    "inner join \n" +
                    "    sysobjects d \n" +
                    "on \n" +
                    "    a.id=d.id  and d.xtype='U' and  d.name<>'dtproperties'\n" +
                    "left join \n" +
                    "sys.extended_properties   g \n" +
                    "on \n" +
                    "    a.id=G.major_id and a.colid=g.minor_id  \n" +
                    "where \n" +
                    "    d.name='"+tableName+"'  \n" +
                    "order by \n" +
                    "    a.id,a.colorder";
        }
        return sql;
    }

    /***
     * 获取指定表名信息
     * @author zengyu
     * @date 2021年09月01日 13:52
     * @param databaseConfigBean 数据库配置
     * @param tableName 表名
     * @param isLike 是否模糊查
     * @param page 分页
     * @return  表信息 (名称，注释，创建时间)
     */
    private static List<Map<String, String>> queryTable(DatabaseConfigBean databaseConfigBean,String tableName,Boolean isLike,Page<?> page){
        Connection conn = null;
        Statement stmt = null;
        List<Map<String, String>> result=new ArrayList<>();
        List<String> sqlList = queryTableSql(tableName,DbTypeEnum.getTimerStatusToCode(databaseConfigBean.getDbType()),isLike,page);
        try {
            Class.forName(databaseConfigBean.getDriverName());
            conn = DriverManager.getConnection(
                    databaseConfigBean.getUrl(),
                    databaseConfigBean.getUserName(),
                    databaseConfigBean.getPassword());
            assert conn != null;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlList.get(0));
            result=toListMap(rs);
            if(page!=null){
                rs = stmt.executeQuery(sqlList.get(1));
                rs.next();
                page.setTotal(rs.getLong("num"));
            }
        } catch (Exception ignored) {
            return result;
        }finally {
            // 关闭资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {
            } // 什么都不做
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /***
     * 获取指定表的列信息
     * @author zengyu
     * @date 2021年09月01日 13:53
     * @param databaseConfigBean 数据库配置
     * @param tableName 表名
     * @return 列信息
     */
    private static List<Map<String, String>> queryColumns(DatabaseConfigBean databaseConfigBean,String tableName){
        Connection conn = null;
        Statement stmt = null;
        List<Map<String,String>> result=new ArrayList<>();
        String sql = queryColumnSql(tableName,DbTypeEnum.getTimerStatusToCode(databaseConfigBean.getDbType()));
        try {
            Class.forName(databaseConfigBean.getDriverName());
            conn = DriverManager.getConnection(
                    databaseConfigBean.getUrl(),
                    databaseConfigBean.getUserName(),
                    databaseConfigBean.getPassword());
            assert conn != null;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            result=toListMap(rs);
        } catch (Exception ignored) {
            return result;
        }finally {
            // 关闭资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {
            } // 什么都不做
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /***
     * ResultSet 转 List Map
     * @author zengyu
     * @date 2021年09月01日 13:54
     * @param ret ResultSet
     * @return List Map
     */
    private static List<Map<String, String>> toListMap(ResultSet ret) throws SQLException{
        List<Map<String, String>> list = new ArrayList<>();
        ResultSetMetaData meta = ret.getMetaData();
        int cot = meta.getColumnCount();

        while(ret.next()) {
            Map<String, String> map = MapUtil.newHashMap();
            for(int i = 0; i < cot; i++) {
                map.put(meta.getColumnLabel(i + 1), ret.getObject(i + 1)==null?"":ret.getObject(i + 1).toString());
            }
            list.add(map);
        }
        return list;
    }

}
