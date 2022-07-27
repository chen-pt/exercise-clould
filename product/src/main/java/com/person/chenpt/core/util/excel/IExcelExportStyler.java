package com.person.chenpt.core.util.excel;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.util.Removal;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public interface IExcelExportStyler {
	
	/**
	 * 列表头样式
	 */
	public XSSFCellStyle getHeaderStyle(short headerColor);
	
	/**
	 * 标题样式
	 */
	public XSSFCellStyle getTitleStyle(short color);
	
	/**
	 * 获取样式方法
	 */
	@Deprecated
	@Removal(version = "4.2")
	public XSSFCellStyle getStyles(boolean parity, ExcelExportEntity entity);
	
	/**
	 * 获取样式方法
	 *
	 * @param dataRow 数据行
	 * @param obj 对象
	 * @param data 数据
	 */
	public XSSFCellStyle getStyles(Cell cell, int dataRow, ExcelExportEntity entity, Object obj, Object data);
	
	/**
	 * 模板使用的样式设置
	 */
	public XSSFCellStyle getTemplateStyles(boolean isSingle, ExcelForEachParams excelForEachParams);
	
}
