package com.person.chenpt.server.bus.re.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.person.chenpt.core.base.model.BasePageQuery;
import com.person.chenpt.core.base.model.PageEntity;
import com.person.chenpt.core.base.model.Result;
import com.person.chenpt.server.bus.re.entity.NucleicAcid;
import com.person.chenpt.server.bus.re.service.NucleicAcidService;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

/**
 * <p>
 * 核酸检测信息上报 前端控制器
 * </p>
 *
 * @author chenpengtao
 * @since 2023-06-15
 */
@RestController
@RequestMapping("/re/nucleic-acid")
@Api(tags = "核酸检测信息上报")
public class NucleicAcidController {
    @Autowired
    private NucleicAcidService nucleicAcidService;

    @GetMapping("/page")
    @ApiOperation("【核酸检测信息上报】分页查询")
    public Result<PageEntity<NucleicAcid>> page(@Validated NucleicAcidPageQuery pgQy){
        Page<NucleicAcid> queryPage = pgQy.getPage().mybatisPlusPage();
        nucleicAcidService.page(queryPage, pgQy.queryWrapper());
        return Result.success(PageEntity.build(queryPage));
    }

    @PostMapping("/save-or-update")
    @ApiOperation("【核酸检测信息上报】保存")
    public Result<Boolean> saveOrUpdate(@RequestBody @Validated NucleicAcidSave saveDto){
        NucleicAcid entity = BeanUtil.copyProperties(saveDto, NucleicAcid.class);
        boolean b = nucleicAcidService.saveOrUpdate(entity);
        return Result.success(b);
    }

    @DeleteMapping("/delete")
    @ApiOperation("【核酸检测信息上报】删除")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "ids",value = "IDs",required = true),
    })
    public Result<Boolean> delete(String ids){
        boolean b = nucleicAcidService.removeByIds(StrUtil.splitTrim(ids,","));
        return Result.success(b);
    }

    @Data
    public static class NucleicAcidPageQuery{
        @ApiModelProperty(value = "分页")
        private BasePageQuery<NucleicAcid> page;

        public Wrapper<NucleicAcid> queryWrapper(){
            return Wrappers.<NucleicAcid>lambdaQuery().orderByDesc(NucleicAcid::getCreateTime);
        }
    }

    @Data
    @ApiModel(description="保存更新【核酸检测信息上报】")
    public static class NucleicAcidSave {
        @ApiModelProperty(value = "主键")
        private String id;
        @ApiModelProperty(value = "上报单位Id")
        private String deptId;
        @ApiModelProperty(value = "上报单位名称")
        private String deptName;
        @ApiModelProperty(value = "统一社会信用代码")
        private String creditCode;
        @ApiModelProperty(value = "行政区划")
        private String administrativeDistrict;
        @ApiModelProperty(value = "上报日期")
        private Date reportDate;
        @ApiModelProperty(value = "从业人员Id")
        private String peopleId;
        @ApiModelProperty(value = "从业人员姓名")
        private String peopleName;
        @ApiModelProperty(value = "身份证号")
        private String idcard;
        @ApiModelProperty(value = "手机号")
        private String mobile;
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
        @ApiModelProperty(value = "审核状态")
        private Integer status;
    }
}
