package com.person.chenpt.poi.web;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.person.chenpt.common.ExcelListener;
import com.person.chenpt.poi.entity.DictEeVo;
import com.person.chenpt.utils.DownloadUtil;
import com.person.chenpt.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-12-17 17:26
 * @Modified By:
 */
@Slf4j
@RestController
@RequestMapping("/poi")
public class PoiController {

    @RequestMapping("/test")
    public void test(HttpServletResponse response) throws Exception {
        List list = new ArrayList();
        list.add(new DictEeVo(1L,22L,"test1","test1","test1"));
        list.add(new DictEeVo(2L,22L,"test2","test1","test1"));
        list.add(new DictEeVo(3L,22L,"test3","test1","test1"));
        list.add(new DictEeVo(4L,22L,"test4","test1","test1"));
        DownloadUtil.export(list,"test",DictEeVo.class,response);
    }

    @RequestMapping("upload")
    public String upload(MultipartFile file) throws IOException {
//        ExcelListener<DictEeVo> listener = new ExcelListener<DictEeVo>();
//        EasyExcel.read("C:\\Users\\chenpt\\Downloads\\test.xlsx", DictEeVo.class, listener).sheet().doRead();
//        List<DictEeVo> lstData = listener.getLstData();
//        lstData.forEach(e->{
//            System.out.println(e.getName());
//        });


        List<DictEeVo> objects = EasyExcel.read("C:\\Users\\chenpt\\Downloads\\test.xlsx")
                .head(DictEeVo.class).sheet().doReadSync();
        objects.forEach(e->{
            System.out.println(e.getName());
        });

        return "success";
    }

}
