package com.xxw.platform.controller.dynamic;

import com.alibaba.excel.EasyExcel;
import com.xxw.platform.module.dynamic.dto.DemoDataDTO;
import com.xxw.platform.module.dynamic.listener.DemoDataListener;
import com.xxw.platform.module.dynamic.service.DynamicService;
import com.xxw.platform.module.dynamic.vo.DynamicVO;
import com.xxw.platform.module.util.rest.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RequestMapping("/dynamic")
@RefreshScope
@RestController
public class DynamicController {

    @Value("${name:word}")
    private String name;

    @Resource
    private DynamicService dynamicService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }

    @GetMapping("/getOrder")
    Result<DynamicVO> getOrder(@RequestParam("id") Integer id) {
        return dynamicService.getOrder(id);
    }

    @GetMapping("/buyOrder")
    public Result<String> buyOrder(@RequestParam("id") Integer id) {
        dynamicService.buyOrder(id);
        return Result.success(name);
    }

    @PostMapping("upload")
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DemoDataDTO.class, new DemoDataListener()).sheet().doRead();
        return "success";
    }
}
