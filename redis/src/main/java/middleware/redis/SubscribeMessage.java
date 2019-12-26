package middleware.redis;

import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
@SuppressWarnings("all")
public class SubscribeMessage {

    private ExecutorService cache = Executors.newCachedThreadPool();

    @Resource
    private JedisCluster jedisCluster;

    public void subscribeChannel(final String channel,final ChatSubscribe chatSubscribe){
        cache.execute(new Runnable() {
            @Override
            public void run() {
                jedisCluster.subscribe(chatSubscribe,channel);
            }
        });
    }
}
