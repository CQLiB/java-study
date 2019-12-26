package util.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Zookeeper Client
 *
 *  zookeeper 客户端操作工具类
 *
 *  主要提供以下几点功能
 *
 *
 */
@Component
public class ZookeeperAPI {
    //日志记录器 logger
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperAPI.class);

    @Autowired
    private ZooKeeper zkClient;


    /**
     *
     * @param path
     * @param needWatch
     * @return
     */
    public Stat exists(String path,boolean needWatch){
        try {
            return zkClient.exists(path,needWatch);
        }catch (Exception e){
            logger.error("");
            return null;
        }
    }


    /**
     *
     * @param path
     * @param watcher
     * @return
     */
    public Stat exists(String path, Watcher watcher){
        try {
            return zkClient.exists(path,watcher);
        }catch (Exception e){

            logger.error("");
            return null;
        }
    }


    /**
     *
     * @param path
     * @param data
     * @return
     */
    public boolean createNode(String path,String data){
        try {
            zkClient.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return  true;
        }catch (Exception e){
            logger.error("");
            return  false;
        }
    }


    /**
     *
     * @param path
     * @param data
     * @return
     */
    public boolean updateNode(String path,String data){
        try {
            zkClient.setData(path,data.getBytes(),-1);
            return true;
        }catch (Exception E){
            logger.error("");
            return false;
        }
    }

    /**
     *
     * @param path
     * @return
     */
    public boolean deleteNode(String path){
        try {
            zkClient.delete(path,-1);
            return true;
        }catch (Exception e){
            logger.error("");
            return  false;
        }
    }

    /**
     *
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException{
        List<String> list = zkClient.getChildren(path,false);
        return list;
    }

    /**
     * 获取指定路径(节点NODE)的值
     * @param path  指定路径
     * @param watcher 监视器
     * @return 获取到的值
     */
    public String getData(String path,Watcher watcher){
        try {
            Stat stat = new Stat();
            byte[] bytes = zkClient.getData(path,watcher,stat);
            return new String(bytes);
        }catch (Exception e){
            logger.error("");
            return  null;
        }
    }

}
