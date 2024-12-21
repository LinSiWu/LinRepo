package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.ResponseDTO;
import org.example.param.IncidentParam;
import org.example.param.IncidentSearchParam;
import org.example.result.IncidentResult;
import org.example.result.PageResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "incident api")
@RestController
@RequestMapping(value = "/incident")
public class IncidentController {

    @ApiOperation(value = "query one incident by id")
    @GetMapping("/{id}")
    public ResponseDTO<IncidentResult> getById(@PathVariable Long id) {
        // TODO
        return ResponseDTO.success(null);
    }

    @ApiOperation(value = "search incidents by condition")
    @PostMapping("/search")
    public ResponseDTO<PageResult<List<IncidentResult>>> search(@Validated @RequestBody IncidentSearchParam incidentSearchParam) {
        // TODO
        return ResponseDTO.success(null);
    }

    @ApiOperation(value = "update incident")
    @PostMapping("/update")
    public ResponseDTO<Boolean> update(@Validated @RequestBody IncidentParam incidentParam) {
        // TODO
        return ResponseDTO.success(null);
    }

    @ApiOperation(value = "create incident")
    @PostMapping("/create")
    public ResponseDTO<IncidentResult> create(@Validated @RequestBody IncidentParam incidentParam) {
        // TODO
        return ResponseDTO.success(null);
    }

    @ApiOperation(value = "delete incident")
    @DeleteMapping("/{id}")
    public ResponseDTO<Boolean> delete(@PathVariable Long id) {
        // TODO
        return ResponseDTO.success(null);
    }
}
