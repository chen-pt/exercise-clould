package ${package.Controller};


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.person.chenpt.core.base.model.BasePageQuery;
import com.person.chenpt.core.base.model.PageEntity;
import com.person.chenpt.core.util.Result;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${package.Entity}.${entity};
import ${package.Service}.${entity}Service;
import io.swagger.annotations.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
@Api(tags = "${table.comment!}")
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
@Api(tags = "${table.comment!}")
public class ${table.controllerName} {
</#if>
<#assign eservice = entity?uncap_first + 'Service' >
	@Autowired
	private ${entity}Service ${eservice};

	@GetMapping("/page")
	@ApiOperation("分页查询${table.comment!}")
	public Result<PageEntity<${entity}>> page(@Validated ${entity}PageQuery page){
		Page<${entity}> queryPage = page.cratePage();
		${eservice}.page(queryPage,page.queryWrapper());
		return Result.success(PageEntity.build(queryPage));
	}

	@PostMapping("/saveOrUpdate")
	@ApiOperation("保存${table.comment!}")
	public Result<Boolean> saveOrUpdate(@RequestBody @Validated ${entity}Save saveDto){
		${entity} entity = BeanUtil.copyProperties(saveDto, ${entity}.class);
		boolean b = ${eservice}.saveOrUpdate(entity);
		return Result.success(b);
	}

	@DeleteMapping("/delete")
	@ApiOperation("删除${table.comment!}")
	@ApiImplicitParams({
	     @ApiImplicitParam(name = "ids",value = "IDs",required = true),
	})
	public Result<Boolean> delete(String ids){
		boolean b = ${eservice}.removeByIds(StrUtil.splitTrim(ids,","));
		return Result.success(b);
	}

	@Data
	@EqualsAndHashCode(callSuper = true)
    public static class ${entity}PageQuery extends BasePageQuery<${entity}> {
		public Wrapper<${entity}> queryWrapper(){
			return Wrappers.<${entity}>lambdaQuery().orderByDesc(${entity}::getCreateTime);
		}
    }
    @Data
    @ApiModel(description="保存更新${table.comment!}")
    public static class ${entity}Save {
		@ApiModelProperty(value = "主键")
		private String id;
		<#list table.fields as field>

		@ApiModelProperty(value = "${field.comment!}")
		private ${field.propertyType} ${field.propertyName};
		</#list>
    }
}
</#if>
