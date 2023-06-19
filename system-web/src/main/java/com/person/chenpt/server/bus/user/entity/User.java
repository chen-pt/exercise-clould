package com.person.chenpt.server.bus.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2023-06-16 16:23
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**用户名**/
    private String name;
    /**密码**/
    private String passwd;
}
