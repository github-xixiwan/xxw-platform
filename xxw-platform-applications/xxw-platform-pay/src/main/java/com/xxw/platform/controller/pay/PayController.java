package com.xxw.platform.controller.pay;

import com.alibaba.excel.EasyExcel;
import com.xxw.platform.api.pay.model.vo.ElasticsearchVO;
import com.xxw.platform.api.pay.model.vo.SuccessKilledVO;
import com.xxw.platform.module.pay.listener.DemoDataListener;
import com.xxw.platform.module.pay.model.entity.DemoData;
import com.xxw.platform.module.pay.service.PayService;
import com.xxw.platform.util.rest.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Slf4j
@Api(tags = "支付相关业务")
@RequestMapping("/pay")
@RestController
public class PayController {

    @Resource
    private PayService payService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return payService.hello();
    }

    @GetMapping("/getListBySeckillId")
    public Result<List<SuccessKilledVO>> getListBySeckillId(@RequestParam("seckillId") Long seckillId) {
        return payService.getListBySeckillId(seckillId);
    }

    @GetMapping("/getElasticsearchByTraceId")
    public Result<List<ElasticsearchVO>> getElasticsearchByTraceId(@RequestParam("index") String index, @RequestParam("traceId") Long traceId) {
        return payService.getElasticsearchByTraceId(index, traceId);
    }

    /**
     * 文件上传
     * <p>1. 创建excel对应的实体对象 参照{@link UploadData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <p>3. 直接读即可
     */
    @PostMapping("upload")
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DemoData.class, new DemoDataListener()).sheet().doRead();
        return "success";
    }
}
