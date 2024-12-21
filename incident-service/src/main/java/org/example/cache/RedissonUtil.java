package org.example.cache;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedissonUtil {

    @Resource
    private RedissonClient redissonClient;

    public Map<String, Object> getBatchByKeys(List<String> keys) {

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, RFuture<Object>> rFutureMap = new HashMap<>();
        RBatch batch = redissonClient.createBatch();
        for (String key : keys) {
            RBucketAsync<Object> bucketAsync = batch.getBucket(key);
            rFutureMap.put(key, bucketAsync.getAsync());
        }
        batch.execute();

        for (Map.Entry<String, RFuture<Object>> entry : rFutureMap.entrySet()) {
            try {
                resultMap.put(entry.getKey(), entry.getValue().get());
            } catch (ExecutionException e) {
                log.error("error get redis key: {} with ExecutionException", entry.getKey(), e);
            } catch (InterruptedException e) {
                log.error("error get redis key: {} with InterruptedException", entry.getKey(), e);
                Thread.currentThread().interrupt();
            }
        }
        return resultMap;
    }

    public void setBatchByMap(Map<String, Object> map, long timeToLive, TimeUnit timeUnit) {

        RBatch batch = redissonClient.createBatch();
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            RBucketAsync<Object> bucketAsync = batch.getBucket(stringObjectEntry.getKey());
            bucketAsync.setAsync(stringObjectEntry.getValue(), timeToLive, timeUnit);
        }
        batch.execute();
    }
}
