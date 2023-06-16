package com.person.chenpt.core.base.model;

/**
 * 编码类型，对状态或类型枚举使用
 */
public interface Code<C> {

    /**
     * code编码值
     * @return
     */
    C getCode();

    /**
     * 名称
     * @return
     */
    String getName();

    /**
     * 描述
     * @return
     */
    String getDescription();
}