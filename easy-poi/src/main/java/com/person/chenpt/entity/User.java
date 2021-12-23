package com.person.chenpt.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-12-20 17:43
 * @Modified By:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Excel(name = "id")
    private String id;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "年龄")
    private String age;
    @Excel(name = "生日",isImportField = "true",exportFormat = "yyyy-MM-dd", importFormat =  "yyyy-MM-dd" ,databaseFormat = "yyyy-MM-dd" )
    private Date birtDay;
    @Excel(name = "备注")
    private String remark;
//    @Excel(name = "肖像",width =20,type = 2,imageType = 1)//导出图片
    private String image;

}
