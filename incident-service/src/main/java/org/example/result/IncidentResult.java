package org.example.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.example.dal.po.Incident;

import java.io.Serializable;
import java.util.Date;

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
    private Date createTime;

    @ApiModelProperty("incident updateTime")
    private Date updateTime;

    public static IncidentResult valueOf(Incident incident) {
        IncidentResult result = new IncidentResult();
        result.setIncidentId(incident.getId());
        result.setStatus(incident.getStatus());
        result.setLevel(incident.getIncidentLevel());
        result.setCreator(incident.getCreateBy());
        result.setCreateTime(incident.getCreateTime());
        result.setUpdateTime(incident.getUpdateTime());
        return result;
    }
}
