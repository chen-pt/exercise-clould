package com.person.chenpt.server.bus.bill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.person.chenpt.core.base.model.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 收支-租金支出
 * </p>
 *
 * @author chenpengtao
 * @since 2022-07-27
 */
@Getter
@Setter
@TableName("T_BILL_PAY_RENT")
@ApiModel(value = "PayRent对象", description = "收支-租金支出")
public class PayRent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("支付日期")
    private String payDateTime;

    @ApiModelProperty("支付周期")
    private String payCycle;

    @ApiModelProperty("支付类型")
    private String payType;

    @ApiModelProperty("支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty("包租方")
    private String lessee;

    @ApiModelProperty("支付说明")
    private String remark;


}
