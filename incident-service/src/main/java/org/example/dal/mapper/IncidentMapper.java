package org.example.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.dal.po.Incident;

@Mapper
public interface IncidentMapper extends BaseMapper<Incident> {

}
