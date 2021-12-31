package com.xxw.platform.controller.single;

import com.alibaba.excel.EasyExcel;
import com.xxw.platform.module.single.listener.DemoDataListener;
import com.xxw.platform.module.single.model.entity.DemoData;
import com.xxw.platform.module.single.service.IXxwOrderService;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Api(tags = "单一数据库")
@RequestMapping("/single")
@RefreshScope
@RestController
public class SingleController {

    @Value("${name:word}")
    private String name;

    @Resource
    private IXxwOrderService xxwOrderService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }

    @PostMapping("upload")
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DemoData.class, new DemoDataListener()).sheet().doRead();
        return "success";
    }
}
