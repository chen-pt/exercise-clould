package com.person.chenpt.web;

import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.person.chenpt.entity.User;
import com.person.chenpt.utils.ExcelPoiUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-12-20 17:41
 * @Modified By:
 */
@RestController
@RequestMapping("/easy-poi")
public class EasyPoiController {

    /**
     * 导入数据
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/import")
    public String importExcel(MultipartFile file) throws IOException {
//        List<User> list = ExcelPoiUtil.importExcel(file, User.class);
        List<User> list = ExcelPoiUtil.importExcel("C:/Users/chenpt/Downloads/testw (2).xlsx",
                                        0,1, User.class);
        if (list.size() != 0) {
            return "导入成功";
        } else {
            return "导入失败";
        }
    }
    /**
     * 使用模板excel导出
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/export/excelTemplate")
    public void makeExcelTemplate(HttpServletResponse response) throws Exception {
        List<User> list = new ArrayList<>();
        list.add(new User("1","测试1","18",new Date(),"测试1","C:/Users/chenpt/Downloads/xue.jpg"));
        list.add(new User("2","测试2","18",new Date(),"测试2","C:/Users/chenpt/Downloads/xue.jpg"));
        list.add(new User("3","测试3","18",new Date(),"测试3","C:/Users/chenpt/Downloads/xue.jpg"));
        list.add(new User("4","测试4","18",new Date(),"测试4","C:/Users/chenpt/Downloads/xue.jpg"));
        TemplateExportParams templatePath = new TemplateExportParams("templates/tmp.xlsx");

        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map.put("date", sdf.format(new Date()));
        map.put("userName", "chenpengtao");
        map.put("userLst", list);
        ExcelPoiUtil.exportExcel(templatePath, map, "testTem", response);
    }

    /**
     * 导入数据
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<User> list = new ArrayList<>();
        list.add(new User("1","测试1","18",new Date(),"测试1","C:/Users/chenpt/Downloads/xue.jpg"));
        list.add(new User("2","测试2","18",new Date(),"测试2","C:/Users/chenpt/Downloads/xue.jpg"));
        list.add(new User("3","测试3","18",new Date(),"测试3","C:/Users/chenpt/Downloads/xue.jpg"));
        list.add(new User("4","测试4","18",new Date(),"测试4","C:/Users/chenpt/Downloads/xue.jpg"));
        ExcelPoiUtil.exportExcel(list,null,"sheet1",User.class,"testw",response);
    }


    /**
     * 使用模板word导出数据
     * @param response
     */
    @RequestMapping("/wordTemplate")
    public void makeWordTemplate(HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "陈澎涛");
        map.put("content", "安徽省亳州市涡阳县");
        map.put("date", "2021-12-22");
        try {
            ExcelPoiUtil.WordTemplateExport(map,"templates/temp.docx","test",response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
