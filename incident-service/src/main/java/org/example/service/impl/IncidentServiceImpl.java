package org.example.service.impl;

import org.example.dal.po.Incident;
import org.example.dal.service.IncidentDaoService;
import org.example.param.IncidentParam;
import org.example.param.IncidentSearchParam;
import org.example.result.IncidentResult;
import org.example.result.PageResult;
import org.example.service.IncidentService;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.Duration;
import java.util.List;

public class IncidentServiceImpl implements IncidentService {
    private static final long REDIS_KEY_EXPIRED_TIME = 30L;
    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Resource
    private IncidentDaoService incidentDaoService;

    @Override
    public IncidentResult getById(Long id) {
        // query from cache
        Incident incident = (Incident) redisTemplate.opsForValue().get(String.valueOf(id));
        if (incident == null) {
            Incident byId = incidentDaoService.getById(id);
            if (byId != null) {
                // add to cache
                redisTemplate.opsForValue().set(String.valueOf(byId.getId()), byId, Duration.ofMinutes(REDIS_KEY_EXPIRED_TIME));
                return IncidentResult.valueOf(byId);
            }
            return null;
        }
        return IncidentResult.valueOf(incident);
    }

    @Override
    public PageResult<List<IncidentResult>> pageSearchByCondition(IncidentSearchParam param) {

        return null;
    }

    @Override
    public Boolean update(IncidentParam param) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public Long insert(IncidentParam param) {
        return null;
    }
}
