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

import static org.hamcrest.Matchers.hasSize;
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
        incident1.setId(1L);
        incident1.setInfo("test record 1");
        incident1.setIncidentLevel(1);
        incident1.setStatus(1);
        incident1.setCreateBy("creator1");
        incidentMapper.insert(incident1);

        // record 2
        Incident incident2 = new Incident();
        incident2.setId(2L);
        incident2.setInfo("test record 2");
        incident2.setIncidentLevel(2);
        incident2.setStatus(2);
        incident2.setCreateBy("creator2");
        incidentMapper.insert(incident2);
    }


    @Test
    public void testQueryIncidentById() throws Exception {
        mockMvc.perform(get("/incident/{id}", 1L))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data.incidentId").value(1L));
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
        param.setLevel(1);
        param.setStatus(1);
        mockMvc.perform(post("/incident")
                        .content(JSON.toJSONString(param))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").isNumber());
    }

    @Test
    public void testDeleteIncidentIfIdExist() throws Exception {
        mockMvc.perform(delete("/incident/{id}", 1L))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    public void testDeleteIncidentIfIdNotExist() throws Exception {
        mockMvc.perform(delete("/incident/{id}", 100L))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.FAILURE.getCode()))
                .andExpect(jsonPath("$.data").value(ResCodeEnum.RESPONSE_CODE_REQ_INVALID.getCode()));
    }

    @Test
    public void testUpdateIncidentIfAllCorrect() throws Exception {
        Long toUpdateId = 1L;
        IncidentParam param = new IncidentParam();
        param.setInfo("update record 1");
        param.setCreator("creator");
        param.setLevel(3);
        param.setStatus(3);

        mockMvc.perform(put("/incident/{id}", toUpdateId)
                        .content(JSON.toJSONString(param))
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
        param.setLevel(3);
        param.setStatus(3);
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
        mockMvc.perform(post("/incident")
                        .content(JSON.toJSONString(param))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ResCodeEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());
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
