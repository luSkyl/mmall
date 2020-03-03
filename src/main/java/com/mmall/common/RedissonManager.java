package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author lcy
 * @Date 2020/3/3
 * @Description
 */
@Component
@Slf4j
public class RedissonManager {
    private Config config = new Config();
    @Getter
    private Redisson redisson;
    /**
     * Redis1的IP地址
     */
    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    /**
     * Redis1的端口
     */
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));

    /**
     * Redis2的IP地址
     */
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    /**
     * Redis2的端口
     */
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    @PostConstruct
    private void  init(){
        try {
            config.useSingleServer().setAddress(new StringBuilder().append(redis1Ip).append(":").append(redis1Port).toString());
            redisson = (Redisson) Redisson.create(config);
            log.info("初始化Redisson结束");
        } catch (Exception e) {
            log.error("初始化Redisson结束",e);
        }
    }



}
