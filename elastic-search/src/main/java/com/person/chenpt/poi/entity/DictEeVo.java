package com.person.chenpt.poi.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated  //忽略没有ExcelProperty注解的属性
public class DictEeVo {
	//设置excel表头名称，index表示对应的第几列
	@ExcelProperty(value = "id" ,index = 0)
	@ColumnWidth(value = 15)
	private Long id;

	@ExcelProperty(value = "上级id" ,index = 1)
	private Long parentId;

	@ColumnWidth(value = 20)
	@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER,verticalAlignment = VerticalAlignment.CENTER)
	@ExcelProperty(value = "名称" ,index = 2)
	private String name;

	@ExcelProperty(value = "值" ,index = 3)
	private String value;

	private String dictCode;

}