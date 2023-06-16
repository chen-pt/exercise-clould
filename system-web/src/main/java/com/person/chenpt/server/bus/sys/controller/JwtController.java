package com.person.chenpt.server.bus.sys.controller;

import com.person.chenpt.core.base.model.Result;
import com.person.chenpt.core.utils.JWTUitls;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2023-06-16 14:26
 * @Modified By:
 */
@RestController
@RequestMapping("/jwt")
@Api(tags = "jwt测试")
public class JwtController {

    @GetMapping("/add")
    @ApiOperation("创建")
    public Result creatJwtToken(){
        return Result.success(JWTUitls.createJwtToken("556688"));
    }

    @GetMapping("/parse")
    @ApiOperation("解析")
    public Result parseJwtToken(String token){
        return Result.success(JWTUitls.parseJwtToken(token));
    }


}
