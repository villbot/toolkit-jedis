package cn.vimor.toolkit.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;


/**
 * @author Jani
 * @date 2022/01/18
 */
@Configuration
@ConditionalOnClass(JedisPool.class)
public class JedisConfigAutoConfiguration {

    /**
     * 服务器IP地址
     */
    @Value("${jedis.host}")
    private String host;

    /**
     * 端口
     */
    @Value("${jedis.port}")
    private Integer port;

    /**
     * 密码
     */
    @Value("${jedis.auth}")
    private String auth;

    /**
     * 连接实例的最大连接数
     */
    @Value("${jedis.maxActive}")
    private Integer maxActive;

    /**
     * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
     */
    @Value("${jedis.maxIdle}")
    private Integer maxIdle;

    /**
     * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间
     */
    @Value("${jedis.maxWait}")
    private Integer maxWait;

    /**
     * 连接超时的时间
     */
    @Value("${jedis.timeOut}")
    private Integer timeOut;

    /**
     * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
     */
    @Value("${jedis.testOnBorrow}")
    private boolean testOnBorrow;

    /**
     * 数据库模式是16个数据库 0~15
     */
    @Value("${jedis.defaultDataBase}")
    private Integer defaultDataBase;

    /**
     * redis连接客户端名称
     */
    @Value("${jedis.clientName}")
    private String clientName;

    /**
     * 初始化jedisPool
     *
     * @return {@link JedisPool}
     */
    @Bean
    @ConditionalOnMissingBean(JedisPool.class)
    public JedisPool jedisPool() {
        GenericObjectPoolConfig<Jedis> poolConfig = new GenericObjectPoolConfig<>();
        if (maxActive != null) {
            poolConfig.setMaxTotal(maxActive);
        } else {
            poolConfig.setMaxTotal(1024);
        }
        if (maxIdle != null) {
            poolConfig.setMaxIdle(maxIdle);
        } else {
            poolConfig.setMaxIdle(200);
        }
        if (maxWait != null) {
            poolConfig.setMaxWait(Duration.ofMillis(maxWait));
            poolConfig.setTestOnBorrow(testOnBorrow);
        } else {
            poolConfig.setMaxWait(Duration.ofMillis(10000));
            poolConfig.setTestOnBorrow(true);
        }
        if (StringUtils.isEmpty(auth)) {
            auth = null;
        }
        poolConfig.setJmxEnabled(false);

        return new JedisPool(poolConfig, host, port, timeOut, auth, defaultDataBase, clientName);
    }
}
