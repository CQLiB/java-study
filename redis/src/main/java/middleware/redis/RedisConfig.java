package middleware.redis;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

/**
 *
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {



    @Bean(name="redisTemplate")
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);



        return redisTemplate;
    }
}
