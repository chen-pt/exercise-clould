package com.person.chenpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.lucene.document.FieldType;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-11-24 14:11
 * @Modified By:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String esId;
    private String name;
    private int age;



}
