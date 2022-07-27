package com.person.chenpt.core.base.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * 基础分页
 * @author ding.haiyang
 * @since 2020/7/2
 */
@ApiModel("分页查询")
public abstract class BasePageQuery<T> {
    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    @ApiModelProperty(value = "页码",example = DEFAULT_PAGE_NUM+"")
    protected Integer pageNum = DEFAULT_PAGE_NUM;
    @ApiModelProperty(value = "每页数量",example = DEFAULT_PAGE_SIZE+"")
    protected Integer pageSize = DEFAULT_PAGE_SIZE;

    public Integer getPageNum() {
        return pageNum;
    }
    public void setPageNum(Integer pageNum) {
        if (Objects.nonNull(pageNum) && pageNum > 0 ){
            this.pageNum = pageNum;
        }
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        if (Objects.nonNull(pageSize) && pageSize > 0 ){
            this.pageSize = pageSize;
        }
    }
    public Page<T> cratePage(){
        Page<T> page = new Page<>();
        page.setCurrent(getPageNum());
        page.setSize(getPageSize());
        return page;
    }
    public com.github.pagehelper.Page<T> startPage(){
        return PageHelper.startPage(getPageNum(), getPageSize());
    }
}
