package com.liu.utils;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class RateLimiter {
    String script;
    Pool<Jedis> pool = new RedisUtils().getJedisPool(0);


    public RateLimiter(String fileName) {
        this.script = getluaString(fileName);
    }

    public Boolean acquire(String key, int permits, int maxPermits, Long intervalMilliseconds) {
        Jedis redis = new RedisUtils().getJedisPool(0).getResource();
        if (StringUtils.isBlank(script)) {
            throw new RuntimeException("脚本加载失败");
        }
        Object result = redis.eval(script,
                Arrays.asList(key, String.valueOf(maxPermits), String.valueOf(intervalMilliseconds)),
                Arrays.asList(String.valueOf(permits))
        );
        if (result != null && 0 != (Long) result) {
            System.out.println(System.currentTimeMillis() + "成功");
            return true;
        } else {
            System.out.println(System.currentTimeMillis() + "失败");
            return false;
        }
    }

    public Boolean acquire(String key, int permits) {
        Jedis redis = pool.getResource();
        if (StringUtils.isBlank(script)) {
            throw new RuntimeException("脚本加载失败");
        }
        Object result = redis.eval(script,
                Arrays.asList(key),
                Arrays.asList(String.valueOf(permits)));

        redis.close();
        if (result != null && 0 != (Long) result) {
            System.out.println(System.currentTimeMillis() + "成功");
            return true;
        } else {
            System.out.println(System.currentTimeMillis() + "失败");
            return false;
        }
    }

    public String getluaString(String fileName) {
        String result = "";
        try {
            result = new String(Files.readAllBytes(Paths.get( this.getClass().getResource("/" + fileName).getPath())));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

}
