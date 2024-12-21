package org.example;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.common.IncidentLevelEnum;
import org.example.common.IncidentStatusEnum;
import org.example.dal.mapper.IncidentMapper;
import org.example.dal.po.Incident;
import org.example.param.IncidentParam;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class StressTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IncidentMapper incidentMapper;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @AfterEach
    public void clearTableAfterEachTest() {
        incidentMapper.delete(null);
    }

    @BeforeEach
    public void clearTableBeforeEachTest() {
        incidentMapper.delete(null);
    }

    @Test
    public void testStress() {
        // 1. create 100000 incidents by api
        CountDownLatch latchForCreate = new CountDownLatch(100000);
        for (int i = 0; i < 100000; i++) {
            threadPoolTaskExecutor.submit(() -> {
                try {
                    create();
                    latchForCreate.countDown();
                } catch (Exception e) {

                }
            });
        }
        try {
            latchForCreate.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 2. test update and query and delete
        // get current max and min id
        Long maxId = getMaxId();
        Long minId = getMinId();
        // update from min to max
        CountDownLatch latchForUpdate = new CountDownLatch((int) (maxId - minId));
        final AtomicInteger updateIndex = new AtomicInteger(minId.intValue());
        final AtomicInteger end = new AtomicInteger(maxId.intValue());
        while (updateIndex.get() <= end.get()) {
            threadPoolTaskExecutor.submit(() -> {
                try {
                    update(updateIndex.get());
                    query(updateIndex.get());
                    deleteById(updateIndex.get());
                    updateIndex.incrementAndGet();
                    latchForUpdate.countDown();
                } catch (Exception e) {
                }
            });
        }
        try {
            latchForUpdate.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void create() throws Exception {
        IncidentParam param = new IncidentParam();
        param.setInfo("test stress");
        param.setCreator("creator");
        param.setLevel(IncidentLevelEnum.HIGH.getCode());
        param.setStatus(IncidentStatusEnum.FINISH.getCode());
        mockMvc.perform(post("/incident")
                .content(JSON.toJSONString(param))
                .contentType(MediaType.APPLICATION_JSON));
    }

    private void update(long id) throws Exception {
        IncidentParam param = new IncidentParam();
        param.setInfo("test stress after update");
        param.setCreator("creator after update");
        param.setLevel(IncidentLevelEnum.HIGH.getCode());
        param.setStatus(IncidentStatusEnum.FINISH.getCode());
        mockMvc.perform(put("/incident/{id}", id)
                .content(JSON.toJSONString(param))
                .contentType(MediaType.APPLICATION_JSON));
    }

    private void query(long id) throws Exception {
        mockMvc.perform(get("/incident/{id}", id));
    }

    private void deleteById(long id) throws Exception {
        mockMvc.perform(delete("/incident/{id}", id));
    }

    private Long getMaxId() {
        LambdaQueryWrapper<Incident> wrapper  = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Incident::getId).last("limit 1");
        Incident incident = incidentMapper.selectOne(wrapper);
        return incident.getId();
    }

    private Long getMinId() {
        LambdaQueryWrapper<Incident> wrapper  = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Incident::getId).last("limit 1");
        Incident incident = incidentMapper.selectOne(wrapper);
        return incident.getId();
    }
}
