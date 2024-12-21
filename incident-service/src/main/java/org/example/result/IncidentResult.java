package org.example.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("incident result")
@Data
public class IncidentResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("incident id")
    private Long incidentId;

    @ApiModelProperty("incident status")
    private Integer status;

    @ApiModelProperty("incident creator")
    private String creator;

    @ApiModelProperty("incident level")
    private Integer level;

    @ApiModelProperty("incident createTime")
    private Long createTime;

    @ApiModelProperty("incident updateTime")
    private Long updateTime;
}
