package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jodd.util.StringUtil;
import org.example.common.ResCodeEnum;
import org.example.dal.po.Incident;
import org.example.dal.service.IncidentDaoService;
import org.example.exception.IncidentException;
import org.example.param.IncidentParam;
import org.example.param.IncidentSearchParam;
import org.example.result.IncidentResult;
import org.example.result.PageResult;
import org.example.service.IncidentService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Service
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
        LambdaQueryWrapper<Incident> query = new LambdaQueryWrapper<>();
        // build query condition
        if (!CollectionUtils.isEmpty(param.getStatuses())) {
            query.in(Incident::getStatus, param.getStatuses());
        }
        if (!CollectionUtils.isEmpty(param.getIncidentIds())) {
            query.in(Incident::getId, param.getIncidentIds());
        }
        if (!StringUtil.isEmpty(param.getSearchCreator())) {
            query.like(Incident::getCreateBy, param.getSearchCreator());
        }
        if (!StringUtil.isEmpty(param.getSearchInfo())) {
            query.like(Incident::getInfo, param.getSearchInfo());
        }
        if (!CollectionUtils.isEmpty(param.getLevels())) {
            query.in(Incident::getIncidentLevel, param.getLevels());
        }

        // build page condition
        Page<Incident> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = incidentDaoService.page(page, query);

        // build result
        PageResult<List<IncidentResult>> result = new PageResult<>();
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setTotal(page.getTotal());
        List<IncidentResult> incidentResults = page.getRecords().stream().map(IncidentResult::valueOf).toList();
        result.setData(incidentResults);
        return result;
    }

    @Override
    public Boolean update(Long id, IncidentParam param) {
        Incident byId = incidentDaoService.getById(id);
        if (byId == null) {
            throw new IncidentException(ResCodeEnum.RESPONSE_CODE_REQ_INVALID.getCode(), "update incident id not exist");
        }
        Incident incident = new Incident();
        incident.setId(id);
        incident.setIncidentLevel(param.getLevel());
        incident.setInfo(param.getInfo());
        incident.setStatus(param.getStatus());
        incident.setCreateBy(param.getCreator());
        incident.setUpdateTime(new Date(System.currentTimeMillis()));
        return incidentDaoService.updateById(incident);
    }

    @Override
    public Boolean delete(Long id) {
        Incident byId = incidentDaoService.getById(id);
        if (byId == null) {
            throw new IncidentException(ResCodeEnum.RESPONSE_CODE_REQ_INVALID.getCode(), "delete incident id not exist");
        }
        redisTemplate.delete(String.valueOf(id));
        return incidentDaoService.removeById(id);
    }

    @Override
    public Long insert(IncidentParam param) {
        Incident incident = new Incident();
        incident.setIncidentLevel(param.getLevel());
        incident.setInfo(param.getInfo());
        incident.setStatus(param.getStatus());
        incident.setCreateBy(param.getCreator());
        incident.setUpdateTime(new Date(System.currentTimeMillis()));
        incident.setCreateTime(new Date(System.currentTimeMillis()));
        incidentDaoService.save(incident);
        return incident.getId();
    }
}
