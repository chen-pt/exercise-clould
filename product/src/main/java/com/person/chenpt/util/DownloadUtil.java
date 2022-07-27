package com.person.chenpt.util;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import com.alibaba.excel.EasyExcel;
import com.cesgroup.jgj.server.bus.compile.utils.ExcelStyleUtil;
import com.cesgroup.jgj.server.bus.compile.utils.WordGenerator;
import com.cesgroup.jgj.server.bus.compile.utils.WordUtils;
import com.cesgroup.jgj.server.bus.compile.utils.excel.ExcelExportService;
import feign.Response;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * SERVLET HTTP 下载工具类
 *
 * @author ding.haiyang
 * @date 2019-4-29
 */
public class DownloadUtil {
  private DownloadUtil() {
  }

  private static final Logger logger = LoggerFactory.getLogger(DownloadUtil.class);
  private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

  public static void httpDownload(HttpServletResponse response, String fileName, InputStream in) throws IOException {
    httpDownload(response, fileName, in, DEFAULT_CONTENT_TYPE);
  }

  public static void httpDownload(HttpServletResponse response, String fileName, byte[] date) throws IOException {
    httpDownload(response, fileName, date, DEFAULT_CONTENT_TYPE);
  }

  public static void httpDownload(HttpServletResponse response, String fileName, byte[] date, String contentType) throws IOException {
    httpDownload(response, fileName, IoUtil.toStream(date), contentType);
  }

  public static void httpDownload(HttpServletResponse response, String fileName, InputStream in, String contentType) throws IOException {
    ServletOutputStream out = null;
    try {
      response.reset();
      logger.info("文件下载：{}", fileName);
      response.setContentType(contentType);
      //response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + CharsetUtil.convert(fileName, CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1));
      response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + URLEncoder.encode(fileName, CharsetUtil.UTF_8));
      out = response.getOutputStream();
      // 写到输出流
      IoUtil.copy(in, out);
    } finally {
      IoUtil.close(out);
      IoUtil.close(in);
    }
  }

  /**
   * 构建 Feign 文件
   *
   * @param response Feign响应
   * @return spring响应实体
   * @throws IOException 异常
   */
  public static ResponseEntity<byte[]> buildResponseEntityByFeignResponse(Response response) throws IOException {
    Collection<String> contentDisposition = response.headers().get(HttpHeaders.CONTENT_DISPOSITION);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.addAll(HttpHeaders.CONTENT_DISPOSITION, new ArrayList<>(contentDisposition));
    return ResponseEntity.ok().headers(httpHeaders).body(IoUtil.readBytes(response.body().asInputStream()));
  }

  /**
   * 构建
   *
   * @param in       流
   * @param fileName 文件名
   * @return spring响应实体
   */
  public static ResponseEntity<byte[]> buildResponseEntity(InputStream in, String fileName) {
    return buildResponseEntity(IoUtil.readBytes(in), fileName);
  }

  /**
   * 构建
   *
   * @param fileName 文件名
   * @return spring响应实体
   */
  public static ResponseEntity<byte[]> buildResponseEntity(byte[] data, String fileName) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION,
      "attachment;fileName=" + CharsetUtil.convert(fileName, CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1));
    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
    return ResponseEntity.ok().headers(httpHeaders).body(data);
  }

  /**
   * 导出excel
   *
   * @param list     导出数据
   * @param fileName 文件名
   * @param clazz    导出实体类
   */
  public static void export(List list, String fileName, Class clazz, HttpServletResponse response) throws Exception {
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
    response.setCharacterEncoding(CharsetUtil.UTF_8);
    fileName = URLEncoder.encode(fileName, CharsetUtil.UTF_8).replaceAll("\\+", "%20");
    // 设定文件名称
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".xlsx");
    ServletOutputStream out = response.getOutputStream();
    EasyExcel.write(out, clazz).sheet("sheet1").doWrite(list);
  }

  /**
   * 导出excelPoi
   *
   * @param entityList
   * @param list       导出数据
   * @param fileName   文件名
   * @param response
   */
  public static void exportPoi(List<ExcelExportEntity> entityList, List list, String fileName, HttpServletResponse response) throws Exception {
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
    response.setCharacterEncoding(CharsetUtil.UTF_8);
    // 设定文件名称
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fileName + DateUtil.today(), CharsetUtil.UTF_8).replaceAll("\\+", "%20") + ".xlsx");
    ServletOutputStream out = response.getOutputStream();
    ExportParams entity = new ExportParams(fileName, fileName);
    entity.setTitleHeight((short) 15);
    entity.setType(ExcelType.XSSF);
    entity.setStyle(ExcelStyleUtil.class);
    Workbook workbook = new XSSFWorkbook();
    new ExcelExportService().createSheetForMap(workbook, entity, entityList, list);
    workbook.write(out);
  }

  /**
   * 打印word
   *
   * @param response
   * @param runMap
   * @param tableMap
   * @param source
   * @param fileName
   */
  public static void word(HttpServletResponse response, Map<String, String> runMap, Map<String, String> tableMap, String source, String fileName) throws Exception {
    response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=utf-8");
    response.setCharacterEncoding(CharsetUtil.UTF_8);
    fileName = URLEncoder.encode(fileName, CharsetUtil.UTF_8).replaceAll("\\+", "%20");
    // 设定文件名称
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".docx");
    ServletOutputStream out = response.getOutputStream();

    WordGenerator word = new WordGenerator(source);
    XWPFDocument doc = word.createXWPFDocumentByTemplate();
    word.setXWPFRunText(doc.getParagraphs(), runMap);
    word.setXWPFTableText(doc.getTables(), tableMap);
    doc.write(out);// 文档写入流
  }

  /**
   * 根据ftl模板生成word并导出
   * @param response
   * @param map
   * @param source     ftl模板
   * @param fileName   导出名
   * @throws Exception
   */
  public static void exportWordByFtl(HttpServletResponse response, Map<String, Object> map, String source, String fileName) throws Exception {

    WordUtils.exportMillCertificateWord(response, map,fileName, source);

  }
}
