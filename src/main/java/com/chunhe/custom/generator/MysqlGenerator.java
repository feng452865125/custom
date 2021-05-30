package com.chunhe.custom.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import javax.script.ScriptException;
import java.io.FileNotFoundException;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 *
 * @author white
 * @since 2020年10月8日19:28:21
 */
public class MysqlGenerator {

    public static String packageName = "com.chunhe.custom";
    public static String[] packageTypes = {"App", "Pc", "Fm"};
    //多表写法：{"test1", "test2"};
    public static String[] tableNames = {"sys_permission", "sys_role_permission", "sys_user_role"};

    private static String datasourceDriver = "com.mysql.jdbc.Driver";
    private static String datasourceUrl = "jdbc:mysql://localhost:3306/custom?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";
    private static String datasourceUsername = "root";
    private static String datasourcePassword = "root";

    /**
     * RUN THIS
     */
    public static void main(String[] args) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        for (String packageType : packageTypes) {
            //代码生成器
            AutoGenerator mpg = new AutoGenerator();
            //设置名称
            GlobalConfig gc = new GlobalConfig();
            String projectPath = System.getProperty("user.dir");
//            gc.setOutputDir(projectPath + "/src/main/java");
            gc.setOutputDir(projectPath + "/src/test/java");
            gc.setAuthor("AutoGenerator from white");
            gc.setOpen(false);
            gc.setDateType(DateType.ONLY_DATE);
            gc.setFileOverride(true);
            gc.setBaseColumnList(true);
            gc.setBaseResultMap(true);
            gc.setControllerName(packageType + "%sController");
            gc.setServiceName("%sService");
            mpg.setGlobalConfig(gc);

            // 数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setDriverName(datasourceDriver);
            dsc.setUrl(datasourceUrl);
            dsc.setUsername(datasourceUsername);
            dsc.setPassword(datasourcePassword);
            mpg.setDataSource(dsc);

            // 包配置
            PackageConfig pc = new PackageConfig();
            pc.setParent(packageName);
            pc.setController("controller." + packageType.toLowerCase());
            mpg.setPackageInfo(pc);

            // 自定义配置
            InjectionConfig cfg = new InjectionConfig() {
                @Override
                public void initMap() {
                    // to do nothing
                }
            };
            mpg.setCfg(cfg);

            // 配置模板
            TemplateConfig tc = new TemplateConfig();
            tc.setServiceImpl(null);
            tc.setXml(null);
            tc.setService("generatorTemp/service.java");
            tc.setController("generatorTemp/controller." + packageType.toLowerCase() + ".java");
            tc.setEntity("generatorTemp/entity.java");
            mpg.setTemplate(tc);

            // 策略配置
            StrategyConfig strategy = new StrategyConfig();
            strategy.setNaming(NamingStrategy.underline_to_camel);
            strategy.setColumnNaming(NamingStrategy.underline_to_camel);
            strategy.setCapitalMode(true);
            strategy.setEntitySerialVersionUID(true);
            strategy.setNaming(NamingStrategy.underline_to_camel);
            strategy.setInclude(tableNames);
            strategy.setControllerMappingHyphenStyle(true);
            strategy.setTablePrefix(pc.getModuleName() + "_");
            strategy.setEntityLombokModel(true);
            strategy.setSuperEntityColumns("id", "create_date", "update_date", "delete_date");
            strategy.setSuperEntityClass("com.chunhe.custom.mybatis.BaseEntity");
            strategy.setSuperMapperClass("com.chunhe.custom.mybatis.BaseMapper");//模板中，再继承BaseWrapperMapper
            strategy.setSuperServiceClass("com.chunhe.custom.mybatis.BaseService");
            mpg.setStrategy(strategy);

            mpg.execute();
            System.out.println("================= MyBatis Plus Generator Execute Complete Finish (" + packageType.toLowerCase() + ")==================");
        }
    }

}
