package org.example.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel("incident param")
@Data
public class IncidentParam implements Serializable {

    private static final long serialVersionUID = 1L;

    // incidentId is empty in create, is not empty in update
    @ApiModelProperty("incident id")
    private Long incidentId;

    @ApiModelProperty("incident status")
    @NotNull(message = "incident status can not be empty")
    private Integer status;

    @ApiModelProperty("incident creator")
    @NotNull(message = "incident creator can not be empty")
    private String creator;

    @ApiModelProperty("incident level")
    @NotNull(message = "incident level can not be empty")
    private Integer level;
}
