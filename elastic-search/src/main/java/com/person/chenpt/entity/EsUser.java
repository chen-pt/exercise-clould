package com.person.chenpt.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.Map;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2022-08-01 16:40
 * @Modified By:
 */
@Data
@Document(indexName = "user")
public class EsUser {

    private String id;
    private String name;
    private Integer age;
    private String remark;

    //用来封装高亮的结果
    private Map<String, List<String>> highlights;

    public EsUser(String id, String name, int age, String remark){
        this.id=id;
        this.name=name;
        this.age=age;
        this.remark=remark;
    }

}
