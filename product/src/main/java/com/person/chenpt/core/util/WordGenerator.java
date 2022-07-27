package com.person.chenpt.core.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 支持根据.docx模板文件中mark，填充（替换标记）需要的内容。
 * <p>
 * 注意：目不支.docx中的table增加行的填充
 * <p>
 * 支持合并多个XWPFDocument。
 * <p>
 * 支持指定位置的输出。
 *
 * @author khaoth
 */
public class WordGenerator {
	// .docx模板文件。
	private XWPFDocument xwpfDocumentTemplate = null;
	
	// 默认的模板路径。
	private final String DEFAULT_FILE_CLASS_PATH = "/templates/";
	
	// 记录模板mark第一部分(第一部分为“$”)在paragraph中xwpfRun的位置。
	private Integer mPart1 = null;
	
	// 记录模板mark第二部分(第二部分为“{”)在paragraph中xwpfRun的位置。
	private Integer mPart2 = null;
	
	// 记录模板mark第三部分(第三部分为标记名)。
	private StringBuilder mPart3 = null;
	
	// 记录模板mark第四部分(第四部分为“}”)在paragraph中xwpfRun的位置。
	private Integer mPart4 = null;
	
	// 记录第一部分除“$”以外的文本。
	private String mPart1IncText = null;
	
	// 记录第四部分除“}”以外的文本。
	private String mPart4IncText = null;
	
	public WordGenerator(String fileName) throws Exception {
		this.xwpfDocumentTemplate = new XWPFDocument(this.getClass().getResourceAsStream(this.DEFAULT_FILE_CLASS_PATH + fileName));
	}
	
	public WordGenerator(String absoluteFilePath, String fileName) throws Exception {
		this.xwpfDocumentTemplate = new XWPFDocument(new FileInputStream(new File(absoluteFilePath + fileName)));
	}
	
	/**
	 * 根据.docx模板，创建XWPFDocument对象。
	 *
	 * @return
	 * @throws Exception
	 */
	public XWPFDocument createXWPFDocumentByTemplate() throws Exception {
		return new XWPFDocument(this.xwpfDocumentTemplate.getPackage());
	}
	
	/**
	 * 根据.docx模板，创建多个XWPFDocument对象。
	 *
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public List<XWPFDocument> createXWPFDocumentByTemplate(Integer number) throws Exception {
		List<XWPFDocument> xwpfDocumentList = new ArrayList<XWPFDocument>();
		for (int i = 0; i < number; i++) {
			xwpfDocumentList.add(new XWPFDocument(this.xwpfDocumentTemplate.getPackage()));
		}
		return xwpfDocumentList;
	}
	
	/**
	 * 设置XWPFDocument段落中的XWPFRun对象的文本。
	 *
	 * @param paraList XWPFDocument中的XWPFParagraph对象
	 * @param textMap
	 * @throws Exception
	 */
	public void setXWPFRunText(List<XWPFParagraph> paraList, Map<String, String> textMap) throws Exception {
		if (ObjectUtil.isNotEmpty(paraList)) {
			for (XWPFParagraph xwpfParagraph : paraList) {
				List<XWPFRun> xwpfRunList = xwpfParagraph.getRuns();
				this.initAllMarkPart();
				for (int i = 0, il = xwpfRunList.size(); i < il; i++) {
					XWPFRun xwpfRun = xwpfRunList.get(i);
					// 分析XWPFRun中的Text的mark，并记录标记在xwpfRunList中的位置。
					this.analyseXWPFRunText(xwpfRun, i);
					// 当存在标记的起始位置和结束位置，说明已匹配到了需要替换的mark。
					if (mPart1 != -1 && mPart4 != -1) {
						// 替换xwpfRunList中被标记部分的内容。
						this.replaceXWPFRunMark(xwpfRunList, textMap);
					}
				}
			}
		}
	}
	
