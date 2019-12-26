package middleware.redis.util;

import middleware.redis.config.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;

@Configuration
public class RedisCluster {
    @Autowired
    private RedisProperties redisProperties;

    public JedisCluster getJedisCluster(){
        String[] serverArray = redisProperties
    }
}
