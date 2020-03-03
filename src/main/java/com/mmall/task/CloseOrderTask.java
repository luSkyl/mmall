package com.mmall.task;

import com.mmall.common.*;
import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @Author lcy
 * @Date 2020/3/3
 * @Description
 */
@Component
@Slf4j
public class CloseOrderTask {
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private RedissonManager redissonManager;

    @Scheduled(cron = "* */1 * * * ?")
    public ServerResponse<String> closeOrderTaskV1() {
        log.info("关闭订单定时任务启动");
        ServerResponse<String> serverResponse = ServerResponse.createByErrorMessage("关闭订单失败");
        long lockTimeOut = Long.parseLong(PropertiesUtil.getProperty("lock.time", "5000"));
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeOut));
        if (setnxResult != null && setnxResult == 1) {
            serverResponse = closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            String oldValue = RedisShardedPoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            if (oldValue != null && System.currentTimeMillis() > Long.parseLong(oldValue)) {
                //说明死锁 而且锁已经超时 可以对该锁进行操作
                String newValue = RedisShardedPoolUtil.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeOut));
                if (StringUtils.isBlank(newValue) || (newValue != null && StringUtils.equals(newValue, oldValue))) {
                    //如果 从Redis取出的值为空 说明 该锁已经删除 或者新值等于旧值 说明该值没有被操作 满足条件说明真正获取到锁
                    serverResponse = closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }else {
                    log.info("【关闭订单】 没有获取到分布式锁{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            }else {
                log.info("【关闭订单】 没有获取到分布式锁{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            }
        }
        log.info("关闭订单定时任务结束");
        return serverResponse;
    }

    /**
     * 防止 Tomcat 忽然关闭而造成死锁
     */
    @PreDestroy
    private ServerResponse preDestory() {
        return closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
    }

    private ServerResponse<String> closeOrder(String lockName) {
        RedisShardedPoolUtil.expire(lockName, Integer.parseInt(PropertiesUtil.getProperty("lock.time", "5000")));
        log.info("获取{},ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        ServerResponse<String> ServerResponse = iOrderService.closeOrder(hour);
        RedisShardedPoolUtil.del(lockName);
        log.info("释放{},ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
        return ServerResponse;
    }

    @Scheduled(cron = "* */1 * * * ?")
    public ServerResponse<String> closeOrderTaskV2() {
        RLock lock = redissonManager.getRedisson().getLock(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        boolean getLock = false;
        ServerResponse<String> serverResponse = ServerResponse.createByErrorMessage("关闭订单失败");
        try {
            if(getLock = lock.tryLock(2,5, TimeUnit.SECONDS)){
                log.info("Redisson获取到分布式锁:{},ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
                int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
                serverResponse = iOrderService.closeOrder(hour);
            }else {
                log.info("Redisson没有获取到分布式锁:{},ThreadName:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("Redisson分布式锁获取异常",e);
        }finally {
            if(getLock){
                lock.unlock();
                log.info("Redisson分布式锁释放锁");
            }
        }
        return serverResponse;
    }


}
