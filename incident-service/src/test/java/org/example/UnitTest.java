package org.example;

import com.alibaba.fastjson.JSON;
import org.example.common.IncidentLevelEnum;
import org.example.common.IncidentStatusEnum;
import org.example.common.ResCodeEnum;
import org.example.dal.mapper.IncidentMapper;
import org.example.dal.po.Incident;
import org.example.param.IncidentParam;
import org.example.param.IncidentSearchParam;
import org.example.service.IncidentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UnitTest {
    @Autowired
    IncidentService incidentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    IncidentMapper incidentMapper;

    @AfterEach
    public void clearTableAfterEachTest() {
        incidentMapper.delete(null);
    }

    @BeforeEach
    public void initTableBeforeEachTest() {
        // record 1
        Incident incident1 = new Incident();
        incident1.setInfo("test record 1");
        incident1.setIncidentLevel(IncidentLevelEnum.HIGH.getCode());
        incident1.setStatus(IncidentStatusEnum.FINISH.getCode());
        incident1.setCreateBy("creator1");
        incidentMapper.insert(incident1);

        // record 2
        Incident incident2 = new Incident();
        incident2.setInfo("test record 2");
        incident2.setIncidentLevel(IncidentLevelEnum.LOW.getCode());
        incident2.setStatus(IncidentStatusEnum.PROCESSING.getCode());
        incident2.setCreateBy("creator2");
        incidentMapper.insert(incident2);
    }


    @Test
    public void testQueryIncidentById() throws Exception {
        // first create an incident and get id
        IncidentParam param = new IncidentParam();
        param.setLevel(IncidentLevelEnum.LOW.getCode());
        param.setStatus(IncidentStatusEnum.FINISH.getCode());
        param.setCreator("someone");
        param.setInfo("something");
        Long insertId = incidentService.insert(param);

        mockMvc.perform(get("/incident/{id}", insertId))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data.incidentId").value(insertId))
                .andExpect(jsonPath("$.data.info").value("something"));
    }

    @Test
    public void testCreateIncidentIfParamInvalid() throws Exception {
        IncidentParam param = new IncidentParam();
        // miss level and status
        param.setInfo("test record 3");
        mockMvc.perform(post("/incident")
                        .content(JSON.toJSONString(param))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.FAILURE.getCode()))
                .andExpect(jsonPath("$.data").value(ResCodeEnum.RESPONSE_CODE_PARAM_ERROR.getCode()));
    }

    @Test
    public void testCreateIncidentIfParamCorrect() throws Exception {
        IncidentParam param = new IncidentParam();
        param.setInfo("test record 3");
        param.setCreator("creator");
        param.setLevel(IncidentLevelEnum.HIGH.getCode());
        param.setStatus(IncidentStatusEnum.FINISH.getCode());
        mockMvc.perform(post("/incident")
                        .content(JSON.toJSONString(param))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").isNumber());
    }

    @Test
    public void testDeleteIncidentIfIdExist() throws Exception {
        // first create an incident and get id
        IncidentParam param = new IncidentParam();
        param.setLevel(IncidentLevelEnum.LOW.getCode());
        param.setStatus(IncidentStatusEnum.FINISH.getCode());
        param.setCreator("someone");
        param.setInfo("something");
        Long insertId = incidentService.insert(param);

        mockMvc.perform(delete("/incident/{id}", insertId))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    public void testDeleteIncidentIfIdNotExist() throws Exception {
        mockMvc.perform(delete("/incident/{id}", -1L))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.FAILURE.getCode()))
                .andExpect(jsonPath("$.data").value(ResCodeEnum.RESPONSE_CODE_REQ_INVALID.getCode()));
    }

    @Test
    public void testUpdateIncidentIfAllCorrect() throws Exception {
        // first create an incident and get id
        IncidentParam param = new IncidentParam();
        param.setLevel(IncidentLevelEnum.LOW.getCode());
        param.setStatus(IncidentStatusEnum.FINISH.getCode());
        param.setCreator("someone");
        param.setInfo("something");
        Long insertId = incidentService.insert(param);

        IncidentParam updateParam = new IncidentParam();
        updateParam.setInfo("update record");
        updateParam.setCreator("creator");
        updateParam.setLevel(IncidentLevelEnum.LOW.getCode());
        updateParam.setStatus(IncidentStatusEnum.PROCESSING.getCode());

        mockMvc.perform(put("/incident/{id}", insertId)
                        .content(JSON.toJSONString(updateParam))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    public void testUpdateIncidentIfIdNotExist() throws Exception {
        Long toUpdateId = 100L;
        IncidentParam param = new IncidentParam();
        param.setInfo("update record 100");
        param.setCreator("creator");
        param.setLevel(IncidentLevelEnum.LOW.getCode());
        param.setStatus(IncidentStatusEnum.PROCESSING.getCode());
        mockMvc.perform(put("/incident/{id}", toUpdateId)
                        .content(JSON.toJSONString(param))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.FAILURE.getCode()))
                .andExpect(jsonPath("$.data").value(ResCodeEnum.RESPONSE_CODE_REQ_INVALID.getCode()));
    }

    @Test
    public void testUpdateIncidentIfParamInvalid() throws Exception {
        Long toUpdateId = 1L;
        IncidentParam param = new IncidentParam();
        // miss level and status
        param.setInfo("update record 100");
        param.setCreator("creator");
        mockMvc.perform(put("/incident/{id}", toUpdateId)
                        .content(JSON.toJSONString(param))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.FAILURE.getCode()))
                .andExpect(jsonPath("$.data").value(ResCodeEnum.RESPONSE_CODE_PARAM_ERROR.getCode()));
    }

    @Test
    public void testSearchIncidentIfAllCorrect() throws Exception {
        IncidentSearchParam param = new IncidentSearchParam();
        param.setLevels(Arrays.asList(IncidentLevelEnum.HIGH.getCode(), IncidentLevelEnum.LOW.getCode()));
        param.setStatuses(Arrays.asList(IncidentStatusEnum.PROCESSING.getCode(), IncidentStatusEnum.FINISH.getCode()));
        param.setPageSize(10L);
        param.setCurrentPage(1L);
        mockMvc.perform(post("/incident/search")
                        .content(JSON.toJSONString(param))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.total").value(2L));
    }

    @Test
    public void testSearchIncidentIfParamInvalid() throws Exception {
        IncidentSearchParam param = new IncidentSearchParam();
        param.setLevels(Arrays.asList(100, 200));
        mockMvc.perform(post("/incident")
                        .content(JSON.toJSONString(param))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.FAILURE.getCode()))
                .andExpect(jsonPath("$.data").value(ResCodeEnum.RESPONSE_CODE_PARAM_ERROR.getCode()));
    }
}
