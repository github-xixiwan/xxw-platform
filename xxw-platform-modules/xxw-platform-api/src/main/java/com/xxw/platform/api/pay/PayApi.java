package com.xxw.platform.api.pay;

import com.xxw.platform.api.pay.model.dto.SuccessKilledDTO;
import com.xxw.platform.api.pay.model.vo.SuccessKilledVO;
import com.xxw.platform.util.rest.Result;

public interface PayApi {

    Result<String> hello();

    Result<SuccessKilledVO> getById(Long id);

    Result<String> addSuccessKilled(SuccessKilledDTO dto);

}