	/**
	 * 设置XWPFDocument中table单元格里的XWPFRun对象的文本。
	 *
	 * @param tableList
	 * @param textMap
	 * @throws Exception
	 */
	public void setXWPFTableText(List<XWPFTable> tableList, Map<String, String> textMap) throws Exception {
		
		if (ObjectUtil.isNotEmpty(tableList)) {
			for (XWPFTable xwpfTable : tableList) {
				// 获取一行数据
				for (int i = 0, il = xwpfTable.getNumberOfRows(); i < il; i++) {
					
					XWPFTableRow row = xwpfTable.getRow(i);
					// 获取一行中的一列的数据
					List<XWPFTableCell> cells = row.getTableCells();
					
					for (XWPFTableCell cell : cells) {
						// 获取单元格中的所有的段落，交由setXWPFRunText处理
						this.setXWPFRunText(cell.getParagraphs(), textMap);
					}
				}
			}
		}
	}
	
	/**
	 * 初始化标记部分的初始值。
	 */
	private void initAllMarkPart() {
		this.mPart1 = -1;
		this.mPart2 = -1;
		this.mPart3 = new StringBuilder();
		this.mPart4 = -1;
		this.mPart1IncText = "";
		this.mPart4IncText = "";
	}
	
	/**
	 * 分析段落中xwpfRun包含的标记，
	 * <p>
	 * i记录了XWPFParagraph中xwpfRun的位置。
	 *
	 * @param XWPFRun
	 * @param i
	 */
	private void analyseXWPFRunText(XWPFRun xwpfRun, int i) {
		
		String xwpfRunText = xwpfRun.toString().trim();
		
		if (StrUtil.isNotBlank(xwpfRunText)) {
			/*
			 * 由于XWPFRun中文本包含内容的不确定性，拆分为char判断， 该XWPFRun是否包含mark的关键字。
			 */
			char[] words = xwpfRunText.toCharArray();
			for (char word : words) {
				// $为标记的起始部分。
				if ('$' == word) {
					// 如果已经记录的起始部分的位置，但有出现$，那么重置markPart。
					if (mPart1 != -1) {
						this.initAllMarkPart();
					}
					// 记录第一部分关键字在此段落的位置。
					mPart1 = i;
					/*
					 * 如果xwpfRunText中包含“$”，并且不是在第一个位置，为了替换mark时的正确性， 记录“$”以外的文本内容。
					 */
					int index = xwpfRunText.indexOf("$");
					if (index != 0) {
						// 一定是截取xwpfRunText中“$”之前的部分。
						mPart1IncText = xwpfRunText.substring(0, index);
					}
					continue;
				}
				
				// 当标记的第二部分（“{”）没有记录位置，以及word为标志“}”
				if (mPart2 == -1 && '{' == word) {
					// 记录第二部分关键字在此段落的位置。
					mPart2 = i;
					continue;
				}
				
				// 只有标记的第一和第二部分有位置记录时，并且不是标记的结束标志“}”
				if (mPart1 != -1 && mPart2 != -1 && '}' != word) {
					// 记录一个mark中的完整标记名。
					mPart3.append(word);
					continue;
				}
				
				// 当标记的第四部分（“}”）没有记录位置，以及word为关键标志“}”
				if (mPart4 == -1 && '}' == word) {
					mPart4 = i;
					/*
					 * 如果xwpfRunText中包含“}”，并且它并不在xwpfRunText的末尾，为了替换mark时的正确性， 记录“}”以外的文本内容。
					 */
					int index = xwpfRunText.lastIndexOf("}");
					if (index != xwpfRunText.length() - 1) {
						// 一定是截取xwpfRunText中“}”之后的部分。
						mPart4IncText = xwpfRunText.substring(index, xwpfRunText.length());
					}
					break;
				}
			}
		}
	}
	
