package com.person.chenpt.common;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.person.chenpt.poi.entity.DictEeVo;
import com.person.chenpt.utils.JacksonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-12-20 13:42
 * @Modified By:
 */
@Slf4j
public class ExcelListener<T> extends AnalysisEventListener<T> {
    List<T> list = new ArrayList<>();

    /**
     * invoke 方法逐行读取数据
     * @param vo
     * @param analysisContext
     */
    @SneakyThrows
    @Override
    public void invoke(T vo, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JacksonUtils.obj2json(vo));
        list.add(vo);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！"+list.size());
    }

    public List<T> getLstData(){
        return list;
    }
}
