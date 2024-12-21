package org.example.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel("incident search param")
@Data
public class IncidentSearchParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("incident ids")
    private List<Long> incidentIds;

    @ApiModelProperty("incident statuses")
    private List<Integer> statuses;

    @ApiModelProperty("fuzzy search Creator")
    private String searchCreator;

    @ApiModelProperty("fuzzy search Info")
    private String searchInfo;

    @ApiModelProperty("incident levels")
    private List<Integer> levels;

    @ApiModelProperty("page size")
    private Long pageSize;

    @ApiModelProperty("current page")
    private Long currentPage;
}