	/**
	 * 替换xwpfRunList中被标记部分的内容。
	 * <p>
	 * 只适用于通过记录mark在段落中位置后替换内容。
	 *
	 * @param xwpfRunList
	 * @param textMap
	 */
	private void replaceXWPFRunMark(List<XWPFRun> xwpfRunList, Map<String, String> textMap) {
		
		// 当存在标记的起始位置和结束位置，说明已匹配到了需要替换的mark。
		if (mPart1 != -1 && mPart4 != -1) {
			
			// 把xwpfRunList中的标记至为空。
			for (int j = mPart1; j <= mPart4; j++) {
				xwpfRunList.get(j).setText("", 0);
			}
			// 获取标记名。
			String key = mPart3.toString().trim();
			if (!"".equals(key)) {
				// 通过标记名获取需要替换的内容。
				String text = textMap.get(key);
				if (text != null) {
					xwpfRunList.get(mPart1).setText(mPart1IncText + text + mPart4IncText);
				}
			}
			this.initAllMarkPart();
		}
	}
	
	/**
	 * 合并XWPFDocument。
	 * <p>
	 * 把xwpfDocumentList的其他xwpfDocument，合并至第一个xwpfDocument。
	 *
	 * @param xwpfDocumentList
	 * @return
	 * @throws Exception
	 */
	public XWPFDocument mergeXWPFDocument(List<XWPFDocument> xwpfDocumentList) throws Exception {
		
		if (ObjectUtil.isNotEmpty(xwpfDocumentList)) {
			
			XWPFDocument xwpfDocument = xwpfDocumentList.get(0);
			
			if (xwpfDocumentList.size() > 1) {
				
				XmlOptions xmlOptions = new XmlOptions().setSaveOuter();
				// 创建新的一页
				xwpfDocument.createParagraph().createRun().addBreak(BreakType.PAGE);
				CTBody srcCTBody = xwpfDocument.getDocument().getBody();
				String srcText = srcCTBody.xmlText();
				String prefix = srcText.substring(0, srcText.indexOf(">") + 1);
				String mainPart = srcText.substring(srcText.indexOf(">") + 1, srcText.lastIndexOf("<"));
				String suffix = srcText.substring(srcText.lastIndexOf("<"));
				
				StringBuilder content = new StringBuilder(prefix).append(mainPart);
				
				// xwpfDocumentList中第二个开始的xwpfDocument合并至第一个xwpfDocument中。
				for (int i = 1, il = xwpfDocumentList.size() - 1; i <= il; i++) {
					
					XWPFDocument appendXWPFDocument = xwpfDocumentList.get(i);
					// 当时不是最后一个XWPFDocument时，创建新的一页。
					if (i != il) {
						appendXWPFDocument.createParagraph().createRun().addBreak(BreakType.PAGE);
					}
					// 根据xmlOptions的配置项（过滤不获取的xml节点），获取被合并的xwpfDocument中内容的字符串。
					String appendText = appendXWPFDocument.getDocument().getBody().xmlText(xmlOptions);
					String appendPart = appendText.substring(appendText.indexOf(">") + 1, appendText.lastIndexOf("<"));
					content.append(appendPart);
				}
				// 重新把字符串变为xml对象，并合并至xwpfDocumentList的第一个xwpfDocument。
				srcCTBody.set(CTBody.Factory.parse(content.append(suffix).toString()));
			}
			return xwpfDocument;
		}
		return null;
	}
	
	/**
	 * 把xwpfDocument变为docx文件，并输出至指定位置。
	 * <p>
	 * fileName文件名必须以.docx为后缀
	 *
	 * @param xwpfDocument
	 * @param outputPath
	 * @param fileName
	 * @throws Exception
	 */
	public void outputXWPFDocument(XWPFDocument xwpfDocument, String outputPath, String fileName) throws Exception {
		
		try (OutputStream outputStream = new FileOutputStream(new File(outputPath + fileName))) {
			xwpfDocument.write(outputStream);
		}
	}
	
}
