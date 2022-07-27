package com.person.chenpt.core.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.person.chenpt.util.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 实体类基类
 *
 * @author: ding.haiyang
 * @date: 2019-2-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty(value = "主键")
  @TableId("ID")
  private String id;

  @ApiModelProperty(value = "创建时间")
  @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
  protected String createTime;

  @ApiModelProperty(value = "创建用户ID")
  @TableField(value = "CREATE_USER_ID", fill = FieldFill.INSERT)
  protected String createUserId;

  @ApiModelProperty(value = "创建用户名称")
  @TableField(value = "CREATE_USER_NAME", fill = FieldFill.INSERT)
  protected String createUserName;

  @ApiModelProperty(value = "创建组织ID")
  @TableField(value = "CREATE_DEPARTMENT_ID", fill = FieldFill.INSERT)
  protected String createDepartmentId;

  @ApiModelProperty(value = "创建组织名称")
  @TableField(value = "CREATE_DEPARTMENT_NAME", fill = FieldFill.INSERT)
  protected String createDepartmentName;

  @ApiModelProperty(value = "更新时间")
  @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
  protected String updateTime;

  @ApiModelProperty(value = "更新用户ID")
  @TableField(value = "UPDATE_USER_ID", fill = FieldFill.UPDATE)
  protected String updateUserId;

  @ApiModelProperty(value = "更新用户名称")
  @TableField(value = "UPDATE_USER_NAME", fill = FieldFill.UPDATE)
  protected String updateUserName;

  @ApiModelProperty(value = "更新组织ID")
  @TableField(value = "UPDATE_DEPARTMENT_ID", fill = FieldFill.UPDATE)
  protected String updateDepartmentId;

  @ApiModelProperty(value = "更新组织名称")
  @TableField(value = "UPDATE_DEPARTMENT_NAME", fill = FieldFill.UPDATE)
  protected String updateDepartmentName;

  @ApiModelProperty(value = "是否删除：1、删除；0、未删除")
  @TableField(value = "IS_DELETE")
  @TableLogic(value = "0", delval = "1")
  protected Integer isDelete = Constants.Delete.NO_DELETE;
}
