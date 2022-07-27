package com.person.chenpt.core.util;

import cn.hutool.core.io.resource.ResourceUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-04-16 18:12
 * @Modified By:
 */
public class WordUtils {
    //配置信息,代码本身写的还是很可读的,就不过多注解了
    private static Configuration configuration = null;
    //这里注意的是利用WordUtils的类加载器动态获得模板文件的位置
     private static final String templateFolder = ResourceUtil.getResource("templates").getPath();
//    private static final String templateFolder = "D:/test";
    static {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(templateFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WordUtils() {
        throw new AssertionError();
    }

    /**
     *
     * @param response
     * @param map
     * @param title
     * @param ftlFile
     * @throws IOException
     */
    public static void exportMillCertificateWord(HttpServletResponse response, Map map, String title, String ftlFile) throws IOException {
        Template freemarkerTemplate = configuration.getTemplate(ftlFile);
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        try {
            // 调用工具类的createDoc方法生成Word文档
            file = createDoc(map,freemarkerTemplate);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件名
            String fileName = title+System.currentTimeMillis()+".doc";
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));

            out = response.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } finally {
            if(fin != null) fin.close();
            if(out != null) out.close();
            if(file != null) file.delete(); // 删除临时文件
        }
    }

    private static File createDoc(Map<?, ?> dataMap, Template template) {
        String name = "GZPFD.doc";
        File f = new File(templateFolder+File.separator+name);
        Template t = template;
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
            t.process(dataMap, w);
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return f;
    }

    //获得图片的base64码
    public static String getImageBase(String src) {
//        if(src==null||src==""){
//            return "";
//        }
        File file = new File("D:/test/test.png");
        if(!file.exists()) {
            return "";
        }
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }


}
