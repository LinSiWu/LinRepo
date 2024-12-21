package org.example.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel("incident param")
@Data
public class IncidentParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("incident status")
    @NotNull(message = "incident status can not be empty")
    @Pattern(regexp = "[01]")
    private Integer status;

    @ApiModelProperty("incident creator")
    @NotNull(message = "incident creator can not be empty")
    private String creator;

    @ApiModelProperty("incident info")
    private String info;

    @ApiModelProperty("incident level")
    @NotNull(message = "incident level can not be empty")
    @Pattern(regexp = "[01]")
    private Integer level;
}
