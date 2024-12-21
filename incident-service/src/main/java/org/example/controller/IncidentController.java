package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.ResponseDTO;
import org.example.param.IncidentParam;
import org.example.param.IncidentSearchParam;
import org.example.result.IncidentResult;
import org.example.result.PageResult;
import org.example.service.IncidentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "incident api")
@RestController
@RequestMapping(value = "/incident")
public class IncidentController {

    @Resource
    private IncidentService incidentService;

    @ApiOperation(value = "query one incident by id")
    @GetMapping("/{id}")
    public ResponseDTO<IncidentResult> getById(@PathVariable Long id) {
        return ResponseDTO.success(incidentService.getById(id));
    }

    @ApiOperation(value = "search incidents by condition")
    @PostMapping("/search")
    public ResponseDTO<PageResult<List<IncidentResult>>> search(@Validated @RequestBody IncidentSearchParam incidentSearchParam) {
        return ResponseDTO.success(incidentService.pageSearchByCondition(incidentSearchParam));
    }

    @ApiOperation(value = "update incident")
    @PutMapping("/{id}")
    public ResponseDTO<Boolean> update(@PathVariable Long id, @Validated @RequestBody IncidentParam incidentParam) {
        return ResponseDTO.success(incidentService.update(id, incidentParam));
    }

    @ApiOperation(value = "create incident")
    @PostMapping
    public ResponseDTO<Long> create(@Validated @RequestBody IncidentParam incidentParam) {
        return ResponseDTO.success(incidentService.insert(incidentParam));
    }

    @ApiOperation(value = "delete incident")
    @DeleteMapping("/{id}")
    public ResponseDTO<Boolean> delete(@PathVariable Long id) {
        return ResponseDTO.success(incidentService.delete(id));
    }
}
