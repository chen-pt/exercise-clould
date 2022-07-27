package com.person.chenpt.core.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.person.chenpt.util.CurrentUserInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 字段填充
 * @author ding.haiyang
 * @since 2020/7/2
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public void insertFill(MetaObject metaObject) {
        log.trace("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", String.class, LocalDateTime.now().format(ofPattern));
        this.strictInsertFill(metaObject, "createUserId", String.class, CurrentUserInfoUtils.getUserId());
        this.strictInsertFill(metaObject, "createUserName", String.class, CurrentUserInfoUtils.getUserName());
        this.strictInsertFill(metaObject, "createDepartmentId", String.class, CurrentUserInfoUtils.getDepartmentId());
        this.strictInsertFill(metaObject, "createDepartmentName", String.class, CurrentUserInfoUtils.getDepartmentName());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.trace("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", String.class, LocalDateTime.now().format(ofPattern));
        this.strictUpdateFill(metaObject, "updateUserId", String.class, CurrentUserInfoUtils.getUserId());
        this.strictUpdateFill(metaObject, "updateUserName", String.class, CurrentUserInfoUtils.getUserName());
        this.strictUpdateFill(metaObject, "updateDepartmentId", String.class, CurrentUserInfoUtils.getDepartmentId());
        this.strictUpdateFill(metaObject, "updateDepartmentName", String.class, CurrentUserInfoUtils.getDepartmentName());
    }
}