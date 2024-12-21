package org.example.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("page result")
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    @ApiModelProperty("total")
    Long total;

    @ApiModelProperty("data")
    T data;

    @ApiModelProperty("page size")
    Long size;

    @ApiModelProperty("current page No")
    Long current;
}
