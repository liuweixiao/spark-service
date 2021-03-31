package com.liu.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RedisUtils {

    private String redisHost = "192.168.200.37";

    private int redisPort = 6379;

    private String redisSentinelMaster = "";

    private String redisSentinelNodes = "";

    private String redisPassword = "wDWHHU2vwVJFMFE8OWhRUXsZIVZsMM";

    /**
     * 连接与读取超时
     */
    private int timeout=20000;

     Map<Integer, Pool<Jedis>> jedisMap = new ConcurrentHashMap<>();


    public Pool<Jedis> getJedisPool(int dbIndex) {
        if (jedisMap == null || jedisMap.size() == 0) {
            for (int i = 0; i < 1; i++) {
                // 没有配置哨兵，则使用单机
                if (StringUtils.isEmpty(redisSentinelMaster)) {
                    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                    jedisPoolConfig.setTestOnBorrow(true);
                    jedisPoolConfig.setTestWhileIdle(true);
                    jedisPoolConfig.setMaxTotal(100);
                    jedisPoolConfig.setMaxIdle(20);
                    jedisMap.put(i, new JedisPool(jedisPoolConfig, redisHost, redisPort, timeout, redisPassword, i));
                } else {
                    Set<String> sentinels = new HashSet<>();
                    for (String s : redisSentinelNodes.split(",")) {
                        if (StringUtils.isNotBlank(s)) {
                            sentinels.add(s);
                        }
                    }
                    GenericObjectPoolConfig jedisPoolConfig = new GenericObjectPoolConfig();
//                    //最大连接数
//                    jedisPoolConfig.setMaxTotal(200);
//                    // 最大空闲数
//                    jedisPoolConfig.setMaxIdle(20);
//                    // 获取连接最长等待时间
//                    jedisPoolConfig.setMaxWaitMillis(2000);
//                    // 连接耗尽时不阻塞，抛出异常
//                    jedisPoolConfig.setBlockWhenExhausted(false);
                    jedisMap.put(i, new JedisSentinelPool(redisSentinelMaster, sentinels, jedisPoolConfig, timeout, redisPassword, i));
                }

            }
        }
        return jedisMap.get(dbIndex);
    }
    public static void main(String[] args) {
//        new RedisUtils().getJedisPool(0).getResource();
    }

}
