package org.example.dal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.dal.mapper.IncidentMapper;
import org.example.dal.po.Incident;
import org.example.dal.service.IncidentDaoService;
import org.springframework.stereotype.Service;

@Service
public class IncidentDaoServiceImpl extends ServiceImpl<IncidentMapper, Incident> implements IncidentDaoService {

}
