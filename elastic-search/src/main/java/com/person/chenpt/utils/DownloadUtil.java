package com.person.chenpt.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.poi.ss.formula.functions.T;
import org.apache.tomcat.util.buf.CharsetUtil;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-12-17 17:47
 * @Modified By:
 */
public class DownloadUtil {


    /**
     * 导出excel
     *
     * @param list     导出数据
     * @param fileName 文件名
     * @param clazz    导出实体类
     */
    public static void export(List list, String fileName, Class<?> clazz, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        // 设定文件名称
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        EasyExcel.write(out, clazz).sheet("sheet1").doWrite(list);
    }




}
