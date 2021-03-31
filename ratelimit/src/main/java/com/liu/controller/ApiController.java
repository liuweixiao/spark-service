package com.liu.controller;

import com.liu.annotation.MyRedisLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api/meta")
@Slf4j
public class ApiController {
    @MyRedisLimiter(limitNum = 1, limitParamName = "appId")
    @RequestMapping("/boot/send/message2")
    public String sendMessage2(@RequestParam(value = "appId") String appId ){
        //记录返回接口
        String result = "";
        boolean flag = true;
        if (flag){
            result = "短信发送成功！";
            return result;
        }
        result = "哎呀，服务器开小差了，请再试一下吧";
        return result;
    }
}
