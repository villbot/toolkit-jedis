package cn.vimor.toolkit.jedis;

import cn.vimor.toolkit.jedis.service.JedisService;
import cn.vimor.toolkit.jedis.service.impl.JedisServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jani
 * @date 2022/01/18
 */
@Configuration
@ConditionalOnClass(JedisService.class)
public class JedisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(JedisService.class)
    public JedisService jedisService() {
        return new JedisServiceImpl();
    }
}
