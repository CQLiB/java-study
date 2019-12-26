package util.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;


/**
 * zookeeper 会话工具类
 */

@Configuration
public class ZookeeperConfig {

    private static final Logger logger  = LoggerFactory.getLogger(ZookeeperConfig.class);

    private String connectionString;

    private int timeout;


    public ZooKeeper zkClient(){
        ZooKeeper zooKeeper = null;
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper(connectionString, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (Event.KeeperState.SyncConnected==watchedEvent.getState()){
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            logger.info("【初始化Zookeeper连接状态....】={}",zooKeeper.getState());

        }catch (Exception e){
            logger.error("【初始化Zookeeper连接异常....】={}",e);
        }

        return zooKeeper;
    }
    
}
