//package com.person.chenpt;
//
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//import com.person.chenpt.core.base.model.BaseEntity;
//import com.person.chenpt.core.base.service.impl.MyServiceImpl;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * <p>
// * mysql 代码生成器演示例子
// * </p>
// *
// * @author jobob
// * @since 2018-09-12
// */
//public class GovernanceGenerator {
//
//	/**
//	 * RUN THIS
//	 */
//	public static void main(String[] args) {
//		String moduleName = "cardeclare";
//		String projectPath = System.getProperty("user.dir");
//		String nextMoudleName = "product";
//		projectPath = projectPath+"/"+nextMoudleName;
//		String servicePath = joinPath(projectPath + "/src/main/java/", "com.person.chenpt.server.bus." + moduleName + ".service");
//		String mapperXmlPath = projectPath + "/src/main/resources/mapper/" + moduleName;
//		String basePackage = "com.person.chenpt.server.bus";
//
//		// 代码生成器
//		AutoGenerator mpg = new AutoGenerator();
//
//		// 全局配置
//		GlobalConfig gc = new GlobalConfig();
//
//		gc.setOutputDir(projectPath + "/src/main/java");
//		gc.setAuthor("chenpengtao");
//		gc.setOpen(false);
//		gc.setSwagger2(true);
//		// 是否覆盖
//		// gc.setFileOverride(true);
//
//		// gc.setActiveRecord(true);
//		// xml 配置
//		gc.setBaseResultMap(true);
//		gc.setBaseColumnList(true);
//		mpg.setGlobalConfig(gc);
//
//		// 数据源配置
//		DataSourceConfig dsc = commonDataSourceConfig();
//		mpg.setDataSource(dsc);
//
//		// 包配置
//		PackageConfig pc = new PackageConfig();
//		// pc.setModuleName(scanner("模块名"));
//
//		pc.setModuleName(moduleName);
//		pc.setParent(basePackage);
//		mpg.setPackageInfo(pc);
//
//		// 自定义配置
//		InjectionConfig cfg = new InjectionConfig() {
//			@Override
//			public void initMap() {
//				// to do nothing
//			}
//		};
//		List<FileOutConfig> focList = new ArrayList<>();
////		focList.add(new FileOutConfig("/mapper.xml.ftl") {
////			@Override
////			public String outputFile(TableInfo tableInfo) {
////				// 自定义输入文件名称
////				return mapperXmlPath + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
////			}
////		});
//		focList.add(new FileOutConfig("/my_service.java.ftl") {
//			@Override
//			public String outputFile(TableInfo tableInfo) {
//				// 自定义输入文件名称
//				return servicePath + "/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
//			}
//		});
//		cfg.setFileOutConfigList(focList);
//		mpg.setCfg(cfg);
//
//		TemplateConfig templateConfig = new TemplateConfig();
//		templateConfig.setController("/my_controller.java.bak");
//
//		templateConfig
//				// .disable(TemplateType.ENTITY)
//				.disable(TemplateType.SERVICE)
//				// .disable(TemplateType.CONTROLLER)
//				// .disable(TemplateType.MAPPER)
//				.disable(TemplateType.XML);
//
//		mpg.setTemplate(templateConfig);
//		// 策略配置
//		StrategyConfig strategy = new StrategyConfig();
//		strategy.setNaming(NamingStrategy.underline_to_camel);
//		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//		strategy.setSuperEntityClass(BaseEntity.class);
//		strategy.setSuperServiceImplClass(MyServiceImpl.class);
//		strategy.setEntityLombokModel(true);
//		strategy.setInclude(
//				"T_BUS_CAR_DECLARE"
//		);
//		strategy.setSuperEntityColumns("ID", "CREATE_TIME", "CREATE_USER_ID", "CREATE_USER_NAME", "CREATE_DEPARTMENT_ID", "CREATE_DEPARTMENT_NAME", "UPDATE_TIME", "UPDATE_USER_ID", "UPDATE_USER_NAME",
//				"UPDATE_DEPARTMENT_ID", "UPDATE_DEPARTMENT_NAME", "IS_DELETE");
//		strategy.setControllerMappingHyphenStyle(true);
//		strategy.setTablePrefix("T_BUS_");
//		strategy.setRestControllerStyle(true);
//		mpg.setStrategy(strategy);
//		// 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
//		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//		mpg.execute();
//	}
//
//	/**
//	 * 数据源配置
//	 *
//	 * @return
//	 */
//	private static DataSourceConfig commonDataSourceConfig() {
//		DataSourceConfig dsc = new DataSourceConfig();
//		dsc.setUrl("jdbc:dm://127.0.0.1:5238");
//		dsc.setDriverName("dm.jdbc.driver.DmDriver");
//		dsc.setUsername("CAR-MANAGER");
//		dsc.setPassword("Aa12345678");
//		return dsc;
//	}
//
//	/**
//	 * 连接路径字符串
//	 *
//	 * @param parentDir 路径常量字符串
//	 * @param packageName 包名
//	 * @return 连接后的路径
//	 */
//	private static String joinPath(String parentDir, String packageName) {
//		if (StringUtils.isBlank(parentDir)) {
//			parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
//		}
//		if (!StringUtils.endsWith(parentDir, File.separator)) {
//			parentDir += File.separator;
//		}
//		packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
//		return parentDir + packageName;
//	}
//}