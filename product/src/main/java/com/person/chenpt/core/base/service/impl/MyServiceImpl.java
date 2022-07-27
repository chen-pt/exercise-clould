package com.person.chenpt.core.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.collect.Lists;
import com.person.chenpt.core.util.Constants;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 解决逻辑删除不更新填充字段
 * @author ding.haiyang
 * @since  2020/7/1
 */
public class MyServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IService<T> {
    private T et(){
        T o= null;
        try {
            o = (T) entityClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  o;
    }
    @Override
    public boolean removeById(Serializable id) {
        UpdateWrapper<T> tUpdateWrapper = new UpdateWrapper<>(et());
        tUpdateWrapper.set("IS_DELETE", Constants.Delete.IS_DELETE);
        tUpdateWrapper.eq("id",id);
        return SqlHelper.retBool(getBaseMapper().update(et(),tUpdateWrapper));
    }
    @Override
    public boolean removeByMap(Map<String, Object> columnMap) {
        Assert.notEmpty(columnMap, "error: columnMap must not be empty");
        UpdateWrapper<T> tUpdateWrapper = new UpdateWrapper<>();
        tUpdateWrapper.set("IS_DELETE",1);
        tUpdateWrapper.allEq(columnMap);
        return SqlHelper.retBool(getBaseMapper().update(et(),tUpdateWrapper));
    }

    /**
     * 不推荐使用
     * @param queryWrapper
     * @return
     */
    @Override
    public boolean remove(Wrapper<T> queryWrapper) {
        UpdateWrapper<T> tUpdateWrapper = new UpdateWrapper<>();
        tUpdateWrapper.set("IS_DELETE",Constants.Delete.IS_DELETE);
        List<Map<String, Object>> mapList = listMaps(queryWrapper);
        List<Object> idList = Lists.newArrayList();
        for (Map<String, Object> map : mapList) {
            Object id = map.get("ID");
            idList.add(id);
        }
        tUpdateWrapper.in("id",idList);
        return SqlHelper.retBool(getBaseMapper().update(et(),tUpdateWrapper));
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return false;
        }
        UpdateWrapper<T> tUpdateWrapper = new UpdateWrapper<>();
        tUpdateWrapper.set("IS_DELETE",Constants.Delete.IS_DELETE);
        tUpdateWrapper.in("id",idList);
        return SqlHelper.retBool(getBaseMapper().update(et(),tUpdateWrapper));
    }
}
