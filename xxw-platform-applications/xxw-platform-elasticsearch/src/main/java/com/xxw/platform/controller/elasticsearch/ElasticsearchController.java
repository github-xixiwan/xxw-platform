package com.xxw.platform.controller.elasticsearch;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxw.platform.module.elasticsearch.entity.XxwOrder;
import com.xxw.platform.module.elasticsearch.service.OrderService;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Api(tags = "elasticsearch")
@RequestMapping("/elasticsearch")
@RestController
@RefreshScope
public class ElasticsearchController {

    @Value("${name:word}")
    private String name;

    @Resource
    private OrderService orderService;

    @PostMapping("/insert")
    public Result<String> insert(@RequestBody List<XxwOrder> list) {
        orderService.insert("xxw-order", list);
        return Result.success(name);
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody List<XxwOrder> list) {
        orderService.update("xxw-order", list);
        return Result.success(name);
    }

    @GetMapping("/deleteIndex")
    public Result<String> deleteIndex() {
        orderService.deleteIndex("xxw-order");
        return Result.success(name);
    }

    @GetMapping("/delete")
    public Result<String> delete(@RequestParam("orderSn") String orderSn) {
        orderService.delete("xxw-order", orderSn);
        return Result.success(name);
    }

    @GetMapping("/searchOne")
    public Result<XxwOrder> searchOne(@RequestParam("orderSn") String orderSn) {
        return Result.success(orderService.searchOne("xxw-order", orderSn));
    }

    @GetMapping("/searchList")
    public Result<List<XxwOrder>> searchList(@RequestParam("userId") Long userId) {
        return Result.success(orderService.searchList("xxw-order", userId));
    }

    @GetMapping("/fuzzyList")
    public Result<List<XxwOrder>> fuzzyList(@RequestParam("consignee") String consignee) {
        return Result.success(orderService.fuzzyList("xxw-order", consignee));
    }

    @GetMapping("/page")
    public Result<Page<XxwOrder>> page(@RequestParam("consignee") String consignee) {
        return Result.success(orderService.page("xxw-order", consignee));
    }
}
