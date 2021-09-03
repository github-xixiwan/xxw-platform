package com.xxw.platform.api.pay.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Elasticsearch对象", description = "ElasticsearchVO")
public class ElasticsearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "traceId")
    private Long traceId;

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "traceType")
    private String traceType;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "body")
    private String body;

}
