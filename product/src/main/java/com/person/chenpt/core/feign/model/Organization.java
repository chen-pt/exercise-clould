package com.person.chenpt.core.feign.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 组织
 *
 * @author: ding.haiyang
 * @date: 2019-3-11
 */
@Data
public class Organization implements Serializable {
  /**
   * 主键
   */
  private String id;

  /**
   * 父组织id
   */
  private String parentId;

  /**
   * 组织名称
   */
  private String name;

  /**
   * 首字母简拼
   */
  private String nameFirstSpell;

  /**
   * 组织代码
   */
  private String code;

  /**
   * 单位简称
   */
  private String abbreviation;


  private String organizationLevel;


  private Integer isVirtual;

  private Integer status;

  private Integer approvalStatus;

  private String comments;

  private String tenantId;

  private Long showOrder;

  private String createUser;

  private String modifyUser;

  private Timestamp createTime;

  private Timestamp modifyTime;

  private Integer independent;

  private Integer maxLimit;

  private String orgSystemCode;

  //===========================新增
  /**
   * 组织机构代码
   */
  private String organizationCode;

  /**
   * 单位性质
   */
  private String unitProperties;

  /**
   * 单位级别
   */
  private String unitLevel;

  /**
   * 单位地址
   */
  private String unitAddress;

  /**
   * 邮编编码
   */
  private String postalcode;

  /**
   * 联系电话
   */
  private String contactNumber;

  /**
   * 统一社会信用代码
   */
  private String creditCode;

  /**
   * 编制人数
   */
  private Integer manningQuotas;

  /**
   * 执法执勤编制数
   */
  private Integer manningQuotasCruise;

  /**
   * 退休干部总数
   */
  private Integer retiredCadres;

  /**
   * 退休局级人数
   */
  private Integer retiredCadresBureau;

  /**
   * 内设机构数
   */
  private Integer internalOrgans;

  /**
   * 部级领导职数
   */
  private Integer currentMinistry;

  /**
   * 正局级领导职数
   */
  private Integer currentPlusBureau;

  /**
   * 副局级领导职数
   */
  private Integer currentMinusBureau;

  /**
   * 财政拨款方式
   */
  private String capitalSource;
  //===========================新增2
  /**
   * 财政预算代码
   */
  private String budgetCode;

  /**
   * 法人实体序号
   */
  private String legalEntityNumber;

  /**
   * 机构成立批准文号
   */
  private String unitApprovalNumber;

  /**
   * 设立日期
   */
  private String buildDate;

  /**
   * 行政区划
   */
  private String administrativeDistrict;

  /**
   * 单位负责人姓名
   */
  private String principal;

  /**
   * 是否执法执勤单位
   */
  private Integer ifLawEnforcementUnit;

  /**
   * 编制证号
   */
  private String authorizedStrengthCertificate;

  /**
   * 编制内在职人数
   */
  private Integer authorizedStrengthIncumbents;

  /**
   * 在职人数
   */
  private Integer incumbents;

  /**
   * 执法人员编制数
   */
  private Integer lawEnforcementAuthorizedStrength;

  /**
   * 执法人员实有数
   */
  private Integer lawEnforcementIncumbents;

  /**
   * 正局级领导实有人数
   */
  private Integer directorGeneralLeaderReal;

  /**
   * 正局级非领导职数
   */
  private Integer directorGeneralNoLeaderPosition;

  /**
   * 正局级非领导实有人数
   */
  private Integer directorGeneralNoLeaderReal;

  /**
   * 副局级领导实有人数
   */
  private Integer deputyDirectorGeneralLeaderReal;

  /**
   * 副局级非领导职数
   */
  private Integer deputyDirectorGeneralNoLeaderPosition;

  /**
   * 副局级非领导实有人数
   */
  private Integer deputyDirectorGeneralNoLeaderReal;

  /**
   * 处级编制数
   */
  private Integer divisionAuthorizedStrength;

  /**
   * 处级以下编制数
   */
  private Integer divisionDownAuthorizedStrength;

  /**
   * 退休部级人数
   */
  private Integer retireMinister;

  /**
   * 财政管理办法
   */
  private String financeManage;

  /**
   * 单位财政类型
   */
  private String unitFinanceType;

  /**
   * 所属财政关系
   */
  private String financeRelation;

  /**
   * 是否市级区县
   */
  private Integer ifDistrictCounty;

  /**
   * 是否直管
   */
  private Integer ifDirectlyUnder;

  /**
   * 是否乡镇
   */
  private Integer ifTownship;

  /**
   * 是否街道
   */
  private Integer ifStreet;

  /**
   * 是否政法单位
   */
  private Integer ifPoliticsAndLawUnit;

  /**
   * 是否车改试点单位
   */
  private Integer ifVehicleReformUnit;
}