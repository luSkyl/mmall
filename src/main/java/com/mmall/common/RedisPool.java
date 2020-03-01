package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author lcy
 * @Date 2020/3/1
 * @Description Redis连接池
 */
public class RedisPool {
    /**
     * redis 连接池
     */
    private static JedisPool pool;
    /**
     * 最大连接数
     */
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));
    /**
     * 最大空闲数
     */
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));
    /**
     * 最小空闲数
     */
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));
    /**
     * 在Borrow一个Jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的Jedis实例肯定是可以用的
     */
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true"));
    /**
     * 在Borrow一个Jedis实例的时候，是否要进行验证操作，如果赋值true。则归还的Jedis实例肯定是可以用的
     */
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.turn", "false"));
    /**
     * Redis的IP地址
     */
    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    /**
     * Redis的端口
     */
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));


    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        //连接耗尽的时候是否阻塞，false会抛出异常，true阻塞直到超时。默认为True。
        config.setBlockWhenExhausted(true);
        pool = new JedisPool(config, redisIp, redisPort,1000*2);
    }

    static {
        initPool();
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }
    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }
    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("lcy","lcy");
        returnResource(jedis);

        //临时调用，销毁连接池中的所有连接
        pool.destroy();
        System.out.println("Program is end");
    }

}
