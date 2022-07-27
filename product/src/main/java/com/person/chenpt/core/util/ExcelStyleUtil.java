package com.person.chenpt.core.util;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams;
import cn.afterturn.easypoi.excel.export.styler.AbstractExcelExportStyler;
import com.person.chenpt.core.util.excel.IExcelExportStyler;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.awt.Color;

public class ExcelStyleUtil extends AbstractExcelExportStyler implements IExcelExportStyler {
	
	private static final short STRING_FORMAT = (short) BuiltinFormats.getBuiltinFormat("TEXT");
	private static final short FONT_SIZE_TEN = 10;
	private static final short FONT_SIZE_ELEVEN = 11;
	private static final short FONT_SIZE_TWELVE = 12;
	private static final double FONT_SIZE_THIRTEEN = 13.5;
	
	/**
	 * 大标题样式
	 */
	private XSSFCellStyle headerStyle;
	/**
	 * 每列标题样式
	 */
	private XSSFCellStyle titleStyle;
	/**
	 * 数据行样式
	 */
	private XSSFCellStyle styles;
	
	public ExcelStyleUtil(Workbook workbook) {
		this.init(workbook);
	}
	
	/**
	 * 初始化样式
	 *
	 * @param workbook
	 */
	private void init(Workbook workbook) {
		this.headerStyle = initHeaderStyle(workbook);
		this.titleStyle = initTitleStyle(workbook);
		this.styles = initStyles(workbook);
	}
	
	/**
	 * 大标题样式
	 *
	 * @param color
	 * @return
	 */
	@Override
	public XSSFCellStyle getHeaderStyle(short color) {
		return headerStyle;
	}
	
	/**
	 * 每列标题样式
	 *
	 * @param color
	 * @return
	 */
	@Override
	public XSSFCellStyle getTitleStyle(short color) {
		return titleStyle;
	}
	
	/**
	 * 数据行样式
	 *
	 * @param parity 可以用来表示奇偶行
	 * @param entity 数据内容
	 * @return 样式
	 */
	@Override
	public XSSFCellStyle getStyles(boolean parity, ExcelExportEntity entity) {
		return styles;
	}
	
	/**
	 * 获取样式方法
	 *
	 * @param dataRow 数据行
	 * @param obj 对象
	 * @param data 数据
	 */
	@Override
	public XSSFCellStyle getStyles(Cell cell, int dataRow, ExcelExportEntity entity, Object obj, Object data) {
		return getStyles(true, entity);
	}
	
	/**
	 * 模板使用的样式设置
	 */
	@Override
	public XSSFCellStyle getTemplateStyles(boolean isSingle, ExcelForEachParams excelForEachParams) {
		return null;
	}
	
	/**
	 * 初始化--大标题样式
	 *
	 * @param workbook
	 * @return
	 */
	private XSSFCellStyle initHeaderStyle(Workbook workbook) {
		XSSFCellStyle style = getBaseCellStyle(workbook);
		style.setFont(getFont(workbook, FONT_SIZE_THIRTEEN, true));
		// 背景色
		style.setFillForegroundColor(new XSSFColor(new Color(208, 241, 255)));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}
	
	/**
	 * 初始化--每列标题样式
	 *
	 * @param workbook
	 * @return
	 */
	private XSSFCellStyle initTitleStyle(Workbook workbook) {
		XSSFCellStyle style = getBaseCellStyle(workbook);
		style.setFont(getFont(workbook, FONT_SIZE_THIRTEEN, true));
		// 背景色
		// style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		// style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}
	
	/**
	 * 初始化--数据行样式
	 *
	 * @param workbook
	 * @return
	 */
	private XSSFCellStyle initStyles(Workbook workbook) {
		XSSFCellStyle style = getBaseCellStyle(workbook);
		style.setFont(getFont(workbook, FONT_SIZE_TEN, false));
		style.setDataFormat(STRING_FORMAT);
		return style;
	}
	
	/**
	 * 基础样式
	 *
	 * @return
	 */
	private XSSFCellStyle getBaseCellStyle(Workbook workbook) {
		XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
		// 下边框
		style.setBorderBottom(BorderStyle.THIN);
		// 左边框
		style.setBorderLeft(BorderStyle.THIN);
		// 上边框
		style.setBorderTop(BorderStyle.THIN);
		// 右边框
		style.setBorderRight(BorderStyle.THIN);
		// 水平居中
		style.setAlignment(HorizontalAlignment.CENTER);
		// 上下居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置自动换行
		style.setWrapText(true);
		return style;
	}
	
	/**
	 * 字体样式
	 *
	 * @param size 字体大小
	 * @param isBold 是否加粗
	 * @return
	 */
	private Font getFont(Workbook workbook, double size, boolean isBold) {
		XSSFFont font = (XSSFFont) workbook.createFont();
		// 字体样式
		font.setFontName("宋体");
		// 是否加粗
		font.setBold(isBold);
		// 字体大小
		font.setFontHeight(size);
		// font.setFontHeightInPoints(size);
		return font;
	}
}
