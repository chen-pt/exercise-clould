package com.person.chenpt;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.function.ConverterFileName;
import com.person.chenpt.core.base.model.BaseEntity;
import com.person.chenpt.core.base.service.impl.MyServiceImpl;
import com.person.chenpt.core.base.service.impl.SimpleServiceImpl;

import java.util.Collections;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 *
 * @author jobob
 * @since 2018-09-12
 */
public class GovernanceGenerator {

	public static void main(String[] args) {
		String projectPath = System.getProperty("user.dir");
		String nextMoudleName = "product";
		String newprojectPath = projectPath+"/"+nextMoudleName;
		String moduleName = "bill";
		FastAutoGenerator.create("jdbc:dm://localhost:5238",  "HOUSE_LEASE","Aa12345678")
				.globalConfig(builder -> {
					builder.author("chenpengtao") // 设置作者
							.enableSwagger() // 开启 swagger 模式
							.fileOverride() // 覆盖已生成文件
							.disableOpenDir()
							.dateType(DateType.TIME_PACK)
							.outputDir(newprojectPath+"/src/main/java"); // 指定输出目录
				})
				.packageConfig(builder -> {
					builder.parent("com.person.chenpt.server.bus") // 设置父包名
							.moduleName(moduleName) // 设置父包模块名
							.pathInfo(Collections.singletonMap(OutputFile.xml, "src/main/resources/mapper/"+moduleName)); // 设置mapperXml生成路径
				})
				.templateConfig(builder -> {
					builder.disable(TemplateType.SERVICE, TemplateType.SERVICEIMPL, TemplateType.CONTROLLER)
							.service("my_service.java")
							.controller("my_controller.java");
				})
				.strategyConfig(builder -> {
					// 设置需要生成的表名
					builder
							.addInclude("T_BILL_PAY_RENT")
							.addTablePrefix("t_sys_", "t_hm_", "c_", "t_cm","t_bill"); // 设置过滤表前缀
					builder.entityBuilder()
							.enableTableFieldAnnotation()
							.superClass(BaseEntity.class)
//							.fileOverride()
							.enableLombok();

					builder.serviceBuilder()
//							.fileOverride()
//							.superServiceImplClass(MyServiceImpl.class)
							.superServiceImplClass(SimpleServiceImpl.class)
							.convertServiceFileName(new ConverterFileName() {
								@Override
								public String convert(String entityName) {
									return entityName + "Service" ;
								}
							});
					builder.controllerBuilder()
//							.fileOverride()
							.enableRestStyle();

				})
				.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.execute();
	}
}