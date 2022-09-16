package com.xxw.platform.controller.dynamic;

import com.alibaba.excel.EasyExcel;
import com.xxw.platform.module.dynamic.dao.intf.XxwOrderDao;
import com.xxw.platform.module.dynamic.dto.DemoDataDTO;
import com.xxw.platform.module.dynamic.entity.XxwOrderEntity;
import com.xxw.platform.module.dynamic.listener.DemoDataListener;
import com.xxw.platform.module.dynamic.service.DynamicService;
import com.xxw.platform.module.dynamic.vo.DynamicVO;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Api(tags = "dynamic")
@RequestMapping("/dynamic")
@RefreshScope
@RestController
public class DynamicController {

    @Value("${name:word}")
    private String name;

    @Resource
    private DynamicService dynamicService;

    @Resource
    @Qualifier("xxwOrder0DaoImpl")
    private XxwOrderDao xxwOrderDao0;

    @Resource
    @Qualifier("xxwOrder1DaoImpl")
    private XxwOrderDao xxwOrderDao1;

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

        XxwOrderEntity entity = new XxwOrderEntity();
        entity.setId(id);
        entity.setOrderSn(String.valueOf(id));
        xxwOrderDao0.save(entity);
        xxwOrderDao1.save(entity);
        return Result.success(name);
    }

    @PostMapping("upload")
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DemoDataDTO.class, new DemoDataListener()).sheet().doRead();
        return "success";
    }
}
