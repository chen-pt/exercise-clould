#springboot集成es6.8版本示例
创建索引
#####索引、mapping创建最好在kibana中执行  Java操作不太方便
```
PUT cestest
{"mappings": {
  "_doc":{
  "properties": {
    "esId":{"type": "keyword"},
    "name":{"type": "text","analyzer": "ik_max_word","search_analyzer": "ik_max_word"},
    "age":{"type": "integer"}
  }
  }
}
}
```
ES分词器
```
ES中存在三种模式的分词：Ik（ik_smart 、 ik_max_word）、standard（es自带的）。
如果我们不指定分词模式，则默认会执行standard，语句被拆分成一个一个字。
而ik_max_word是最细粒度的拆分，也是ik默认的，ik_smart是做最粗粒度的拆分。
```

#2021-12-17 17:11整合easyExcel文件导出
#注解使用资料https://blog.csdn.net/weixin_45151960/article/details/109095332
#和poi相比 poi是基于内存操作 操作大数据量时容易引发oom内存溢出，easyExcel基于磁盘操作不会引发内存溢出
```
<!--easyexcel-->
<dependency>
  <groupId>com.alibaba</groupId>
  <artifactId>easyexcel</artifactId>
  <version>2.2.6</version>
</dependency>
注解
@ExcelProperty
@ColumnWith 列宽
@ContentFontStyle 文本字体样式
@ContentLoopMerge 文本合并
@ContentRowHeight 文本行高度
@ContentStyle 文本样式
@HeadFontStyle 标题字体样式
@HeadRowHeight 标题高度
@HeadStyle 标题样式
@ExcelIgnore 忽略项
@ExcelIgnoreUnannotated 忽略未注解
```





















