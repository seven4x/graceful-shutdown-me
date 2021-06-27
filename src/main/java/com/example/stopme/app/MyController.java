package com.example.stopme.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MyController {

    @Autowired
    MyWorker worker;

    @RequestMapping("/time")
    public Map serverTime(String echo) {
        Map<String, String> res = new HashMap<>();
        res.put("time", System.currentTimeMillis() + "");
        res.put("echo", echo);
        return res;
    }


    @RequestMapping("/myexit")
    public String exit() {
        //如果不在新线程退出， 会因为当前请求未处理 超过x值被SpringBoot的book强制（吃罚酒）
        new Thread(() -> {
            worker.stop();
            System.out.println("System.exit()");
            System.exit(0);
        }).start();
        return "done";
    }
}
