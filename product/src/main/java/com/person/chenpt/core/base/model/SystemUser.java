package com.person.chenpt.core.base.model;


import com.person.chenpt.core.shiro.JwtUser;

/**
 * 系统管理平台用户信息
 * @author: ding.haiyang
 * @date: 2019-3-11
 */
public class SystemUser {

    /**
     * abbreviation :
     * address :
     * age : 0
     * approvalStatus : 0
     * beginValidTime :
     * birthDay :
     * comments :
     * createTime : 20180123 113709
     * createUser : f0c5b1d761a542d6b0147802c99848ae
     * email :
     * endValidTime :
     * fax :
     * gender : 0
     * id : 0AE72B2CA8F944C4A692910728ADCE5F
     * identityId :
     * isSuperadmin : 1
     * loginName : liyn
     * mobile : 18019789995
     * modifyTime :
     * modifyUser :
     * name : 李永宁
     * organizeId :
     * picUrl :
     * showOrder : 5
     * status : 0
     * telephone :
     * tenantId : 27da667769d44d609db77f0323166d1e
     * unitId : 27da667769d44d609db77f0323166d1e
     * isOperator : false
     */

    private String abbreviation;
    private String address;
    private Integer age;
    private Integer approvalStatus;
    private String beginValidTime;
    private String birthDay;
    private String comments;
    private String createTime;
    private String createUser;
    private String email;
    private String endValidTime;
    private String fax;
    private Integer gender;
    private String id;
    private String identityId;
    private Integer isSuperadmin;
    private String loginName;
    private String mobile;
    private String modifyTime;
    private String modifyUser;
    private String name;
    private String organizeId;
    private String picUrl;
    private Integer showOrder;
    private Integer status;
    private String telephone;
    private String tenantId;
    private String unitId;
    private String isOperator;

    public SystemUser() {
    }


    public SystemUser(JwtUser iUser) {
        this.email = iUser.getEmail();
        this.id = iUser.getId();
        this.loginName = iUser.getLoginName();
        this.mobile = iUser.getMobile();
        this.name = iUser.getUserName();
        this.tenantId = iUser.getTenantId();
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public SystemUser setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public SystemUser setAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public SystemUser setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public SystemUser setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
        return this;
    }

    public String getBeginValidTime() {
        return beginValidTime;
    }

    public SystemUser setBeginValidTime(String beginValidTime) {
        this.beginValidTime = beginValidTime;
        return this;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public SystemUser setBirthDay(String birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public SystemUser setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public SystemUser setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreateUser() {
        return createUser;
    }

    public SystemUser setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SystemUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEndValidTime() {
        return endValidTime;
    }

    public SystemUser setEndValidTime(String endValidTime) {
        this.endValidTime = endValidTime;
        return this;
    }

    public String getFax() {
        return fax;
    }

    public SystemUser setFax(String fax) {
        this.fax = fax;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public SystemUser setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public String getId() {
        return id;
    }

    public SystemUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getIdentityId() {
        return identityId;
    }

    public SystemUser setIdentityId(String identityId) {
        this.identityId = identityId;
        return this;
    }

    public Integer getIsSuperadmin() {
        return isSuperadmin;
    }

    public SystemUser setIsSuperadmin(Integer isSuperadmin) {
        this.isSuperadmin = isSuperadmin;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public SystemUser setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public SystemUser setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public SystemUser setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public SystemUser setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
        return this;
    }

    public String getName() {
        return name;
    }

    public SystemUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public SystemUser setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public SystemUser setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public SystemUser setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public SystemUser setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public SystemUser setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getTenantId() {
        return tenantId;
    }

    public SystemUser setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getUnitId() {
        return unitId;
    }

    public SystemUser setUnitId(String unitId) {
        this.unitId = unitId;
        return this;
    }

    public String getIsOperator() {
        return isOperator;
    }

    public SystemUser setIsOperator(String isOperator) {
        this.isOperator = isOperator;
        return this;
    }
}
