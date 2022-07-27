package com.person.chenpt.core.base.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 显示类基类
 *
 * @author shen.shaohua
 * @date 2021-3-16 12:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntityVo implements Serializable {
  private static final long serialVersionUID = 1L;

  @ExcelIgnore
  @ApiModelProperty(value = "主键")
  private String id;

  @ExcelIgnore
  @ApiModelProperty(value = "创建时间")
  protected String createTime;

  @ExcelIgnore
  @ApiModelProperty(value = "创建用户ID")
  protected String createUserId;

  @ExcelIgnore
  @ApiModelProperty(value = "创建用户名称")
  protected String createUserName;

  @ExcelIgnore
  @ApiModelProperty(value = "创建组织ID")
  protected String createDepartmentId;

  @ExcelIgnore
  @ApiModelProperty(value = "创建组织名称")
  protected String createDepartmentName;

  @ExcelIgnore
  @ApiModelProperty(value = "更新时间")
  protected String updateTime;

  @ExcelIgnore
  @ApiModelProperty(value = "更新用户ID")
  protected String updateUserId;

  @ExcelIgnore
  @ApiModelProperty(value = "更新用户名称")
  protected String updateUserName;

  @ExcelIgnore
  @ApiModelProperty(value = "更新组织ID")
  protected String updateDepartmentId;

  @ExcelIgnore
  @ApiModelProperty(value = "更新组织名称")
  protected String updateDepartmentName;

  @ExcelIgnore
  @ApiModelProperty(value = "是否删除：1、删除；0、未删除")
  protected Integer isDelete;
}