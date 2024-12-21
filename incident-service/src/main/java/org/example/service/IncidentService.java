package org.example.service;

import org.example.param.IncidentParam;
import org.example.param.IncidentSearchParam;
import org.example.result.IncidentResult;
import org.example.result.PageResult;

import java.util.List;

public interface IncidentService {
    IncidentResult getById(Long id);

    PageResult<List<IncidentResult>> pageSearchByCondition(IncidentSearchParam param);

    Boolean update(Long id, IncidentParam param);

    Boolean delete(Long id);

    Long insert(IncidentParam param);
}
