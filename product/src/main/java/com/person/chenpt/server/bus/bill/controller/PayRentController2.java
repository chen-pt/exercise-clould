package com.person.chenpt.server.bus.bill.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.person.chenpt.core.base.model.BasePageQuery;
import com.person.chenpt.core.base.model.PageEntity;
import com.person.chenpt.core.util.Result;
import com.person.chenpt.server.bus.bill.entity.PayRent;
import com.person.chenpt.server.bus.bill.service.PayRentService;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * <p>
 * 收支-租金支出 前端控制器
 * </p>
 *
 * @author chenpengtao
 * @since 2022-07-27
 */
@RestController
@RequestMapping("/bill/pay-rent2")
@Api(tags = "收支-租金支出")
public class PayRentController2 {
    @Autowired
    private PayRentService payRentService;

    @GetMapping("/page")
    @ApiOperation("【收支-租金支出2】分页查询")
    public Result<PageEntity<PayRent>> page(@Validated PayRentPageQuery pgQy){
        Page<PayRent> queryPage = pgQy.getPage().mybatisPlusPage();
        payRentService.page(queryPage, pgQy.queryWrapper());
        return Result.success(PageEntity.build(queryPage));
    }

    @PostMapping("/save-or-update")
    @ApiOperation("【收支-租金支出2】保存")
    public Result<Boolean> saveOrUpdate(@RequestBody @Validated PayRentSave saveDto){
        PayRent entity = BeanUtil.copyProperties(saveDto, PayRent.class);
        boolean b = payRentService.saveOrUpdate(entity);
        return Result.success(b);
    }

    @DeleteMapping("/delete")
    @ApiOperation("【收支-租金支出2】删除")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "ids",value = "IDs",required = true),
    })
    public Result<Boolean> delete(String ids){
        boolean b = payRentService.removeByIds(StrUtil.splitTrim(ids,","));
        return Result.success(b);
    }

    @Data
    public static class PayRentPageQuery{
        @ApiModelProperty(value = "分页")
        private BasePageQuery<PayRent> page;

        public Wrapper<PayRent> queryWrapper(){
            return Wrappers.<PayRent>lambdaQuery().orderByDesc(PayRent::getCreateTime);
        }
    }

    @Data
    @ApiModel(description="保存更新【收支-租金支出2】")
    public static class PayRentSave {
        @ApiModelProperty(value = "主键")
        private String id;
        @ApiModelProperty(value = "支付日期")
        private String payDateTime;
        @ApiModelProperty(value = "支付周期")
        private String payCycle;
        @ApiModelProperty(value = "支付类型")
        private String payType;
        @ApiModelProperty(value = "支付金额")
        private BigDecimal payAmount;
        @ApiModelProperty(value = "包租方")
        private String lessee;
        @ApiModelProperty(value = "支付说明")
        private String remark;
    }
}
