package com.tul.generator;

import apps.commons.base.BaseController;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * <pre>
 * 对象功能 模板生成类
 * 开发人员：曾煜
 * 创建时间：2020/8/11 18:12
 * </pre>
 **/
public class TemplateMain {
    public static void main(String[] args) throws Exception {
        createTemple();
    }

    private static void createTemple() throws Exception {
        Properties configType = getConfig("config.properties");
        String tables = configType.getProperty("tables");
        Properties pro;
        TemplateConfig templateConfig = new TemplateConfig();
        String config = configType.getProperty("config");
        pro = getConfig("dynamicDatasource/config-"+config+".properties");
        templateConfig.setServiceImpl("template/ServiceImpl-"+config+".java");
        templateConfig.setMapper("template/Dao-"+config+".java");
        templateConfig.setController("template/Controller.java");

        String author = pro.getProperty("author");
        boolean fileOverride = Boolean.parseBoolean(pro.getProperty("fileOverride"));
        boolean openSwagger2 = Boolean.parseBoolean(pro.getProperty("openSwagger2"));
        String url = pro.getProperty("url");
        String driverName = pro.getProperty("driverName");
        String userName = pro.getProperty("userName");
        String password = pro.getProperty("password");
        boolean openLombok = Boolean.parseBoolean(pro.getProperty("openLombok"));
        boolean restController = Boolean.parseBoolean(pro.getProperty("restController"));
        String parent = pro.getProperty("parent")+"."+pro.getProperty("moduleName");
        String mapperName = pro.getProperty("MapperName");
        boolean logicDelete = Boolean.parseBoolean(pro.getProperty("logicDelete"));
        String logicDeleteFieldName = pro.getProperty("logicDeleteFieldName");
        boolean createTimeAuto = Boolean.parseBoolean(pro.getProperty("createTimeAuto"));
        String createTimeFieldName = pro.getProperty("createTimeFieldName");
        boolean updateTimeAuto = Boolean.parseBoolean(pro.getProperty("updateTimeAuto"));
        String updateTimeFieldName = pro.getProperty("updateTimeFieldName");
        String dbType = pro.getProperty("dbType");


        String daoPackageName = pro.getProperty("daoPackageName");
        String servicePackageName = pro.getProperty("servicePackageName");
        String serviceImplPackageName = pro.getProperty("serviceImplPackageName");
        String entityPackageName = pro.getProperty("entityPackageName");

        //项目目录
        String projectPath = System.getProperty("user.dir") + "/generator";
        //全局策略
        GlobalConfig globalConfig = new GlobalConfig();
        //支持AR模式
        globalConfig.setActiveRecord(true)
                //作者
                .setAuthor(author)
                //生成路径
                .setOutputDir(projectPath + "/src/main/java")
                //是否覆盖文件
                .setFileOverride(fileOverride)
                //主键策略
                .setIdType(IdType.ASSIGN_UUID)
                //是否打开输出目录
                .setOpen(false)
                //开启swagger2
                .setSwagger2(openSwagger2)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setMapperName("%s" + mapperName);


        //数据源配置
        DataSourceConfig dbConfig = new DataSourceConfig();
        if ("mysql".equals(dbType)) {
            dbConfig.setDbType(DbType.MYSQL);
        }
        if ("sqlserver".equals(dbType)) {
            dbConfig.setDbType(DbType.SQL_SERVER);
        }
        dbConfig.setUrl(url);
        dbConfig.setDriverName(driverName);
        dbConfig.setUsername(userName);
        dbConfig.setPassword(password);

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //全局大写命名
        strategyConfig.setCapitalMode(true);
        //下划线转驼峰
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        //生成Rest控制器
        strategyConfig.setRestControllerStyle(restController);
        //设置Lombok
        strategyConfig.setEntityLombokModel(openLombok);
        strategyConfig.setNameConvert(new NameConvert());

        if (tables != null && !"".equals(tables.trim())) {
            //只生成指定的表
            strategyConfig.setInclude(tables.split(","));
        }


        //设置自动填充配置
        List<TableFill> tableFill = new ArrayList<>();
        if (createTimeAuto) {
            tableFill.add(new TableFill(createTimeFieldName, FieldFill.INSERT));
        }
        if (updateTimeAuto) {
            tableFill.add(new TableFill(updateTimeFieldName, FieldFill.INSERT_UPDATE));
        }
        //设置逻辑删除
        if (logicDelete) {
            strategyConfig.setLogicDeleteFieldName(logicDeleteFieldName);
            tableFill.add(new TableFill(logicDeleteFieldName, FieldFill.INSERT));
        }
        if (tableFill.size() != 0) {
            strategyConfig.setTableFillList(tableFill);
        }
        //乐观锁配置
        //strategyConfig.setVersionFieldName("version");


        String templatePath = "/templates/mapper.xml.vm";

        //包名
        PackageConfig packageConfig = new PackageConfig();
        //设置父包
        packageConfig.setParent(parent);
        packageConfig.setMapper(daoPackageName.toLowerCase());
        packageConfig.setService(servicePackageName.toLowerCase());
        packageConfig.setServiceImpl(serviceImplPackageName.toLowerCase());
        packageConfig.setEntity(entityPackageName.toLowerCase());
        packageConfig.setXml(null);

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }

            @Override
            public void initTableMap(TableInfo tableInfo) {
                super.initTableMap(tableInfo);
                for (TableField tableField : tableInfo.getFields()) {
                    tableField.setConvert(true);
                }
            }
        };

        cfg.setFileOutConfigList(focList);

        strategyConfig.setSuperControllerClass(BaseController.class);


        //整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setCfg(cfg);
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dbConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setTemplate(templateConfig);

        //执行
        autoGenerator.execute();
        deleteDir(new File(projectPath + "/src/main/java/" + parent.replaceAll("\\.", "/") + "/null"));
    }

    /**
     * 获取配置
     *
     * @return 配置类
     * @throws Exception 读取配置失败
     */
    private static Properties getConfig(String configName) throws Exception {
        ClassLoader classLoader = TemplateMain.class.getClassLoader();
        URL resourceUrl = classLoader.getResource(configName);
        assert resourceUrl != null;
        String path = resourceUrl.getPath();
        path = path.replaceAll("%20", " ");
        File file = new File(path);
        Properties pro = new Properties();
        pro.load(new FileInputStream(file));
        return pro;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean true 删除成功 false 删除失败
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < Objects.requireNonNull(children).length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    private static void replaceTextContent(String path) throws IOException {
        //原有的内容
        String srcStr = "@RequestMapping\\(\"";
        //要替换的内容
        String replaceStr = "@RequestMapping(\"/admin";
        // 读
        File file = new File(path);
        FileReader in = new FileReader(file);
        BufferedReader bufIn = new BufferedReader(in);
        // 内存流, 作为临时流
        CharArrayWriter tempStream = new CharArrayWriter();
        // 替换
        String line;
        while ((line = bufIn.readLine()) != null) {
            // 替换每行中, 符合条件的字符串
            line = line.replaceAll(srcStr, replaceStr);
            // 将该行写入内存
            tempStream.write(line);
            // 添加换行符
            tempStream.append(System.getProperty("line.separator"));
        }
        // 关闭 输入流
        bufIn.close();
        // 将内存中的流 写入 文件
        FileWriter out = new FileWriter(file);
        tempStream.writeTo(out);
        out.close();
    }

    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        if (tempList == null) {
            return null;
        }
        for (File value : tempList) {
            if (value.isFile()) {
                files.add(value.toString());
            }
        }
        return files;
    }

    private static void copyFileUsingJava7Files(File source, File dest)
            throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

}
