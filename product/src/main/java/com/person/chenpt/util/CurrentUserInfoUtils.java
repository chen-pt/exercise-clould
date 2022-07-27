package com.person.chenpt.util;


import com.person.chenpt.core.base.model.BaseEntity;
import com.person.chenpt.core.base.model.SystemUser;
import com.person.chenpt.core.feign.model.Organization;
import com.sun.istack.internal.NotNull;

/**
 * 当前用户信息工具类
 * 获取用户信息，设置 BaseEntity 信息
 * @author ding.haiyang
 * @since 2020/9/8
 */
public class CurrentUserInfoUtils {
    private final static ThreadLocal<String> USER_ID = new ThreadLocal<String>();
    private final static ThreadLocal<String> USER_NAME = new ThreadLocal<String>();
    private final static ThreadLocal<String> DEPARTMENT_ID = new ThreadLocal<String>();
    private final static ThreadLocal<String> DEPARTMENT_NAME = new ThreadLocal<String>();
    private final static ThreadLocal<SystemUser> USER = new ThreadLocal<SystemUser>();
    private final static ThreadLocal<Organization> ORGANIZATION = new ThreadLocal<Organization>();
    public static void clearCreateInfo(@NotNull BaseEntity entity){
        entity.setCreateUserId(null);
        entity.setCreateUserName(null);
        entity.setCreateDepartmentId(null);
        entity.setCreateDepartmentName(null);
        entity.setCreateTime(null);
    }
    public static void clearUpdateInfo(@NotNull BaseEntity entity){
        entity.setUpdateUserId(null);
        entity.setUpdateUserName(null);
        entity.setUpdateDepartmentId(null);
        entity.setUpdateDepartmentName(null);
        entity.setUpdateTime(null);
    }
    public static void clearBaseEntity(@NotNull BaseEntity entity){
        clearUpdateInfo(entity);
        clearCreateInfo(entity);
    }

    /**
     * 清空ThreadLocal中的数据
     */
    public static void removeAll(){
        USER_ID.remove();
        USER_NAME.remove();
        DEPARTMENT_ID.remove();
        DEPARTMENT_NAME.remove();
        USER.remove();
        ORGANIZATION.remove();
    }
    public static void setInfo(String userId,String userName,String departmentId,String departmentName){
        USER_ID.set(userId);
        USER_NAME.set(userName);
        DEPARTMENT_ID.set(departmentId);
        DEPARTMENT_NAME.set(departmentName);
    }
    public static String[] getInfo(){
        return new String[]{USER_ID.get(),USER_NAME.get(),DEPARTMENT_ID.get(),DEPARTMENT_NAME.get()};
    }
    public static void setEntityCreateInfo(@NotNull BaseEntity entity){
        entity.setCreateUserId(USER_ID.get());
        entity.setCreateUserName(USER_NAME.get());
        entity.setCreateDepartmentId(DEPARTMENT_ID.get());
        entity.setCreateDepartmentName(DEPARTMENT_NAME.get());
    }
    public static void setEntityUpdateInfo(@NotNull BaseEntity entity){
        entity.setUpdateUserId(USER_ID.get());
        entity.setUpdateUserName(USER_NAME.get());
        entity.setUpdateDepartmentId(DEPARTMENT_ID.get());
        entity.setUpdateDepartmentName(DEPARTMENT_NAME.get());
    }

    public static SystemUser getSystemUser() {
        return USER.get();
    }

    public static void setSystemUser(SystemUser systemUser) {
        USER.set(systemUser);
    }

    public static String getUserId() {
        return USER_ID.get();
    }

    public static void setUserId(String userId) {
        USER_ID.set(userId);
    }

    public static String getUserName() {
        return USER_NAME.get();
    }

    public static void setUserName(String userName) {
        USER_NAME.set(userName);
    }

    public static String getDepartmentId() {
        return DEPARTMENT_ID.get();
    }

    public static void setDepartmentId(String departmentId) {
        DEPARTMENT_ID.set(departmentId);
    }

    public static String getDepartmentName() {
        return DEPARTMENT_NAME.get();
    }

    public static void setDepartmentName(String departmentName) {
        DEPARTMENT_NAME.set(departmentName);
    }

    public static Organization getOrganization() {
        return ORGANIZATION.get();
    }

    public static void setOrganization(Organization organization) {
        ORGANIZATION.set(organization);
    }
}
