package com.person.chenpt.core.base.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author ding.haiyang
 * @date 2019/6/3
 */
@ApiModel(description = "分页实体")
public class PageEntity<T> {

  /**
   * 页码，从1开始
   */
  @ApiModelProperty("页码，从1开始")
  private int pageNum;
  /**
   * 页面大小
   */
  @ApiModelProperty("页面大小")
  private int pageSize;

  /**
   * 总数
   */
  @ApiModelProperty("总数")
  private long total;
  /**
   * 总页数
   */
  @ApiModelProperty("总页数")
  private int pages;

  /**
   * 数据
   */
  @ApiModelProperty("数据")
  private List<T> list;

  public static <T> PageEntity<T> build(IPage<T> page) {
    PageEntity<T> pageEntity = new PageEntity<>();
    pageEntity.setTotal(page.getTotal())
      .setPageNum((int) page.getCurrent())
      .setPageSize((int) page.getSize())
      .setPages((int) page.getPages())
      .setList(page.getRecords());
    return pageEntity;
  }

  public static <T> PageEntity<T> buildForPagehelper(com.github.pagehelper.Page<T> page) {
    PageEntity<T> pageEntity = new PageEntity<>();
    pageEntity.setTotal(page.getTotal())
      .setPageNum(page.getPageNum())
      .setPageSize(page.getPageSize())
      .setPages(page.getPages())
      .setList(page);
    return pageEntity;
  }

  /**
   * 内存分页
   *
   * @param page
   * @param records
   * @param <T>
   * @return
   */
  public static <T> PageEntity<T> buildRam(BasePageQuery<T> page, List<T> records) {
    int i = PageUtil.totalPage(records.size(), page.getPageSize());
    PageEntity<T> pageEntity = new PageEntity<>();
    pageEntity.setTotal(records.size())
      .setPageNum(page.getPageNum())
      .setPageSize(page.getPageSize())
      .setPages(i)
      .setList(CollUtil.page(page.getPageNum() - 1, page.getPageSize(), records));
    return pageEntity;
  }

  public static <T> PageEntity<T> build(int pageNum, int pageSize, long total, int pages, List<T> records) {
    return new PageEntity<>(pageNum, pageSize, total, pages, records);
  }

  public PageEntity() {
  }

  public PageEntity(int pageNum, int pageSize, long total, int pages, List<T> list) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.total = total;
    this.pages = pages;
    this.list = list;
  }

  public PageEntity(int pageNum, int pageSize, long total, int pages) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.total = total;
    this.pages = pages;
  }

  public <V> PageEntity(PageEntity<V> pageEntity, List<T> records) {
    this.pageNum = pageEntity.pageNum;
    this.pageSize = pageEntity.pageSize;
    this.total = pageEntity.total;
    this.pages = pageEntity.pages;
    this.list = records;
  }

  /**
   * 转换泛型
   *
   * @param pageEntity 原始分页信息
   * @param records    转变后数据记录
   * @param <T>        原始泛型
   * @param <K>        转变后的泛型
   * @return 转换泛型分页
   */
  public static <T, K> PageEntity<K> wrapper(PageEntity<T> pageEntity, List<K> records) {
    return new PageEntity<>(pageEntity, records);
  }

  public int getPageNum() {
    return pageNum;
  }

  public PageEntity<T> setPageNum(int pageNum) {
    this.pageNum = pageNum;
    return this;
  }

  public int getPageSize() {
    return pageSize;
  }

  public PageEntity<T> setPageSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  public long getTotal() {
    return total;
  }

  public PageEntity<T> setTotal(long total) {
    this.total = total;
    return this;
  }

  public int getPages() {
    return pages;
  }

  public PageEntity<T> setPages(int pages) {
    this.pages = pages;
    return this;
  }

  public List<T> getList() {
    return list;
  }

  public PageEntity<T> setList(List<T> list) {
    this.list = list;
    return this;
  }
}
