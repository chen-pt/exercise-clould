package com.person.chenpt.core.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author ding.haiyang
 * @since 2022/5/12
 */
public interface ISimpleService<T> {
    /**
     * 批量处理条数
     */
    int DEFAULT_BATCH_SIZE = 1000;

    /**
     * 条件分页查询
     *
     * @param page         翻页对象
     * @param queryWrapper
     * @param <E>
     * @return
     */
    <E extends IPage<T>> E page(E page, Wrapper<T> queryWrapper);

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    boolean saveOrUpdate(T entity);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param list 主键ID或实体列表
     */
    @Transactional(rollbackFor = Exception.class)
    boolean removeByIds(Collection<?> list);
}
