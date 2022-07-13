package cn.vimor.toolkit.jedis;

import cn.vimor.toolkit.jedis.lock.GlobalLock;
import cn.vimor.toolkit.jedis.lock.GlobalLockImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jani
 * @date 2022/01/18
 */
@Configuration
@ConditionalOnClass(GlobalLock.class)
public class GlobalLockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(GlobalLock.class)
    public GlobalLock globalLock() {
        return new GlobalLockImpl();
    }
}
