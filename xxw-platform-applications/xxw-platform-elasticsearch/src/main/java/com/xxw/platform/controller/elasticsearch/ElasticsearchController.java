package com.xxw.platform.controller.elasticsearch;

import cn.hutool.db.PageResult;
import com.xxw.platform.module.elasticsearch.dto.ElasticsearchDTO;
import com.xxw.platform.module.elasticsearch.service.ElasticsearchService;
import com.xxw.platform.module.common.rest.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/elasticsearch")
@RestController
@RefreshScope
public class ElasticsearchController {

    @Value("${name:word}")
    private String name;

    @Resource
    private ElasticsearchService elasticsearchService;

    @PostMapping("/insert")
    public Result<String> insert(@RequestBody List<ElasticsearchDTO> list) {
        elasticsearchService.insert("xxw-order", list);
        return Result.success(name);
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody List<ElasticsearchDTO> list) {
        elasticsearchService.update("xxw-order", list);
        return Result.success(name);
    }

    @GetMapping("/deleteIndex")
    public Result<String> deleteIndex() {
        elasticsearchService.deleteIndex("xxw-order");
        return Result.success(name);
    }

    @GetMapping("/delete")
    public Result<String> delete(@RequestParam("orderSn") String orderSn) {
        elasticsearchService.delete("xxw-order", orderSn);
        return Result.success(name);
    }

    @GetMapping("/searchOne")
    public Result<ElasticsearchDTO> searchOne(@RequestParam("orderSn") String orderSn) {
        return Result.success(elasticsearchService.searchOne("xxw-order", orderSn));
    }

    @GetMapping("/searchList")
    public Result<List<ElasticsearchDTO>> searchList(@RequestParam("userId") Long userId) {
        return Result.success(elasticsearchService.searchList("xxw-order", userId));
    }

    @GetMapping("/fuzzyList")
    public Result<List<ElasticsearchDTO>> fuzzyList(@RequestParam("consignee") String consignee) {
        return Result.success(elasticsearchService.fuzzyList("xxw-order", consignee));
    }

    @GetMapping("/page")
    public Result<PageResult<ElasticsearchDTO>> page(@RequestParam("consignee") String consignee) {
        return Result.success(elasticsearchService.page("xxw-order", consignee));
    }
}
