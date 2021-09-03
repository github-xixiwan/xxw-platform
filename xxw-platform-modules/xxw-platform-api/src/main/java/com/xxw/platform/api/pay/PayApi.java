package com.xxw.platform.api.pay;

import com.xxw.platform.api.pay.model.dto.SuccessKilledDTO;
import com.xxw.platform.api.pay.model.vo.ElasticsearchVO;
import com.xxw.platform.api.pay.model.vo.SuccessKilledVO;
import com.xxw.platform.util.rest.Result;

import java.util.List;

public interface PayApi {

    Result<String> hello();

    Result<List<SuccessKilledVO>> getListBySeckillId(Long seckillId);

    Result<List<ElasticsearchVO>> getElasticsearchByTraceId(String index, Long traceId);

    Result<String> addSuccessKilled(SuccessKilledDTO dto);

}
