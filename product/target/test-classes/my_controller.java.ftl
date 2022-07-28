package ${package.Controller};


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cesgroup.jgj.core.base.model.BasePageQuery;
import com.cesgroup.jgj.core.base.model.PageEntity;
import com.cesgroup.jgj.core.base.model.Result;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${package.Entity}.${entity};
import ${package.Service}.${entity}Service;
import io.swagger.annotations.*;
import lombok.Data;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

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
    @ApiOperation("【${table.comment!}】分页查询")
    public Result<PageEntity<${entity}>> page(@Validated ${entity}PageQuery pgQy){
        Page<${entity}> queryPage = pgQy.getPage().mybatisPlusPage();
        ${eservice}.page(queryPage, pgQy.queryWrapper());
        return Result.success(PageEntity.build(queryPage));
    }

    @PostMapping("/save-or-update")
    @ApiOperation("【${table.comment!}】保存")
    public Result<Boolean> saveOrUpdate(@RequestBody @Validated ${entity}Save saveDto){
        ${entity} entity = BeanUtil.copyProperties(saveDto, ${entity}.class);
        boolean b = ${eservice}.saveOrUpdate(entity);
        return Result.success(b);
    }

    @DeleteMapping("/delete")
    @ApiOperation("【${table.comment!}】删除")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "ids",value = "IDs",required = true),
    })
    public Result<Boolean> delete(String ids){
        boolean b = ${eservice}.removeByIds(StrUtil.splitTrim(ids,","));
        return Result.success(b);
    }

    @Data
    public static class ${entity}PageQuery{
        @ApiModelProperty(value = "分页")
        private BasePageQuery<${entity}> page;

        public Wrapper<${entity}> queryWrapper(){
            return Wrappers.<${entity}>lambdaQuery().orderByDesc(${entity}::getCreateTime);
        }
    }

    @Data
    @ApiModel(description="保存更新【${table.comment!}】")
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
