package com.person.chenpt.server.bus.re.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.person.chenpt.core.base.model.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 核酸检测信息上报
 * </p>
 *
 * @author chenpengtao
 * @since 2023-06-15
 */
@Getter
@Setter
@TableName("T_RE_NUCLEIC_ACID")
@ApiModel(value = "NucleicAcid对象", description = "核酸检测信息上报")
public class NucleicAcid extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上报单位Id")
    @TableField("DEPT_ID")
    private String deptId;

    @ApiModelProperty("上报单位名称")
    @TableField("DEPT_NAME")
    private String deptName;

    @ApiModelProperty("统一社会信用代码")
    @TableField("CREDIT_CODE")
    private String creditCode;

    @ApiModelProperty("行政区划")
    @TableField("ADMINISTRATIVE_DISTRICT")
    private String administrativeDistrict;

    @ApiModelProperty("上报日期")
    @TableField("REPORT_DATE")
    private Date reportDate;

    @ApiModelProperty("从业人员Id")
    @TableField("PEOPLE_ID")
    private String peopleId;

    @ApiModelProperty("从业人员姓名")
    @TableField("PEOPLE_NAME")
    private String peopleName;

    @ApiModelProperty("身份证号")
    @TableField("IDCARD")
    private String idcard;

    @ApiModelProperty("手机号")
    @TableField("MOBILE")
    private String mobile;

    @ApiModelProperty("居住地_行政区划")
    @TableField("ADRESS_ADMINISTRATIVE_DISTRICT")
    private String adressAdministrativeDistrict;

    @ApiModelProperty("居住地_街道")
    @TableField("ADRESS_STREET")
    private String adressStreet;

    @ApiModelProperty("居住地_详细地址")
    @TableField("ADRESS_DETAIL")
    private String adressDetail;

    @ApiModelProperty("所属单位名称")
    @TableField("UNIT_NAME")
    private String unitName;

    @ApiModelProperty("所属单位统一社会信用代码")
    @TableField("UNIT_CREDIT_CODE")
    private String unitCreditCode;

    @ApiModelProperty("行业类别")
    @TableField("INDUSTRY_CATEGORY")
    private String industryCategory;

    @ApiModelProperty("审核状态")
    @TableField("STATUS")
    private Integer status;


}
