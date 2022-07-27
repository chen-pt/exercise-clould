package com.person.chenpt.core.shiro;

import com.person.chenpt.core.feign.model.Organization;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author ding.haiyang
 * @date 2020/6/30
 */
@Data
public class JwtUser implements Serializable {

    private String id;

    private String loginName;

    private String userName;

    private String tenantId;

    private String email;

    private String mobile;

    private String password;

    private String salt;

    /**
     * 单位 机管局
     */
    private Organization unit;

    /**
     * 部门 机管局->信息中心
     */
    private Organization depart;

    private boolean enabled = true;

    private Set<String> roles;

    private Set<String> permissions;

}
