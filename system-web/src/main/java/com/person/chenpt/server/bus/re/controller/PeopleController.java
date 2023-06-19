package com.person.chenpt.server.bus.re.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.person.chenpt.core.base.model.BasePageQuery;
import com.person.chenpt.core.base.model.PageEntity;
import com.person.chenpt.core.base.model.Result;
import com.person.chenpt.server.bus.re.entity.People;
import com.person.chenpt.server.bus.re.service.PeopleService;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

/**
 * <p>
 * 从业人员信息 前端控制器
 * </p>
 *
 * @author chenpengtao
 * @since 2023-06-15
 */
@RestController
@RequestMapping("/re/people")
@Api(tags = "从业人员信息")
public class PeopleController {
    @Autowired
    private PeopleService peopleService;

    @GetMapping("/page")
    @ApiOperation("【从业人员信息】分页查询")
    public Result<PageEntity<People>> page(@Validated PeoplePageQuery pgQy){
        Page<People> queryPage = pgQy.getPage().mybatisPlusPage();
        peopleService.page(queryPage, pgQy.queryWrapper());
        return Result.success(PageEntity.build(queryPage));
    }

    @GetMapping("/getLst")
    @ApiOperation("【从业人员信息】列表查询")
    public Result getLst(@Validated PeoplePageQuery pgQy){
        return Result.success(peopleService.list());
    }

    @PostMapping("/save-or-update")
    @ApiOperation("【从业人员信息】保存")
    public Result<Boolean> saveOrUpdate(@RequestBody @Validated PeopleSave saveDto){
        People entity = BeanUtil.copyProperties(saveDto, People.class);
        boolean b = peopleService.saveOrUpdate(entity);
        return Result.success(b);
    }

    @DeleteMapping("/delete")
    @ApiOperation("【从业人员信息】删除")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "ids",value = "IDs",required = true),
    })
    public Result<Boolean> delete(String ids){
        boolean b = peopleService.removeByIds(StrUtil.splitTrim(ids,","));
        return Result.success(b);
    }

    @Data
    public static class PeoplePageQuery{
        @ApiModelProperty(value = "分页")
        private BasePageQuery<People> page;

        public Wrapper<People> queryWrapper(){
            return Wrappers.<People>lambdaQuery().orderByDesc(People::getCreateTime);
        }
    }

    @Data
    @ApiModel(description="保存更新【从业人员信息】")
    public static class PeopleSave {
        @ApiModelProperty(value = "主键")
        private String id;
        @ApiModelProperty(value = "从业人员姓名")
        private String peopleName;
        @ApiModelProperty(value = "手机号")
        private String mobile;
        @ApiModelProperty(value = "身份证")
        private String idcard;
        @ApiModelProperty(value = "性别")
        private String gender;
        @ApiModelProperty(value = "居住地_行政区划")
        private String adressAdministrativeDistrict;
        @ApiModelProperty(value = "居住地_街道")
        private String adressStreet;
        @ApiModelProperty(value = "居住地_详细地址")
        private String adressDetail;
        @ApiModelProperty(value = "所属单位名称")
        private String unitName;
        @ApiModelProperty(value = "所属单位统一社会信用代码")
        private String unitCreditCode;
        @ApiModelProperty(value = "行业类别")
        private String industryCategory;
        @ApiModelProperty(value = "部门Id")
        private String deptId;
        @ApiModelProperty(value = "部门名称")
        private String deptName;
    }
}
