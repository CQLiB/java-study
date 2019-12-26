package middleware.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

public class RedisPublisher {

    private Logger logger = LoggerFactory.getLogger(RedisPublisher.class);

    @Resource
    private JedisCluster jedisCluster;

    public void sendMessage(final String channel,final String message){
        Thread thread = new Thread(()->{
            Long publisher = jedisCluster.publish(channel,message);
            logger.info("服务器：{}频道发布消息{}-{}",channel,message,publisher);
        });
        logger.info("发布线程启动");
        thread.setName("RedisPublishName");
        thread.start();
    }



}
