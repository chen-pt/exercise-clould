package com.person.chenpt.server.bus.re.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.person.chenpt.core.base.model.BaseEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 从业人员信息
 * </p>
 *
 * @author chenpengtao
 * @since 2023-06-15
 */
@Getter
@Setter
@TableName("T_RE_PEOPLE")
@ApiModel(value = "People对象", description = "从业人员信息")
public class People extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("从业人员姓名")
    @TableField("PEOPLE_NAME")
    private String peopleName;

    @ApiModelProperty("手机号")
    @TableField("MOBILE")
    private String mobile;

    @ApiModelProperty("身份证")
    @TableField("IDCARD")
    private String idcard;

    @ApiModelProperty("性别")
    @TableField("GENDER")
    private String gender;

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

    @ApiModelProperty("部门Id")
    @TableField("DEPT_ID")
    private String deptId;

    @ApiModelProperty("部门名称")
    @TableField("DEPT_NAME")
    private String deptName;


}
