package middleware.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis",ignoreUnknownFields = false)
@PropertySource("classpath:/redis-config.yml")
@SuppressWarnings("all")
public class RedisProperties {
    
}
